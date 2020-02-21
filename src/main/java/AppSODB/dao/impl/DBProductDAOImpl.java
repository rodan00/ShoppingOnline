package AppSODB.dao.impl;

import AppSODB.dao.IProductDAO;
import AppSODB.model.Product;
import AppSODB.model.User;
import AppSODB.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class DBProductDAOImpl implements IProductDAO {


    @Autowired
    Connection connection;

    @Override
    public void persistProduct(Product product){
        String sql;
        PreparedStatement ps;

        if (product.getProductId() == 0) {
            try {
                sql = "INSERT INTO tproduct (productname, productprice, storequant) VALUES (?,?,?);";
                ps = this.connection.prepareStatement(sql);
                ps.setString(1, product.getProductName());
                ps.setDouble(2, product.getProductPrice());
                ps.setDouble(3, product.getStoreQuant());
                ps.executeUpdate();  // teraz jest wykonywany SQL

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: persistProduct() dodawanie Produktu");
            }
        } else {
            try {
                sql = "UPDATE tproduct SET productname=?, productprice=?, storequant=? WHERE productid=?";
                ps = this.connection.prepareStatement(sql);

                ps.setString(1, product.getProductName());
                ps.setDouble(2, product.getProductPrice());
                ps.setDouble(3, product.getStoreQuant());
                ps.setInt(4, product.getProductId());
                ps.executeUpdate();  // teraz jest wykonywany SQL
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error:  persisProduct() update Produktu");
            }
        }
    }

    @Override
    public Product getProductById(int productId){
        try {
            String sql = "SELECT * FROM tproduct WHERE productid=?;";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, productId);

            ResultSet resultSet = preparedStatement.executeQuery();
            /*productID jest polem unikalnym więc będzie co najwyżej jeden produkt*/

            /*tu sprawdzamy czy zbiór wyników zapytania (=rekordów) nie jest pusty,
            jeżeli nie jest pusty tzna że jest w nim dokładnie jeden element, bo pole
            productid jest unikalne*/

            if (resultSet.next()) {
                // zamieniamy obiekt ResultSet na Produkt
                Product product = mapResultSetToProduct(resultSet);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: getProductById()");}
        return null;
    }

    private Product mapResultSetToProduct(ResultSet rs){
        Product product  =new Product();
        try {
            // czytamy poszczególne pola z bazy danych do seterów produktu
            product.setProductId(rs.getInt("productid"));
            product.setProductName(rs.getString("productname"));
            product.setProductPrice(rs.getDouble("productprice"));
            product.setStoreQuant(rs.getDouble("storequant"));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: mapResultToProduct()");
        }
        // jak będzie błąd to dostaniemy product=null
        return product;
    }

    @Override
    public ArrayList<Product> getListOfProducts(){
        ArrayList<Product> listOfProducts=new ArrayList<>();
        try {
            String sql = "SELECT * FROM tproduct;";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // zamieniamy obiekt ResultSet na Produkt
                Product product = mapResultSetToProduct(resultSet);
                listOfProducts.add(product);
            }
            return listOfProducts;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: getListOfProducts()");}
        return null;
    }

    public void removeProductByIdFromDB(int id){
        try {
            String sql = "DELETE FROM tproduct WHERE productid=?;";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: removeProductByIdFromDB()");
        }

    }

}
