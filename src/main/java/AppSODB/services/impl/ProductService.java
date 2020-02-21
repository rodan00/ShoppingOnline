package AppSODB.services.impl;

import AppSODB.dao.IProductDAO;
import AppSODB.model.BasketItem;
import AppSODB.model.Product;
import AppSODB.services.IProductService;
import AppSODB.services.ITransactService;
import AppSODB.session.SessionObject;
import com.sun.org.apache.bcel.internal.generic.LRETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class ProductService implements IProductService {


    @Autowired
    IProductDAO iProductDAO;

    @Resource
    SessionObject sessionObject;

    @Autowired
    ITransactService iTransactService;

    @Override
    public ArrayList<Product> getListOfProductsFromService() {
        ArrayList<Product> lista = iProductDAO.getListOfProducts();
       // System.out.println(lista);
        return iProductDAO.getListOfProducts();
    }

    public Product getProductByIdFromService(int productId){
        Product product=iProductDAO.getProductById(productId);
    return product;
    }

    public void decreaseProductStore(int productId,double quant){
        Product product=iProductDAO.getProductById(productId);
        double newStoreQuant=Math.max(product.getStoreQuant()-quant,0);
        System.out.println("Stan magazynowy"+ newStoreQuant);
        product.setStoreQuant(newStoreQuant);
        iProductDAO.persistProduct(product);
    }
    public void increaseProductStore(int productId,double quant){
        Product product=iProductDAO.getProductById(productId);
        double newStoreQuant=product.getStoreQuant()+quant;
        product.setStoreQuant(newStoreQuant);
        iProductDAO.persistProduct(product);
    }

    public void restoreProductStoreFromBasket(){
        ArrayList<BasketItem> basket=sessionObject.getBasket();
        System.out.println("jestem w restore -------------");
        for (BasketItem item : basket){
            System.out.println("jestem w pętli przed");
            increaseProductStore(item.getBasketItemId(), item.getBasketItemQuant());
            System.out.println("jestem w pętli za");
        }
        iTransactService.clearBasket();
    }

    @Override
    public void removeProductById(int id){
        iProductDAO.removeProductByIdFromDB(id);
    }

    @Override
    public void addNewProduct(String productname,
                                          double productprice,
                                          double productquant){
     Product product=new Product();
     product.setProductName(productname);
     product.setProductPrice(productprice);
     product.setStoreQuant(productquant);
     iProductDAO.persistProduct(product);
    }

    @Override
    public void updateProductQuant(int id, double productquant){
        Product product=iProductDAO.getProductById(id);
        product.setStoreQuant(productquant);
        iProductDAO.persistProduct(product);
    }
}
