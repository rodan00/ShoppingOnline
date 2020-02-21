package AppSODB.dao;


import AppSODB.model.BasketItem;
import AppSODB.model.TransactItem;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ITransactDAO {

    void saveTransactionsToDB(ArrayList<BasketItem> basket,
                              LocalDate date,
                              int userId,
                              String orderId);

    ArrayList<TransactItem> getTransactionsWhereUserIdGroupedByOrderId(int userId);

    ArrayList<BasketItem>getTransactionsWhereOrderIdUserId(String orderId, int userId);

}
