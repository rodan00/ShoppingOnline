package AppSODB.model;

public class Product {

private int productId;
private String productName;
private double productPrice;
private double storeQuant;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double price) {
        this.productPrice = price;
    }

    public double getStoreQuant() {
        return storeQuant;
    }

    public void setStoreQuant(double storequant) {
        this.storeQuant = storequant;
    }

    @Override
    public String toString() {
        return "Product:" +
                "productId=" + productId +
                ", productName='" + productName +
                ", price=" + productPrice +
                ", storequant=" + storeQuant;
    }
}
