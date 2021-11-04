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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import hanu.a2_1801040158.mycart.R;
import hanu.a2_1801040158.mycart.db.DbManager;
import hanu.a2_1801040158.mycart.models.Cart;
import hanu.a2_1801040158.mycart.models.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private List<Cart> cartItems;

    public CartAdapter(List<Cart> cartItems) {
        this.cartItems = cartItems;
    }
    @NonNull
    @Override
    public CartAdapter.CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View cartView= inflater.inflate(R.layout.cart,parent,false);
        return new CartHolder(cartView,context);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartHolder holder, int position) {
        Cart cartItem= cartItems.get(position);
        holder.bind(cartItem,position);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder{
        private ImageView productThumbnail;
        private TextView productName, productPrice, productQuantity,sumPrice;
        private ImageButton btnIncrease, btnDecrease;
        private DbManager dbManager;
        private Context context;
        private TextView totalPrice;

        public CartHolder(@NonNull View itemView, Context context) {

            super(itemView);
            this.context=context;
            productThumbnail=itemView.findViewById(R.id.productThumbnail);
            productName=itemView.findViewById(R.id.productName);
            productPrice=itemView.findViewById(R.id.productPrice);
            productQuantity=itemView.findViewById(R.id.productQuantity);
            sumPrice=itemView.findViewById(R.id.sumPrice);
            btnIncrease=itemView.findViewById(R.id.btnIncrease);
            btnDecrease=itemView.findViewById(R.id.btnDecrease);
            dbManager= DbManager.getInstance(context);
            totalPrice=((Activity) context).findViewById(R.id.totalPrice);
        }

        public void bind(Cart cartItem, int position) {
            productName.setText(cartItem.getProductName());
            productPrice.setText(String.valueOf(cartItem.getPrice())+ " vnd");
            productQuantity.setText(String.valueOf(cartItem.getQuantity()));
            sumPrice.setText(String.valueOf(cartItem.getPrice()* cartItem.getQuantity()));
            ThumbnailLoader thumbnailLoader = new ThumbnailLoader();
            thumbnailLoader.execute(cartItem.getProductThumbnail());
            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItem.setQuantity(cartItem.getQuantity()+1);
                    if(dbManager.updateCartItem(cartItem)){
                        notifyItemChanged(position);
                    }
                    totalPrice.setText(dbManager.getTotalPrice()+ " VND");
                }
            });
            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cartItem.getQuantity()==1){
                        dbManager.deletCartItem(cartItem);
                        cartItems.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,getItemCount());

                    }else {
                        cartItem.setQuantity(cartItem.getQuantity()-1);
                        if(dbManager.updateCartItem(cartItem)){
                            notifyItemChanged(position);
                        }
                    }
                    totalPrice.setText(dbManager.getTotalPrice()+ " VND");
                }
            });
        }
        public class ThumbnailLoader extends AsyncTask<String, Void, Bitmap> {

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
                productThumbnail.setImageBitmap(bitmap);
            }
        }
    }
}
