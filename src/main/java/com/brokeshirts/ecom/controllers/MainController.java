package com.brokeshirts.ecom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class MainController {

    static ArrayList<String> featured_products = new ArrayList<>();

    @RequestMapping(value= "")
    public String index(Model model) {

        model.addAttribute("title", "Broke Shirts");
        model.addAttribute("featured", featured_products);

        return "index";

    }

    @RequestMapping(value="admin/add_product", method = RequestMethod.GET)
    public String displayAddProductForm(Model model) {

        model.addAttribute("title","Add Product");

        return "admin/add_product";

    }

    @RequestMapping(value="admin/add_product", method = RequestMethod.POST)
    public String processAddProductForm(@RequestParam String productName) {

        featured_products.add(productName);

        return "redirect:..";

    }

}
