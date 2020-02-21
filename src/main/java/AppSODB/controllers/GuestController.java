package AppSODB.controllers;


import AppSODB.model.Product;
import AppSODB.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class GuestController {


    @Autowired
    IProductService iProductService;

    @GetMapping(value="/guest")
    public String showForGuest(Model model){
        ArrayList<Product> products=iProductService.getListOfProductsFromService();
        model.addAttribute("products", products);
        return "guestPage";
    }





}
