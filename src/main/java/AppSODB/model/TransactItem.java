package AppSODB.model;

import java.time.LocalDate;

public class TransactItem extends BasketItem {

    private int transactId;
    private LocalDate date;
    private int userId;
    private String orderId;
    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }




    public int getTransactId() {
        return transactId;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }


    public void setTransactId(int transactId) {
        this.transactId = transactId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "ORDER:"+
                " orderId='" + orderId +
                ", date=" + date +
                ", value=" + value;
    }
}
