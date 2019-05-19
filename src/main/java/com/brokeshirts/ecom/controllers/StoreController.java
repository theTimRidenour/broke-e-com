package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.models.data.ProductsData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brokeshirts.ecom.models.Types;
import com.brokeshirts.ecom.models.Products;
import com.brokeshirts.ecom.models.data.CategoriesData;
import com.brokeshirts.ecom.models.data.TypesData;

import java.util.ArrayList;

@Controller
@RequestMapping(value="store")
public class StoreController {

    @RequestMapping(value="{categoryName}", method = RequestMethod.GET)
    public String category(@PathVariable String categoryName, Model model) {

        ArrayList<Types> categoryTypes = TypesData.getByCategory(CategoriesData.getByName(categoryName).getCategoryId());
        ArrayList<Products> allProducts = new ArrayList<>();

        for (Types type : categoryTypes) {
            for (Products productType : ProductsData.getByType(type.getTypeId())) {
                allProducts.add(productType);
            }
        }

        model.addAttribute("title", categoryName);
        model.addAttribute("types", categoryTypes);
        model.addAttribute("products", allProducts);
        model.addAttribute("menuItems", CategoriesData.getAll());


        return "store/category";
    }

    @RequestMapping(value="{categoryName}/{typeName}", method = RequestMethod.GET)
    public String type(@PathVariable String categoryName,@PathVariable String typeName, Model model) {

        System.out.println("categoryName : " + categoryName);
        System.out.println("typeName : " + typeName);

        Types oneType = TypesData.getByName(typeName);

        System.out.println("oneType : " + oneType);

        ArrayList<Products> allProducts = new ArrayList<>();

        for (Products productType : ProductsData.getByType(oneType.getTypeId())) {
            allProducts.add(productType);
        }

        System.out.println("allProducts : " + allProducts);


        model.addAttribute("title", categoryName + " : " + typeName);
        model.addAttribute("type", oneType);
        model.addAttribute("products", allProducts);
        model.addAttribute("menuItems", CategoriesData.getAll());


        return "store/type";
    }

}
