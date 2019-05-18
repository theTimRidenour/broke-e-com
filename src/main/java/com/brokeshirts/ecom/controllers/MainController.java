package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.models.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import com.brokeshirts.ecom.models.Product;
import com.brokeshirts.ecom.models.ProductData;

@Controller
public class MainController {

    @RequestMapping(value= "")
    public String index(Model model) {

        model.addAttribute("title", "Broke Shirts");
        model.addAttribute("featured", ProductData.getAll());

        return "index";

    }

    @RequestMapping(value="admin/add_product", method = RequestMethod.GET)
    public String displayAddProductForm(Model model) {

        model.addAttribute("title","Add Product");

        return "admin/add_product";

    }

    @RequestMapping(value="admin/add_product", method = RequestMethod.POST)
    public String processAddProductForm(@ModelAttribute Product newProduct) {

        ProductData.add(newProduct);

        return "redirect:..";

    }

}
