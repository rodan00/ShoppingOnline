package AppSODB.controllers;


import AppSODB.dao.IProductDAO;
import AppSODB.model.Product;
import AppSODB.model.User;
import AppSODB.model.UserRole;
import AppSODB.services.IProductService;
import AppSODB.services.IUserService;
import AppSODB.session.SessionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;

@Controller
public class AdminController {

    @Autowired
    IUserService iUserService;

    @Autowired
    IProductService iProductService;

    @Resource
    SessionObject sessionObject;


    @RequestMapping(value="/adminpanel", method= RequestMethod.GET)
    public String showAdminPanel(Model model){
        if (sessionObject.getUser().getUserRole()!= UserRole.ADMIN){
            String komunikat="YOU DONT HAVE PERMISSION  - PLEASE LOGIN AS ADMIN";
            model.addAttribute("komunikat" , komunikat);
            return "mainPage";
        }
        return "adminPage";
    }


    @RequestMapping(value="/adminuser", method= RequestMethod.GET)
    public String showAdminUser(Model model){
        String komunikat;
        if (sessionObject.getUser().getUserRole()!= UserRole.ADMIN){
            komunikat="AACCES DENIED - PLEASE LOGIN AS ADMIN";
            model.addAttribute("komunikat" , komunikat);
            return "redirect:/main";
        }
        komunikat="CHANGE USER CREDENTIALS : USER/ADMIN/VISITOR";
        model.addAttribute("Komunikat" , komunikat);

        ArrayList<User> listOfUsers=iUserService.getAllUsers();
        model.addAttribute("users", listOfUsers);

        return "adminUser";
    }

    @RequestMapping(value="/removeuser/{id}", method=RequestMethod.POST)
    public String removeUser(@PathVariable int id, Model model){
        if (sessionObject.getUser().getUserRole()!= UserRole.ADMIN){
            String komunikat="YOU DONT HAVE PERMISSION  - PLEASE LOGIN AS ADMIN";
            model.addAttribute("komunikat" , komunikat);
            return "mainPage";
        }
        iUserService.removeUserById(id);
        return "redirect:/adminuser";
    }

    @RequestMapping(value="/setusertoadmin/{id}", method=RequestMethod.POST)
    public String actionUserToAdmin(@PathVariable int id, Model model){
        if (sessionObject.getUser().getUserRole()!= UserRole.ADMIN){
            String komunikat="YOU DONT HAVE PERMISSION  - PLEASE LOGIN AS ADMIN";
            model.addAttribute("komunikat" , komunikat);
            return "mainPage";
        }
        iUserService.setUserToAdmin(id);
        return "redirect:/adminuser";
    }

    @RequestMapping(value="/setusertouser/{id}", method=RequestMethod.POST)
    public String actionUserToUser(@PathVariable int id, Model model){
        if (sessionObject.getUser().getUserRole()!= UserRole.ADMIN){
            String komunikat="YOU DONT HAVE PERMISSION  - PLEASE LOGIN AS ADMIN";
            model.addAttribute("komunikat" , komunikat);
            return "mainPage";
        }
        iUserService.setUserToUser(id);
        return "redirect:/adminuser";
    }

    @RequestMapping(value="/setusertovisitor/{id}", method=RequestMethod.POST)
    public String actionUserToVisitor(@PathVariable int id, Model model){if (sessionObject.getUser().getUserRole()!= UserRole.ADMIN){
        String komunikat="YOU DONT HAVE PERMISSION  - PLEASE LOGIN AS ADMIN";
        model.addAttribute("komunikat" , komunikat);
        return "mainPage";
    }
        iUserService.setUserToVisitor(id);
        return "redirect:/adminuser";
    }



    @RequestMapping(value="/adminproduct", method= RequestMethod.GET)
    public String showAdminProduct(Model model){
        String komunikat;
        if (sessionObject.getUser().getUserRole()!= UserRole.ADMIN){
            komunikat="AACCES DENIED - PLEASE LOGIN AS ADMIN";
            model.addAttribute("komunikat" , komunikat);
            return "redirect:/main";
        }
        komunikat="";
        model.addAttribute("komunikat" , komunikat);

        ArrayList<Product> listOfProducts=iProductService.getListOfProductsFromService();
        model.addAttribute("products", listOfProducts);

        return "adminProduct";
    }

    @RequestMapping(value="/removeproduct/{id}", method=RequestMethod.POST)
    public String removeProduct(@PathVariable int id, Model model){
        if (sessionObject.getUser().getUserRole()!= UserRole.ADMIN){
            String komunikat="YOU DONT HAVE PERMISSION  - PLEASE LOGIN AS ADMIN";
            model.addAttribute("komunikat" , komunikat);
            return "mainPage";
        }
        iProductService.removeProductById(id);
        return "redirect:/adminproduct";
    }

    @RequestMapping(value="/addnewproduct", method=RequestMethod.POST)
    public String addNewProduct(@RequestParam String productname,
                                double productprice,
                                double productquant){
        iProductService.addNewProduct(productname,productprice,productquant);
        return "redirect:/adminproduct";
    }

    @RequestMapping(value="/updateproduct/{id}", method=RequestMethod.POST)
    public String updateProductQuant(@PathVariable int id, @RequestParam double updatedquant){
        iProductService.updateProductQuant(id,updatedquant);
        return "redirect:/adminproduct";
    }

}
