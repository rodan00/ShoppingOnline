package AppSODB.services.impl;

import AppSODB.dao.IUserDAO;
import AppSODB.model.User;
import AppSODB.model.UserRole;
import AppSODB.services.IUserService;
import AppSODB.session.SessionObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class UserService implements IUserService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserDAO iUserDAO;

    @Override
    public User validateUser(String login, String pass) {
        User user = iUserDAO.getUserByLogin(login);
        //System.out.println(user);
       // System.out.println(user.getPass());
       //System.out.println(pass);
        String hashedPass = DigestUtils.md5Hex(pass);
        System.out.println(hashedPass);

        if (hashedPass.equals(user.getPass())) {
            System.out.println(user);
            System.out.println("zwracam usera");

            sessionObject.getUser().setId(user.getId());
            sessionObject.getUser().setLogin(user.getLogin());
            sessionObject.getUser().setUserRole(user.getUserRole());
            return user;
        }
        System.out.println("zwracam null");
        return null;
    }

    @Override
    public boolean registerUser(String login, String pass, String passConfirm) {
        if (pass.equals(passConfirm)) {
           User user = iUserDAO.getUserByLogin(login);

            if (user == null) {
               user=new User();
                user.setLogin(login);
                user.setPass(DigestUtils.md5Hex(pass));
                user.setUserRole(UserRole.USER);
                iUserDAO.persistUser(user);

                sessionObject.getUser().setId(user.getId());
                sessionObject.getUser().setLogin(user.getLogin());
                sessionObject.getUser().setUserRole(user.getUserRole());
                return true;
           }
        }
    return false;
    }

    @Override
    public User getUserByLoginFromService(String login){
        User user=iUserDAO.getUserByLogin(login);
        return user;
    }

    @Override
    public ArrayList<User> getAllUsers(){
        return iUserDAO.getAllUsersFromDB();
    }

    @Override
    public void removeUserById(int id){
        iUserDAO.removeUserFromDB(id);
    }

    @Override
    public void setUserToAdmin(int id){
        User user=iUserDAO.getUserById(id);
        System.out.println(user.getId()+" "+user.getUserRole());
        user.setUserRole(UserRole.ADMIN);
        System.out.println("tu mnie nie ma");
        iUserDAO.persistUser(user);
    }

    @Override
    public void setUserToUser(int id){
        User user=iUserDAO.getUserById(id);
        user.setUserRole(UserRole.USER);
        iUserDAO.persistUser(user);
    }

    @Override
    public void setUserToVisitor(int id){
        User user=iUserDAO.getUserById(id);
        user.setUserRole(UserRole.VISITOR);
        iUserDAO.persistUser(user);
    }

}