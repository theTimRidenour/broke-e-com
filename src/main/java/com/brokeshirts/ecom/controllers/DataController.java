package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.models.data.*;
import com.brokeshirts.ecom.models.data.old.*;
import com.brokeshirts.ecom.models.old.*;
import com.brokeshirts.ecom.models.old.Addresses;
import com.brokeshirts.ecom.models.old.Categories;
import com.brokeshirts.ecom.models.old.Colors;
import com.brokeshirts.ecom.models.old.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="data")
public class DataController {

    @Autowired
    private AddressesDao addressesDao;

    @RequestMapping(value="")
    public String showData(Model model) {

        model.addAttribute("title", "Data Log");
        model.addAttribute("menuItems", CategoriesData.getAll());
        model.addAttribute("addresses", addressesDao.findAll());
        model.addAttribute("categories", CategoriesData.getAll());
        model.addAttribute("colors", ColorsData.getAll());
        model.addAttribute("customers", CustomersData.getAll());
        model.addAttribute("inventory", InventoryData.getAll());
        model.addAttribute("products", ProductsData.getAll());
        model.addAttribute("sizes", SizesData.getAll());
        model.addAttribute("types", TypesData.getAll());


        return "data";
    }

    @RequestMapping(value="init")
    public String addInitData() {

        Categories categoryOne = new Categories();
        categoryOne.setName("shirts");
        CategoriesData.add(categoryOne);

        Types typeOne = new Types();
        typeOne.setCategoryId(1);
        typeOne.setName("unisex");
        TypesData.add(typeOne);

        Types typeTwo = new Types();
        typeTwo.setCategoryId(1);
        typeTwo.setName("mens");
        TypesData.add(typeTwo);

        Types typeThree = new Types();
        typeThree.setCategoryId(1);
        typeThree.setName("womens");
        TypesData.add(typeThree);

        Types typeFour = new Types();
        typeFour.setCategoryId(1);
        typeFour.setName("childs");
        TypesData.add(typeFour);

        Types typeFive = new Types();
        typeFive.setCategoryId(1);
        typeFive.setName("infant");
        TypesData.add(typeFive);

        Categories categoryTwo = new Categories();
        categoryTwo.setName("pants");
        CategoriesData.add(categoryTwo);

        Types typeSix = new Types();
        typeSix.setCategoryId(2);
        typeSix.setName("slacks");
        TypesData.add(typeSix);

        Types typeSeven = new Types();
        typeSeven.setCategoryId(2);
        typeSeven.setName("sweatpants");
        TypesData.add(typeSeven);

        Types typeEight = new Types();
        typeEight.setCategoryId(2);
        typeEight.setName("shorts");
        TypesData.add(typeEight);

        Categories categoryThree = new Categories();
        categoryThree.setName("hats");
        CategoriesData.add(categoryThree);

        Types typeNine = new Types();
        typeNine.setCategoryId(3);
        typeNine.setName("6-panel");
        TypesData.add(typeNine);

        Types typeTen = new Types();
        typeTen.setCategoryId(3);
        typeTen.setName("trucker");
        TypesData.add(typeTen);

        Categories categoryFour = new Categories();
        categoryFour.setName("accessories");
        CategoriesData.add(categoryFour);

        Types typeEleven = new Types();
        typeEleven.setCategoryId(4);
        typeEleven.setName("mugs");
        TypesData.add(typeEleven);

        Types typeTwelve = new Types();
        typeTwelve.setCategoryId(4);
        typeTwelve.setName("mouse pads");
        TypesData.add(typeTwelve);

        Customers customer = new Customers();
        customer.setEmail("JohnQPublic@site.com");
        customer.setFirstName("John");
        customer.setLastName("Public");
        customer.setPhoneArea(618);
        customer.setPhonePrefix(555);
        customer.setPhoneLine(1234);
        CustomersData.add(customer);

        Addresses address = new Addresses();
        address.setAddressOne("123 Main St.");
        address.setCity("Belleville");
        address.setStateCode("IL");
        address.setZipCode(62221);
        address.setCustomerId(1);

        // AddressesData.add(address);

        Sizes sizeOne = new Sizes();
        sizeOne.setLongName("one size fits all");
        sizeOne.setShortName("one");
        SizesData.add(sizeOne);

        Sizes sizeTwo = new Sizes();
        sizeTwo.setLongName("extra small");
        sizeTwo.setShortName("xs");
        SizesData.add(sizeTwo);

        Sizes sizeThree = new Sizes();
        sizeThree.setLongName("small");
        sizeThree.setShortName("sm");
        SizesData.add(sizeThree);

        Sizes sizeFour = new Sizes();
        sizeFour.setLongName("medium");
        sizeFour.setShortName("md");
        SizesData.add(sizeFour);

        Sizes sizeFive = new Sizes();
        sizeFive.setLongName("large");
        sizeFive.setShortName("lg");
        SizesData.add(sizeFive);

        Sizes sizeSix = new Sizes();
        sizeSix.setLongName("extra large");
        sizeSix.setShortName("xl");
        SizesData.add(sizeSix);

        Sizes sizeSeven = new Sizes();
        sizeSeven.setLongName("2x-large");
        sizeSeven.setShortName("2xl");
        SizesData.add(sizeSeven);

        Sizes sizeEight = new Sizes();
        sizeEight.setLongName("3x-large");
        sizeEight.setShortName("3xl");
        SizesData.add(sizeEight);

        Sizes sizeNine = new Sizes();
        sizeNine.setLongName("4x-large");
        sizeNine.setShortName("4xl");
        SizesData.add(sizeNine);

        Sizes sizeTen = new Sizes();
        sizeTen.setLongName("5x-large");
        sizeTen.setShortName("5xl");
        SizesData.add(sizeTen);

        Colors colorOne = new Colors();
        colorOne.setName("black");
        colorOne.setHex("0d0d0d");
        ColorsData.add(colorOne);

        Colors colorTwo = new Colors();
        colorTwo.setName("white");
        colorTwo.setHex("f7f6f6");
        ColorsData.add(colorTwo);

        Colors colorThree = new Colors();
        colorThree.setName("red");
        colorThree.setHex("ff0000");
        ColorsData.add(colorThree);

        Colors colorFour = new Colors();
        colorFour.setName("heather grey");
        colorFour.setHex("c2c7c0");
        ColorsData.add(colorFour);

        Colors colorFive = new Colors();
        colorFive.setName("antique irish green");
        colorFive.setHex("40c575");
        ColorsData.add(colorFive);

        Colors colorSix = new Colors();
        colorSix.setName("indigo blue");
        colorSix.setHex("4b0082");
        ColorsData.add(colorSix);

        Products productOne = new Products();
        productOne.setName("Broke Logo");
        productOne.setTypeId(1);
        ProductsData.add(productOne);

        Products productTwo = new Products();
        productTwo.setName("Broke Line");
        productTwo.setTypeId(1);
        ProductsData.add(productTwo);

        Products productThree = new Products();
        productThree.setName("Bag Logo");
        productThree.setTypeId(1);
        ProductsData.add(productThree);

        Products productFour = new Products();
        productFour.setName("Gold Limited");
        productFour.setTypeId(1);
        ProductsData.add(productFour);

        Products productFive = new Products();
        productFive.setName("Broke Logo");
        productFive.setTypeId(7);
        ProductsData.add(productFive);

        Products productSix = new Products();
        productSix.setName("Gold Limited");
        productSix.setTypeId(7);
        ProductsData.add(productSix);

        Products productSeven = new Products();
        productSeven.setName("Broke Logo");
        productSeven.setTypeId(9);
        ProductsData.add(productSeven);

        Products productEight = new Products();
        productEight.setName("Broke Logo");
        productEight.setTypeId(11);
        ProductsData.add(productEight);

        Inventory itemOne = new Inventory();
        itemOne.setProductId(1);
        itemOne.setColorId(6);
        itemOne.setSizeId(5);
        itemOne.setPrice(15);
        itemOne.setSku("ibbrlglg");
        itemOne.setQuantity(3);
        InventoryData.add(itemOne);

        Inventory itemTwo = new Inventory();
        itemTwo.setProductId(2);
        itemTwo.setColorId(2);
        itemTwo.setSizeId(5);
        itemTwo.setPrice(15);
        itemTwo.setSku("whbrlnlg");
        itemTwo.setQuantity(2);
        InventoryData.add(itemTwo);

        Inventory itemThree = new Inventory();
        itemThree.setProductId(3);
        itemThree.setColorId(1);
        itemThree.setSizeId(8);
        itemThree.setPrice(18);
        itemThree.setSku("blbglg4x");
        itemThree.setQuantity(3);
        InventoryData.add(itemThree);

        Inventory itemFour = new Inventory();
        itemFour.setProductId(4);
        itemFour.setColorId(1);
        itemFour.setSizeId(2);
        itemFour.setPrice(15);
        itemFour.setSku("bllmgoxs");
        itemFour.setQuantity(10);
        InventoryData.add(itemFour);

        Inventory itemFive = new Inventory();
        itemFive.setProductId(5);
        itemFive.setColorId(6);
        itemFive.setSizeId(5);
        itemFive.setPrice(15);
        itemFive.setSku("ibgobrlg");
        itemFive.setQuantity(3);
        InventoryData.add(itemFive);

        Inventory itemSix = new Inventory();
        itemSix.setProductId(6);
        itemSix.setColorId(3);
        itemSix.setSizeId(4);
        itemSix.setPrice(16);
        itemSix.setSku("kdienglc");
        itemSix.setQuantity(17);
        InventoryData.add(itemSix);

        Inventory itemSeven = new Inventory();
        itemSeven.setProductId(7);
        itemSeven.setColorId(5);
        itemSeven.setSizeId(3);
        itemSeven.setPrice(19);
        itemSeven.setSku("oemcuelf");
        itemSeven.setQuantity(1);
        InventoryData.add(itemSeven);

        Inventory itemEight = new Inventory();
        itemEight.setProductId(8);
        itemEight.setColorId(2);
        itemEight.setSizeId(1);
        itemEight.setPrice(9);
        itemEight.setSku("kcndiehc");
        itemEight.setQuantity(0);
        InventoryData.add(itemEight);

        return "redirect:../data";
    }


}
