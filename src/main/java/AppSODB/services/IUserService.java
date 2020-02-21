package AppSODB.services;

import AppSODB.model.User;

import java.util.ArrayList;

public interface IUserService {

    User validateUser(String login, String pass);
    boolean registerUser(String login,String pass, String passConfirm);
    User getUserByLoginFromService(String login);
    ArrayList<User> getAllUsers();
    void removeUserById(int id);
    void setUserToAdmin(int id);
    void setUserToUser(int id);
    void setUserToVisitor(int id);

}
