package AppSODB.model;

public class User {
    private int id;
    private String login;
    private String pass;
    private UserRole userRole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


    @Override
    public String toString() {
        return "User: " + "id=" + id +
                ", login='" + login  +
                ", userRole='" + userRole;
    }
}
