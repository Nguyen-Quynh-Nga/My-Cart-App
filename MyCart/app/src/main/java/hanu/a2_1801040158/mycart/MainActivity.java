package hanu.a2_1801040158.mycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hanu.a2_1801040158.mycart.adapter.ProductAdapter;
import hanu.a2_1801040158.mycart.models.Product;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvProductList;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvProductList=findViewById(R.id.rvProductList);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        productList= new ArrayList<>();
        productAdapter= new ProductAdapter(productList);
        rvProductList.setLayoutManager(gridLayoutManager);
        rvProductList.setAdapter(productAdapter);
        ProductLoader productLoader= new ProductLoader();
        productLoader.execute("https://mpr-cart-api.herokuapp.com/products");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.or)));
        search= findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }
    private void filter(String text){
        List<Product> filteredList = new ArrayList<>();
        for(int i=0; i<productList.size(); i++){
            if(productList.get(i).getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(productList.get(i));
            }
        }
        productAdapter.filterList(filteredList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.myCart:
                Intent intent= new Intent(this, CartActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public class ProductLoader extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            //fetch API and update product
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream is = urlConnection.getInputStream();
                Scanner sc = new Scanner(is);
                StringBuilder result = new StringBuilder();
                String line;
                while(sc.hasNextLine()) {
                    line = sc.nextLine();
                    result.append(line);
                }
                JSONArray productArr= new JSONArray(result.toString());
                for(int i=0; i<productArr.length();i++){
                    JSONObject jsonObject= productArr.getJSONObject(i);
                    int id= jsonObject.getInt("id");
                    String thumbnail=jsonObject.getString("thumbnail");
                    String name= jsonObject.getString("name");
                    int unitPrice= jsonObject.getInt("unitPrice");
                    Product product= new Product(id, thumbnail,name, unitPrice);
                    productList.add(product);
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productAdapter.notifyDataSetChanged();
        }
    }
}