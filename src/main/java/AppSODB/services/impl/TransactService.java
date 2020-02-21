package AppSODB.services.impl;


import AppSODB.dao.ITransactDAO;
import AppSODB.dao.IUserDAO;
import AppSODB.model.BasketItem;
import AppSODB.model.Product;
import AppSODB.model.TransactItem;
import AppSODB.services.IProductService;
import AppSODB.services.ITransactService;
import AppSODB.session.SessionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Service
public class TransactService implements ITransactService {

    @Autowired
    ITransactService iTransactService;

    @Autowired
    IProductService iProductService;

    @Resource
    SessionObject sessionObject;

    @Autowired
    ITransactDAO iTransactDAO;


    public Double getBasketValue(){
        ArrayList<BasketItem> basket=sessionObject.getBasket();
        double basketValue =0;
        for(BasketItem item : basket){
            basketValue=basketValue+item.getBasketItemQuant()*item.getBasketItemPrice();
        }
        return basketValue;
    }

    @Override
    public void addProductToBasket(int productId, double quant){

        // TODO sprawdź czy w ogóle taki produkt istnieje
        Product product=iProductService.getProductByIdFromService(productId);
        BasketItem newBasketItem=new BasketItem(product, quant);
        sessionObject.getBasket().add(newBasketItem);
        iProductService.decreaseProductStore(productId,quant);
    }

    @Override
    public void buyBasket(){
        LocalDate date=LocalDate.now();
        String orderId=getOrderId();
        iTransactDAO.saveTransactionsToDB(sessionObject.getBasket(),
                                        date,
                                        sessionObject.getUser().getId(),
                                        orderId);
       clearBasket();
    }

    @Override
    public void clearBasket(){
        ArrayList<BasketItem> basket= sessionObject.getBasket();
        int basketSize=basket.size();
        for(int i=(basketSize-1); i>=0; i--) {
            basket.remove(i);
        }
    }

    private String getOrderId(){
        LocalDateTime datetime =LocalDateTime.now();
        StringBuilder sb=new StringBuilder();
        sb.append(datetime.getYear());
        sb.append(datetime.getMonthValue());
        sb.append(datetime.getDayOfYear()+".");
        sb.append(datetime.getHour());
        sb.append(datetime.getMinute());
        sb.append(datetime.getSecond());
        return sb.toString();
    }

    @Override
    public ArrayList<TransactItem> getListOfOrders(){
        int userId=sessionObject.getUser().getId();
        return iTransactDAO.getTransactionsWhereUserIdGroupedByOrderId(userId);
    }
    @Override
    public ArrayList<BasketItem> getListOfOrderItems(String orderId){
        int userId=sessionObject.getUser().getId();
        return iTransactDAO.getTransactionsWhereOrderIdUserId(orderId,userId);
    }
}
