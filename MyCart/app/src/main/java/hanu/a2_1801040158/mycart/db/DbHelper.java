package hanu.a2_1801040158.mycart.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME="cart.db";
    private static final int DATABASE_VERSION= 3;
    public DbHelper(@Nullable Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE " + DbSchema.CartTable.NAME
        + "(" + "cartId INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DbSchema.CartTable.Cols.PRODUCT_ID + " int, "+
                DbSchema.CartTable.Cols.PRODUCT_NAME+ " TEXT, "+
                DbSchema.CartTable.Cols.PRODUCT_PRICE+ " int, "+
                DbSchema.CartTable.Cols.PRODUCT_QUANTITY+" int, "+
                DbSchema.CartTable.Cols.PRODUCT_THUMBNAIL+ " TEXT"+")"
                );
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.CartTable.NAME);
        onCreate(db);
    }
}
