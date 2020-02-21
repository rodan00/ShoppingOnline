package AppSODB.services;

import AppSODB.dao.IProductDAO;
import AppSODB.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public interface IProductService {

    ArrayList<Product> getListOfProductsFromService();

    Product getProductByIdFromService(int productId);

    void decreaseProductStore(int productId, double quant);

    void increaseProductStore(int productId, double quant);

    void restoreProductStoreFromBasket();

    void removeProductById(int id);

    void addNewProduct(String productname,double productprice,double productquant);

    void updateProductQuant(int id, double productquant);
}
