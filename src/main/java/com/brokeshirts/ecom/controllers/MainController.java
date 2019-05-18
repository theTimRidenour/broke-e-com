package com.brokeshirts.ecom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brokeshirts.ecom.models.Products;
import com.brokeshirts.ecom.models.ProductsData;

@Controller
public class MainController {

    @RequestMapping(value= "")
    public String index(Model model) {

        model.addAttribute("title", "Broke Shirts");
        model.addAttribute("featured", ProductsData.getAll());

        return "index";

    }

    @RequestMapping(value="admin/add_product", method = RequestMethod.GET)
    public String displayAddProductForm(Model model) {

        model.addAttribute("title","Add Product");

        return "admin/add_product";

    }

    @RequestMapping(value="admin/add_product", method = RequestMethod.POST)
    public String processAddProductForm(@ModelAttribute Products newProduct) {

        ProductsData.add(newProduct);

        return "redirect:..";

    }

}
