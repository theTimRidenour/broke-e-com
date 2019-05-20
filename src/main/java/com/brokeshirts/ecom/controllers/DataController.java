package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.models.data.*;
import com.brokeshirts.ecom.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Iterator;

@Controller
@RequestMapping(value="data")
public class DataController {

    @Autowired
    private AddressesDao addressesDao;
    private CartDao cartDao;
    private CategoriesDao categoriesDao;
    private ColorsDao colorsDao;
    private CustomersDao customersDao;
    private InventoryDao inventoryDao;
    private PhotosDao photosDao;
    private ProductsDao productsDao;
    private SizesDao sizesDao;
    private TypesDao typesDao;

    @RequestMapping(value="")
    public String showData(Model model) {

        ArrayList<Addresses> addresses = new ArrayList<>();
        ArrayList<Categories> categories = new ArrayList<>();
        ArrayList<Colors> colors = new ArrayList<>();
        ArrayList<Customers> customers = new ArrayList<>();
        ArrayList<Inventory> inventory = new ArrayList<>();
        ArrayList<Products> products = new ArrayList<>();
        ArrayList<Sizes> sizes = new ArrayList<>();
        ArrayList<Types> types = new ArrayList<>();

        if (addressesDao != null) {
            for (Addresses address : addressesDao.findAll()) {
                addresses.add(address);
            }
        }

        if (categoriesDao != null) {
            for (Categories category : categoriesDao.findAll()) {
                categories.add(category);
            }
        }

        if (colorsDao != null) {
            for (Colors color : colorsDao.findAll()) {
                colors.add(color);
            }
        }

        if (customersDao != null) {
            for (Customers customer : customersDao.findAll()) {
                customers.add(customer);
            }
        }

        if (inventoryDao != null) {
            for (Inventory item : inventoryDao.findAll()) {
                inventory.add(item);
            }
        }

        if (productsDao != null) {
            for (Products product : productsDao.findAll()) {
                products.add(product);
            }
        }

        if (sizesDao != null) {
            for (Sizes size : sizesDao.findAll()) {
                sizes.add(size);
            }
        }

        if (typesDao != null) {
            for (Types type : typesDao.findAll()) {
                types.add(type);
            }
        }

        model.addAttribute("title", "Data Log");
        model.addAttribute("menuItems", categories);
        model.addAttribute("addresses", addresses);
        model.addAttribute("colors", colors);
        model.addAttribute("customers", customers);
        model.addAttribute("inventory", inventory);
        model.addAttribute("products", products);
        model.addAttribute("sizes", sizes);
        model.addAttribute("types", types);

        return "data";
    }

