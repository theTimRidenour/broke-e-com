package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Menus;
import com.brokeshirts.ecom.functions.Store;
import com.brokeshirts.ecom.models.Products;
import com.brokeshirts.ecom.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

import static com.brokeshirts.ecom.functions.Data.replaceUnderscore;

@Controller
@RequestMapping(value="store")
public class StoreController {

    @Autowired
    CategoriesDao categoriesDao;

    @Autowired
    TypesDao typesDao;

    @Autowired
    ProductsDao productsDao;

    @Autowired
    PhotosDao photosDao;

    @Autowired
    ColorsDao colorsDao;

    @Autowired
    SizesDao sizesDao;

//// DISPLAY FORMS

    // DISPLAY PRODUCTS IN SINGLE CATEGORY
    @RequestMapping(value="{categoryName}", method = RequestMethod.GET)
    public String showCat(@PathVariable String categoryName, Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        model.addAttribute("title", categoryName);
        model.addAttribute("types", Store.allCatTypes(replaceUnderscore(categoryName), typesDao, categoriesDao));
        model.addAttribute("products", Store.limitedProductListByType(replaceUnderscore(categoryName), typesDao, categoriesDao, productsDao, photosDao));
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));

        return "store/category";
    }

    // DISPLAY PRODUCTS IN SINGLE SUB-CATEGORY
    @RequestMapping(value="{categoryName}/{typeName}", method = RequestMethod.GET)
    public String showType(@PathVariable String categoryName,@PathVariable String typeName, Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        model.addAttribute("title", replaceUnderscore(categoryName) + " : " + replaceUnderscore(typeName));
        model.addAttribute("type", Store.oneTypeByName(replaceUnderscore(typeName), typesDao));
        model.addAttribute("products", Store.oneCatProducts(replaceUnderscore(typeName), productsDao, typesDao, photosDao));
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));

        return "store/type";
    }

    // DISPLAY A PRODUCT
    @RequestMapping(value = "product/{productId}", method = RequestMethod.GET)
    public String showProduct(@PathVariable int productId, Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        System.out.println(cartItems);

        Products product = productsDao.findById(productId).orElse(new Products());

        model.addAttribute("title", product.getName());
        model.addAttribute("activate", "PRODUCT_PAGE");
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("prodColors", Store.prodColorsImages(product, colorsDao, photosDao));
        model.addAttribute("prodSizes", Store.prodSizes(product, sizesDao));
        model.addAttribute("prodPrices", Store.priceList(product));
        model.addAttribute("prodPriceRange", Store.maxMinPrice(product));
        model.addAttribute("prodInfo", Store.productInfo(product));
        model.addAttribute("cartCnt", cartItems);

        return "store/product";
    }
}
