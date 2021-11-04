package hanu.a2_1801040158.mycart.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import hanu.a2_1801040158.mycart.R;
import hanu.a2_1801040158.mycart.db.DbManager;
import hanu.a2_1801040158.mycart.models.Product;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder>{

    private List<Product> productList;
    private Context context;
    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View productView= inflater.inflate(R.layout.product,parent,false);
        return new ProductHolder(productView,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, int position) {
        Product product= productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void filterList(List<Product> filteredlist){
        if(!filteredlist.isEmpty()){
            productList=filteredlist;
            notifyDataSetChanged();
        }

    }
    public class ProductHolder extends RecyclerView.ViewHolder {
        private TextView name, unitPrice;
        private ImageView thumbnail;
        private ImageButton btnAdd;
        private DbManager dbManager;
        private Context context;
        public ProductHolder(@NonNull View itemView, Context context) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            thumbnail= itemView.findViewById(R.id.thumbnail);
            unitPrice= itemView.findViewById(R.id.unitPrice);
            btnAdd= itemView.findViewById(R.id.btnAdd);
            dbManager = DbManager.getInstance(context);
        }

        public void bind(Product product) {
            name.setText(product.getName());
            unitPrice.setText(String.valueOf(product.getUnitPrice())+ " vnd");
            ThumbnailLoader thumbnailLoader= new ThumbnailLoader();
            thumbnailLoader.execute(product.getThumbnail());
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbManager.addToCart(product);
                }
            });
        }
        public class ThumbnailLoader extends AsyncTask<String, Void, Bitmap>{

            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream is = null;
                    is=connection.getInputStream();
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    return bitmap;
                }catch (IOException e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {

                thumbnail.setImageBitmap(bitmap);
            }
        }
    }
}
