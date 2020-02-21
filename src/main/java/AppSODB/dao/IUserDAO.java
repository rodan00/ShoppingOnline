package AppSODB.dao;

import AppSODB.model.User;

import java.util.ArrayList;

public interface IUserDAO {

    void persistUser(User user);
    User getUserByLogin(String login);
    User getUserById(int id);
    ArrayList<User> getAllUsersFromDB();
    void removeUserFromDB(int id);
}
