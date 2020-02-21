package AppSODB.controllers;

import AppSODB.model.User;
import AppSODB.services.IUserService;
import AppSODB.session.SessionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class UserController {


    @Autowired
    IUserService iUserService;

    @Resource
    SessionObject sessionObject;


    @GetMapping(value="/")
    public String redirectToLogin(){return "redirect:/login";}

    @GetMapping(value = "/login")
    public String loginGet (){
        return "loginPage";}

    @GetMapping(value="/register")
    public String registerGet(){
        return "registerPage";
    }

    @GetMapping(value="/main")
    public String showMainPage(Model model) {
        String userLogin=sessionObject.getUser().getLogin();
        String userRole=sessionObject.getUser().getUserRole().toString();

        String komunikat="HELLO  "+userRole+"\\"+userLogin+"  !";

        model.addAttribute("komunikat", komunikat);

        return "mainPage";
    }

    @GetMapping(value="/quit")
    public String showQuitPage(){
        return "quitPage";
    }

    @PostMapping(value = "/login")
    public String loginPost(@RequestParam String login, String pass){
        System.out.println(login +" - "+pass);
        User user=iUserService.validateUser(login,pass);
        if (user==null) {
            System.out.println("niezwalidowany");
            return "redirect:/login";
        }
        else {
            System.out.println("zwalidowany");
            return "redirect:/main";}
    }

    @PostMapping(value="/register")
    public String registrePost(@RequestParam String login, String pass, String passconfirm){
        boolean isRegistered = iUserService.registerUser(login,pass,passconfirm);
        if (isRegistered){
            return "redirect:/main";
        }
        return "redirect:quit";
    }

}
