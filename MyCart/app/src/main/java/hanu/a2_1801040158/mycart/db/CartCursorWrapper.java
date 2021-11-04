package hanu.a2_1801040158.mycart.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1801040158.mycart.models.Cart;

public class CartCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CartCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Cart getCartItem(){
        int cartId= getInt(getColumnIndex(DbSchema.CartTable.Cols.CART_ID));
        int productId= getInt(getColumnIndex(DbSchema.CartTable.Cols.PRODUCT_ID));
        String productThumbnail= getString(getColumnIndex(DbSchema.CartTable.Cols.PRODUCT_THUMBNAIL));
        String productName= getString(getColumnIndex(DbSchema.CartTable.Cols.PRODUCT_NAME));
        int productPrice= getInt(getColumnIndex(DbSchema.CartTable.Cols.PRODUCT_PRICE));
        int productQuantity= getInt(getColumnIndex(DbSchema.CartTable.Cols.PRODUCT_QUANTITY));
        Cart cart= new Cart(cartId,productId,productName,productPrice, productThumbnail,productQuantity);
        return cart;
    }
    public List<Cart> getCartItems(){
        List<Cart> cartItems= new ArrayList<>();
        moveToFirst();
        while (!isAfterLast()){
            Cart cart= getCartItem();
            cartItems.add(cart);
            moveToNext();
        }
        return cartItems;
    }
}
