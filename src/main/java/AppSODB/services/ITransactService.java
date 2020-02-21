package AppSODB.services;

import AppSODB.model.BasketItem;
import AppSODB.model.Product;
import AppSODB.model.TransactItem;

import java.util.ArrayList;

public interface ITransactService {


    Double getBasketValue();

    void buyBasket();

    void clearBasket();

    void addProductToBasket(int productId, double quant);

    ArrayList<TransactItem> getListOfOrders();

    ArrayList<BasketItem> getListOfOrderItems(String orderId);

}
