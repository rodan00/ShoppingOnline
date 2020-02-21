package AppSODB.controllers;

import AppSODB.dao.IProductDAO;
import AppSODB.model.BasketItem;
import AppSODB.model.Product;
import AppSODB.model.TransactItem;
import AppSODB.services.IProductService;
import AppSODB.services.ITransactService;
import AppSODB.services.impl.ProductService;
import AppSODB.session.SessionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@Controller
public class TransactController {

    @Autowired
    IProductService iProductService;

    @Autowired
    ITransactService iTransactService;

    @Resource
    SessionObject sessionObject;


    @GetMapping(value="/shopping")
    public String displayShoppingPage(Model model){
        ArrayList<Product> products=iProductService.getListOfProductsFromService();
        model.addAttribute("products", products);
        model.addAttribute("basket", sessionObject.getBasket());
        model.addAttribute("basketvalue", iTransactService.getBasketValue());
        return "shoppingPage";
    }

    @RequestMapping(value="/shopping", method = RequestMethod.POST)
    public String putProductToBasket(@RequestParam int productid, double orderquant ){
        iTransactService.addProductToBasket(productid,orderquant);
        return "redirect:/shopping";
    }

    @RequestMapping(value="/buybasket", method = RequestMethod.GET)
    public String makeTransaction(Model model){
        double basketValue = iTransactService.getBasketValue();
        model.addAttribute("basketvalue", basketValue  );
        iTransactService.buyBasket();
        return "buyPage";
    }

    @RequestMapping(value="/clearbasket", method = RequestMethod.GET)
    public String clearBasket(){
        System.out.println("czyszczę koszyk!");
        iProductService.restoreProductStoreFromBasket();
        return "redirect:/shopping";
    }

    @GetMapping(value="/history")
    public String showHistoryPage(Model model){
        ArrayList<TransactItem> listOfOrders=iTransactService.getListOfOrders();
        model.addAttribute("listoforders", listOfOrders);
    return "historyPage";
    }

    @RequestMapping(value ="/order/{orderid}", method=RequestMethod.GET )
    public String showOrderDetails(@PathVariable String orderid, Model model){
        ArrayList<BasketItem> listOfOrderItems=iTransactService.getListOfOrderItems(orderid);
                model.addAttribute("listoforderitems", listOfOrderItems);
        return "orderPage";
    }



}
