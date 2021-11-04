package hanu.a2_1801040158.mycart.models;

import androidx.annotation.NonNull;

public class Cart {
    private int cartId;
    private int productId;
    private String productThumbnail;
    private String productName;
    private int price;
    private int quantity;

    public Cart(int cartId, int productId, String productName, int price, String productThumbnail, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.productThumbnail = productThumbnail;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public Cart() {

    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductThumbnail() {
        return productThumbnail;
    }

    public void setProductThumbnail(String productThumbnail) {
        this.productThumbnail = productThumbnail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @NonNull
    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + price +
                ", productThumbnail='" + productThumbnail + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
