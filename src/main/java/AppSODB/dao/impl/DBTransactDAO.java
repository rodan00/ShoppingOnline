package AppSODB.dao.impl;


import AppSODB.dao.ITransactDAO;
import AppSODB.model.BasketItem;
import AppSODB.model.Product;
import AppSODB.model.TransactItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class DBTransactDAO implements ITransactDAO {

    @Autowired
    Connection connection;

    @Override
    public void saveTransactionsToDB(ArrayList<BasketItem> basket, LocalDate date, int userId, String orderId) {
        String sql;
        PreparedStatement ps;
        for (BasketItem basketItem: basket){
            try {
                sql = "INSERT INTO ttransaction " +
                        "(date, userid, orderid,productid,productName, productprice,transactquant,value) " +
                        "VALUES (?,?,?,?,?,?,?,?);";
                    ps = this.connection.prepareStatement(sql);
                    ps.setDate(1, Date.valueOf(date));
                    ps.setInt(2, userId);
                    ps.setString(3, orderId);
                    ps.setInt(4, basketItem.getBasketItemId());
                    ps.setString(5, basketItem.getBasketItemName());
                    ps.setDouble(6, basketItem.getBasketItemPrice());
                    ps.setDouble(7, basketItem.getBasketItemQuant());
                    ps.setDouble(8,basketItem.getBasketItemQuant()*basketItem.getBasketItemPrice());
                    ps.executeUpdate();  // run SQL
            } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error: saveTransactions()");
            }
        }
    }

    @Override
    public ArrayList<TransactItem> getTransactionsWhereUserIdGroupedByOrderId(int userId){
        ArrayList<TransactItem> transactList=new ArrayList<>();
        String sql;
        PreparedStatement preparedStatement;
        try{
            sql="SELECT * , SUM(value) as vsum FROM ttransaction WHERE userid=? GROUP BY orderid";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TransactItem transactItem = mapResultSetToTransactItem(resultSet);
                transactList.add(transactItem);
            }

        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: getTransactionsGroupedByOrder()");
        }
        return transactList;
    }

    private TransactItem mapResultSetToTransactItem(ResultSet rs){
        TransactItem transactItem  =new TransactItem();
        try {
            // czytamy poszczególne pola z bazy danych do seterów produktu
            transactItem.setTransactId(rs.getInt("transactid"));
            transactItem.setDate(rs.getDate("date").toLocalDate());
            transactItem.setUserId(rs.getInt("userid"));
            transactItem.setOrderId(rs.getString("orderid"));
            transactItem.setValue(rs.getDouble("vsum"));

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: mapResultSetToTransactItem()");
        }
        return transactItem;
    }



    @Override
    public ArrayList<BasketItem> getTransactionsWhereOrderIdUserId(String orderId, int userId){
        ArrayList<BasketItem> basketItemList=new ArrayList<>();
        String sql;
        PreparedStatement preparedStatement;
        try{
            sql="SELECT * FROM ttransaction WHERE orderid=? AND userid=?";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, orderId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BasketItem basketItem = mapResultSetToBasketItem(resultSet);
               basketItemList.add(basketItem);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: getTransactionsWhereOrderIdUserId()");
        }
        return basketItemList;
    }



    private BasketItem mapResultSetToBasketItem(ResultSet rs){
        BasketItem basketItem  =new BasketItem();
        try {
            // czytamy poszczególne pola z bazy danych do seterów basketItem
            basketItem.setBasketItemId(rs.getInt("productid"));
            basketItem.setBasketItemName(rs.getString("productname"));
            basketItem.setBasketItemPrice(rs.getDouble("productprice"));
            basketItem.setBasketItemQuant(rs.getDouble("transactquant"));

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: mapResultSetToBasketItem()");
        }
        return basketItem;
    }
}
