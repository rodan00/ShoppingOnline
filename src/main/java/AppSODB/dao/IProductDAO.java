package AppSODB.dao;

import AppSODB.model.Product;
import AppSODB.model.User;

import java.util.ArrayList;

public interface IProductDAO {

    void persistProduct(Product product);
    Product getProductById(int productId);
    ArrayList <Product> getListOfProducts();
    void removeProductByIdFromDB(int id);

}
