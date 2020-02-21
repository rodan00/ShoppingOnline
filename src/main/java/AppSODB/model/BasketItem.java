
package AppSODB.model;

public class BasketItem {

    private int basketItemId;
    private String basketItemName;
    private double basketItemPrice;
    private double basketItemQuant;

    public BasketItem(){}

    public BasketItem(Product product, double quant){
        this.basketItemId=product.getProductId();
        this.basketItemName=product.getProductName();
        this.basketItemPrice=product.getProductPrice();
        this.basketItemQuant=Math.max(Math.min(product.getStoreQuant(),quant),0);
    }

    public int getBasketItemId() {
        return basketItemId;
    }

    public String getBasketItemName() {
        return basketItemName;
    }

    public double getBasketItemPrice() {
        return basketItemPrice;
    }

    public double getBasketItemQuant() {
        return basketItemQuant;
    }


    public void setBasketItemId(int basketItemId) {
        this.basketItemId = basketItemId;
    }

    public void setBasketItemName(String basketItemName) {
        this.basketItemName = basketItemName;
    }

    public void setBasketItemPrice(double basketItemPrice) {
        this.basketItemPrice = basketItemPrice;
    }

    public void setBasketItemQuant(double basketItemQuant) {
        this.basketItemQuant = basketItemQuant;
    }

    @Override
    public String toString() {
        return "PRODUCT IN BASKET: " +
                " ID=" + basketItemId +
                ", name=" + basketItemName +
                ", price=" + basketItemPrice +
                ", quantity=" + basketItemQuant+
                ", value="+(basketItemPrice*basketItemQuant);

    }
}
