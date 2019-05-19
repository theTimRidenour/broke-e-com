package com.brokeshirts.ecom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brokeshirts.ecom.models.CategoriesData;
import com.brokeshirts.ecom.models.Products;
import com.brokeshirts.ecom.models.ProductsData;

@Controller
@RequestMapping(value="admin")
public class AdminController {

    @RequestMapping(value="add_product", method = RequestMethod.GET)
    public String displayAddProductForm(Model model) {

        model.addAttribute("title","Add Product");
        model.addAttribute("menuItems", CategoriesData.getAll());


        return "admin/add_product";

    }

    @RequestMapping(value="add_product", method = RequestMethod.POST)
    public String processAddProductForm(@ModelAttribute Products newProduct) {

        ProductsData.add(newProduct);

        return "redirect:..";

    }

}
