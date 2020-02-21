package AppSODB.session;

import AppSODB.model.BasketItem;
import AppSODB.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;


@Component
@SessionScope
public class SessionObject {

    private User user=new User();
    private ArrayList<BasketItem> basket=new ArrayList<>();
    private boolean logged=false;


    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public User getUser() {return user; }

    public void setUser(User user) {this.user = user;}

    public ArrayList<BasketItem> getBasket() {return basket;}

    public void setBasket(ArrayList<BasketItem> basket) {this.basket = basket;}

}
