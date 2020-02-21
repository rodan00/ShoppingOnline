package AppSODB.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class AppConfiguration {


    @Bean
    public Connection connection() {
        try {
            String url = "jdbc:mysql://localhost:3306/shoppingonline?user=root&password=";
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Błąd modczas łączenia !!");
            e.printStackTrace();
        }
        return null;
    }


}
