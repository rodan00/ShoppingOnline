package AppSODB.dao.impl;

import AppSODB.dao.IUserDAO;
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
public class DBUserDAOImpl implements IUserDAO {

    @Autowired
    Connection connection;

    @Override
    public void persistUser(User user) {
        String sql;
        PreparedStatement ps;

        if (user.getId() == 0) {
            try {
                sql = "INSERT INTO tuser (login, pass, userrole) VALUES (?,?,?);";
                ps = this.connection.prepareStatement(sql);
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPass());
                ps.setString(3, user.getUserRole().toString());
                ps.executeUpdate();  // teraz jest wykonywany SQL

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: persistUser() dodawanie Usera");
            }
        } else {
            try {
                sql = "UPDATE tuser SET login=?, pass=?, userrole=? WHERE userid=?";
                ps = this.connection.prepareStatement(sql);
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPass());
                ps.setString(3, user.getUserRole().toString());
                ps.setInt(4, user.getId());
                ps.executeUpdate();  // teraz jest wykonywany SQL

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error:  persistUser() update Usera");
            }
        }
    }

    @Override
    public User getUserByLogin(String login){

        try {
            String sql = "SELECT * FROM tuser WHERE login=?;";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            /*w tym przypadku ResultSet to powinien być jeden rekord ale DB
            nie ma założonego warunku0, na UNIQUE(login), więc może się tu pojawić
            kilka rekordów z tym samym loginem. Bierzemy pierwszego napotkanego*/
            if (resultSet.next()) {
                // zamieniamy obiekt ResultSet na Usera
                User user = mapResultSetToUser(resultSet);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: getUserByLogin()");}
        return null;
    }
    @Override
    public User getUserById(int id){
        try {
            String sql = "SELECT * FROM tuser WHERE userid=?;";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: getUserById()");}
        return null;
    }


    private User mapResultSetToUser(ResultSet rs){
        User user =new User();
        try {
            // czytamy poszczególne pola z bazy danych do seterów usera
            user.setId(rs.getInt("userid"));
            user.setLogin(rs.getString("login"));
            user.setPass(rs.getString("pass"));
            user.setUserRole(UserRole.valueOf(rs.getString("userrole")));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: mapResultToUser()");
        }
        // jak będzie błąd to dostaniemy user=null
        return user;
    }

    @Override
    public ArrayList<User> getAllUsersFromDB(){
        ArrayList<User> listOfAllUsers=new ArrayList<>();
        try {
            String sql = "SELECT * FROM tuser;";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // zamieniamy obiekt ResultSet na Usera
                User user = mapResultSetToUser(resultSet);
                listOfAllUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: getAllUsersFromDB()");}
        return listOfAllUsers;
    }

    @Override
    public void removeUserFromDB(int id){
        try {
            String sql = "DELETE FROM tuser WHERE userid=?;";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
           preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: getUserByLogin()");
        }
    }


}
