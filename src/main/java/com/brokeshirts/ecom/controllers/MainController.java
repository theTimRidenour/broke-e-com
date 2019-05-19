package com.brokeshirts.ecom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.brokeshirts.ecom.models.Products;
import com.brokeshirts.ecom.models.Types;
import com.brokeshirts.ecom.models.CategoriesData;
import com.brokeshirts.ecom.models.ProductsData;
import com.brokeshirts.ecom.models.TypesData;

import java.util.ArrayList;

@Controller
public class MainController {

    @RequestMapping(value= "")
    public String index(Model model) {

        ArrayList<Products> featured = new ArrayList<>();

        int featuredSize = ProductsData.getAll().size();
        int counter = 0;

        while ( featuredSize > 0 ) {
            if (counter < 4 ) {
                featured.add(ProductsData.getById(featuredSize));
            }
                featuredSize--;
                counter++;
        }

        ArrayList<Products> shirts = new ArrayList<>();
        ArrayList<Products> shirtsToDisplay = new ArrayList<>();
        ArrayList<Types> shirtTypes = TypesData.getByCategory(1);

        for (Types type : shirtTypes) {
            for (Products productType : ProductsData.getByType(type.getTypeId())) {
                shirts.add(productType);
            }
        }

        counter = 0;

        for ( Products shirtCandidate : shirts) {

            if (counter < 4) {
                shirtsToDisplay.add(shirtCandidate);
            }

            counter++;
        }

        ArrayList<Products> pants = new ArrayList<>();
        ArrayList<Products> pantsToDisplay = new ArrayList<>();
        ArrayList<Types> pantsTypes = TypesData.getByCategory(2);

        for (Types type : pantsTypes) {
            for (Products productType : ProductsData.getByType(type.getTypeId())) {
                pants.add(productType);
            }
        }

        counter = 0;

        for ( Products candidate : pants) {

            if (counter < 4) {
                pantsToDisplay.add(candidate);
            }

            counter++;
        }

        ArrayList<Products> accessories = new ArrayList<>();
        ArrayList<Products> accessoriesToDisplay = new ArrayList<>();
        ArrayList<Types> accessoryTypes = TypesData.getByCategory(4);

        for (Types type : accessoryTypes) {
            for (Products productType : ProductsData.getByType(type.getTypeId())) {
                accessories.add(productType);
            }
        }

        counter = 0;

        for ( Products candidate : accessories) {

            if (counter < 4) {
                accessoriesToDisplay.add(candidate);
            }

            counter++;
        }

        model.addAttribute("title", "Broke Shirts");
        model.addAttribute("featured", featured);
        model.addAttribute("shirts", shirtsToDisplay);
        model.addAttribute("shirtMenu", shirtTypes);
        model.addAttribute("pants", pantsToDisplay);
        model.addAttribute("pantsMenu", pantsTypes);
        model.addAttribute("accessories", accessoriesToDisplay);
        model.addAttribute("accessoriesMenu", accessoryTypes);
        model.addAttribute("menuItems", CategoriesData.getAll());

        return "index";

    }

    @RequestMapping(value="terms")
    public String showTermsAndConditions(Model model) {

        model.addAttribute("title", "Terms and Conditions");
        model.addAttribute("menuItems", CategoriesData.getAll());


        return "terms";
    }

}
