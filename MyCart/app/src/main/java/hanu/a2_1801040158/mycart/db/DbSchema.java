package hanu.a2_1801040158.mycart.db;

public class DbSchema {
    public final class CartTable{
        public static final String NAME="cart";
        public final class Cols{
            public static final String CART_ID ="cartId";
            public static final String PRODUCT_ID ="productId";
            public static final String PRODUCT_NAME ="productName";
            public static final String PRODUCT_PRICE ="productPrice";
            public static final String PRODUCT_THUMBNAIL ="productThumbnail";
            public static final String PRODUCT_QUANTITY ="productQuantity";
        }
    }
}
