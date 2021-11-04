package hanu.a2_1801040158.mycart.db;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.List;

import hanu.a2_1801040158.mycart.models.Cart;
import hanu.a2_1801040158.mycart.models.Product;


public class DbManager {
    private static DbManager dbManager;
    private  DbHelper dbHelper;
    private SQLiteDatabase db;
    private static final String INSERT_STMT =
            "INSERT INTO " + DbSchema.CartTable.NAME + "(productId, productName, productPrice, productThumbnail, productQuantity)  VALUES (?, ?, ?, ?, ?)";

    public static DbManager getInstance(Context context){
        if(dbManager==null){
            dbManager= new DbManager(context);
        }
        return dbManager;
    }
    private DbManager(Context context){
        dbHelper= new DbHelper(context);
        db=dbHelper.getWritableDatabase();
        Log.d("dbs", ":DbManager is creating new dbs");
    }
    public List<Cart> all(){
        String query= "SELECT * FROM cart";
        Cursor cursor= db.rawQuery(query, null);
        CartCursorWrapper cursorWrapper= new CartCursorWrapper(cursor);
        return cursorWrapper.getCartItems();
    }
    public Cart getCartItem(int productId){
        String query= "SELECT * FROM cart WHERE productId=?";
        Cursor cursor= db.rawQuery(query, new String[]{String.valueOf(productId)});
        Cart cart =null;
        if(cursor.moveToFirst()){
            do{
                int cartId  = cursor.getInt(0);
                int pdId = cursor.getInt(1);
                String productName = cursor.getString(2);
                int productPrice = cursor.getInt(3);
                String productThumbnail = cursor.getString(5);
                int productQuantity = cursor.getInt(4);
                cart = new Cart(cartId,pdId,productName,productPrice,productThumbnail, productQuantity);
            }while (cursor.moveToNext());
        }
        return cart;
    }
    public boolean addToCart(Product product){
        if(getCartItem(product.getId())==null){
            String query= "INSERT INTO cart(productId, productName, productPrice, productThumbnail, productQuantity) VALUES (?,?,?,?,1);";
            SQLiteStatement sqLiteStatement= db.compileStatement(query);
            sqLiteStatement.bindLong(1, product.getId());
            sqLiteStatement.bindString(2, product.getName());
            sqLiteStatement.bindLong(3, product.getUnitPrice());
            sqLiteStatement.bindString(4, product.getThumbnail());
            long id= sqLiteStatement.executeInsert();
            if(id>0){
                return true;
            }
            return false;

            }
        else {
            int quantity = getCartItem(product.getId()).getQuantity() + 1;
            String query = "UPDATE cart SET productQuantity = ? WHERE productId = ?";
            SQLiteStatement sqLiteStatement = db.compileStatement(query);
            sqLiteStatement.bindLong(1, quantity);
            sqLiteStatement.bindLong(2, product.getId());
            long id = sqLiteStatement.executeUpdateDelete();
            if (id > 0) {
                return true;
            }
            return false;
        }
    }
    public boolean updateCartItem(Cart cartItem){
        String query = "UPDATE cart SET productQuantity= ? WHERE productId= ?";
        SQLiteStatement sqLiteStatement= db.compileStatement(query);
        sqLiteStatement.bindLong(1, cartItem.getQuantity());
        sqLiteStatement.bindLong(2, cartItem.getProductId());
        int result= sqLiteStatement.executeUpdateDelete();
        if(result>0){
            return true;
        }
        return false;
    }
    public int getTotalPrice(){
        String query= "SELECT SUM(productQuantity*productPrice) FROM cart";
        Cursor cursor= db.rawQuery(query, null);
        int totalPrice=0;
        if(cursor.moveToFirst()){
            do{
                totalPrice= cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return totalPrice;
    }
    public boolean deletCartItem(Cart cartItem){
        String query= "DELETE FROM cart WHERE productId = ?";
        SQLiteStatement sqLiteStatement= db.compileStatement(query);
        sqLiteStatement.bindLong(1, cartItem.getProductId());
        int result= sqLiteStatement.executeUpdateDelete();
        if(result>0){
            return true;
        }
        return false;
    }
}
