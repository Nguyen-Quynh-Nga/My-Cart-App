package hanu.a2_1801040158.mycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1801040158.mycart.adapter.CartAdapter;
import hanu.a2_1801040158.mycart.db.DbManager;
import hanu.a2_1801040158.mycart.models.Cart;
import hanu.a2_1801040158.mycart.models.Product;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartList;
    private TextView sumPrice, totalPrice;
    private ImageButton btnIncrease, btnDecrease;
    private List<Cart> cartItems;
    private CartAdapter cartAdapter;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartList= findViewById(R.id.cartList);
        sumPrice=findViewById(R.id.sumPrice);
        btnIncrease=findViewById(R.id.btnIncrease);
        btnDecrease=findViewById(R.id.btnDecrease);
        totalPrice = findViewById(R.id.totalPrice);
        dbManager=DbManager.getInstance(this);
        cartItems= dbManager.all();
        cartAdapter= new CartAdapter(cartItems);
        cartList.setAdapter(cartAdapter);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,1);
        cartList.setLayoutManager(gridLayoutManager);
        totalPrice.setText(dbManager.getTotalPrice()+" VND");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.or)));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}