    @RequestMapping(value="init")
    public String addInitData() {

        Categories categoryOne = new Categories();
        categoryOne.setName("shirts");
        categoriesDao.save(categoryOne);

        Types typeOne = new Types();
        typeOne.setCategoryId(1);
        typeOne.setName("unisex");
        typesDao.save(typeOne);

        Types typeTwo = new Types();
        typeTwo.setCategoryId(1);
        typeTwo.setName("mens");
        typesDao.save(typeTwo);

        Types typeThree = new Types();
        typeThree.setCategoryId(1);
        typeThree.setName("womens");
        typesDao.save(typeThree);

        Types typeFour = new Types();
        typeFour.setCategoryId(1);
        typeFour.setName("childs");
        typesDao.save(typeFour);

        Types typeFive = new Types();
        typeFive.setCategoryId(1);
        typeFive.setName("infant");
        typesDao.save(typeFive);

        Categories categoryTwo = new Categories();
        categoryTwo.setName("pants");
        categoriesDao.save(categoryTwo);

        Types typeSix = new Types();
        typeSix.setCategoryId(2);
        typeSix.setName("slacks");
        typesDao.save(typeSix);

        Types typeSeven = new Types();
        typeSeven.setCategoryId(2);
        typeSeven.setName("sweatpants");
        typesDao.save(typeSeven);

        Types typeEight = new Types();
        typeEight.setCategoryId(2);
        typeEight.setName("shorts");
        typesDao.save(typeEight);

        Categories categoryThree = new Categories();
        categoryThree.setName("hats");
        categoriesDao.save(categoryThree);

        Types typeNine = new Types();
        typeNine.setCategoryId(3);
        typeNine.setName("6-panel");
        typesDao.save(typeNine);

        Types typeTen = new Types();
        typeTen.setCategoryId(3);
        typeTen.setName("trucker");
        typesDao.save(typeTen);

        Categories categoryFour = new Categories();
        categoryFour.setName("accessories");
        categoriesDao.save(categoryFour);

        Types typeEleven = new Types();
        typeEleven.setCategoryId(4);
        typeEleven.setName("mugs");
        typesDao.save(typeEleven);

        Types typeTwelve = new Types();
        typeTwelve.setCategoryId(4);
        typeTwelve.setName("mouse pads");
        typesDao.save(typeTwelve);

        Customers customer = new Customers();
        customer.setEmail("JohnQPublic@site.com");
        customer.setFirstName("John");
        customer.setLastName("Public");
        customer.setPhoneArea(618);
        customer.setPhonePrefix(555);
        customer.setPhoneLine(1234);
        customersDao.save(customer);

        Addresses address = new Addresses();
        address.setAddressOne("123 Main St.");
        address.setCity("Belleville");
        address.setState("IL");
        address.setZipCode(62221);
        address.setCustomerId(1);
        addressesDao.save(address);

        Sizes sizeOne = new Sizes();
        sizeOne.setLongName("one size fits all");
        sizeOne.setShortName("one");
        sizesDao.save(sizeOne);

        Sizes sizeTwo = new Sizes();
        sizeTwo.setLongName("extra small");
        sizeTwo.setShortName("xs");
        sizesDao.save(sizeTwo);

        Sizes sizeThree = new Sizes();
        sizeThree.setLongName("small");
        sizeThree.setShortName("sm");
        sizesDao.save(sizeThree);

        Sizes sizeFour = new Sizes();
        sizeFour.setLongName("medium");
        sizeFour.setShortName("md");
        sizesDao.save(sizeFour);

        Sizes sizeFive = new Sizes();
        sizeFive.setLongName("large");
        sizeFive.setShortName("lg");
        sizesDao.save(sizeFive);

        Sizes sizeSix = new Sizes();
        sizeSix.setLongName("extra large");
        sizeSix.setShortName("xl");
        sizesDao.save(sizeSix);

        Sizes sizeSeven = new Sizes();
        sizeSeven.setLongName("2x-large");
        sizeSeven.setShortName("2xl");
        sizesDao.save(sizeSeven);

        Sizes sizeEight = new Sizes();
        sizeEight.setLongName("3x-large");
        sizeEight.setShortName("3xl");
        sizesDao.save(sizeEight);

        Sizes sizeNine = new Sizes();
        sizeNine.setLongName("4x-large");
        sizeNine.setShortName("4xl");
        sizesDao.save(sizeNine);

        Sizes sizeTen = new Sizes();
        sizeTen.setLongName("5x-large");
        sizeTen.setShortName("5xl");
        sizesDao.save(sizeTen);

        Colors colorOne = new Colors();
        colorOne.setName("black");
        colorOne.setHex("0d0d0d");
        colorsDao.save(colorOne);

        Colors colorTwo = new Colors();
        colorTwo.setName("white");
        colorTwo.setHex("f7f6f6");
        colorsDao.save(colorTwo);

        Colors colorThree = new Colors();
        colorThree.setName("red");
        colorThree.setHex("ff0000");
        colorsDao.save(colorThree);

        Colors colorFour = new Colors();
        colorFour.setName("heather grey");
        colorFour.setHex("c2c7c0");
        colorsDao.save(colorFour);

        Colors colorFive = new Colors();
        colorFive.setName("antique irish green");
        colorFive.setHex("40c575");
        colorsDao.save(colorFive);

        Colors colorSix = new Colors();
        colorSix.setName("indigo blue");
        colorSix.setHex("4b0082");
        colorsDao.save(colorSix);

        Products productOne = new Products();
        productOne.setName("Broke Logo");
        productOne.setTypeId(1);
        productsDao.save(productOne);

        Products productTwo = new Products();
        productTwo.setName("Broke Line");
        productTwo.setTypeId(1);
        productsDao.save(productTwo);

        Products productThree = new Products();
        productThree.setName("Bag Logo");
        productThree.setTypeId(1);
        productsDao.save(productThree);

        Products productFour = new Products();
        productFour.setName("Gold Limited");
        productFour.setTypeId(1);
        productsDao.save(productFour);

        Products productFive = new Products();
        productFive.setName("Broke Logo");
        productFive.setTypeId(7);
        productsDao.save(productFive);

        Products productSix = new Products();
        productSix.setName("Gold Limited");
        productSix.setTypeId(7);
        productsDao.save(productSix);

        Products productSeven = new Products();
        productSeven.setName("Broke Logo");
        productSeven.setTypeId(9);
        productsDao.save(productSeven);

        Products productEight = new Products();
        productEight.setName("Broke Logo");
        productEight.setTypeId(11);
        productsDao.save(productEight);

        Inventory itemOne = new Inventory();
        itemOne.setProductId(1);
        itemOne.setColorId(6);
        itemOne.setSizeId(5);
        itemOne.setPrice(15);
        itemOne.setSku("ibbrlglg");
        itemOne.setQuantity(3);
        inventoryDao.save(itemOne);

        Inventory itemTwo = new Inventory();
        itemTwo.setProductId(2);
        itemTwo.setColorId(2);
        itemTwo.setSizeId(5);
        itemTwo.setPrice(15);
        itemTwo.setSku("whbrlnlg");
        itemTwo.setQuantity(2);
        inventoryDao.save(itemTwo);

        Inventory itemThree = new Inventory();
        itemThree.setProductId(3);
        itemThree.setColorId(1);
        itemThree.setSizeId(8);
        itemThree.setPrice(18);
        itemThree.setSku("blbglg4x");
        itemThree.setQuantity(3);
        inventoryDao.save(itemThree);

        Inventory itemFour = new Inventory();
        itemFour.setProductId(4);
        itemFour.setColorId(1);
        itemFour.setSizeId(2);
        itemFour.setPrice(15);
        itemFour.setSku("bllmgoxs");
        itemFour.setQuantity(10);
        inventoryDao.save(itemFour);

        Inventory itemFive = new Inventory();
        itemFive.setProductId(5);
        itemFive.setColorId(6);
        itemFive.setSizeId(5);
        itemFive.setPrice(15);
        itemFive.setSku("ibgobrlg");
        itemFive.setQuantity(3);
        inventoryDao.save(itemFive);

        Inventory itemSix = new Inventory();
        itemSix.setProductId(6);
        itemSix.setColorId(3);
        itemSix.setSizeId(4);
        itemSix.setPrice(16);
        itemSix.setSku("kdienglc");
        itemSix.setQuantity(17);
        inventoryDao.save(itemSix);

        Inventory itemSeven = new Inventory();
        itemSeven.setProductId(7);
        itemSeven.setColorId(5);
        itemSeven.setSizeId(3);
        itemSeven.setPrice(19);
        itemSeven.setSku("oemcuelf");
        itemSeven.setQuantity(1);
        inventoryDao.save(itemSeven);

        Inventory itemEight = new Inventory();
        itemEight.setProductId(8);
        itemEight.setColorId(2);
        itemEight.setSizeId(1);
        itemEight.setPrice(9);
        itemEight.setSku("kcndiehc");
        itemEight.setQuantity(0);
        inventoryDao.save(itemEight);

        return "redirect:../data";
    }


}
