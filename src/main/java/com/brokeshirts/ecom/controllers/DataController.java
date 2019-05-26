package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Menus;
import com.brokeshirts.ecom.models.data.*;
import com.brokeshirts.ecom.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping(value="data")
public class DataController {

    @Autowired
    private AddressesDao addressesDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CategoriesDao categoriesDao;

    @Autowired
    private ColorsDao colorsDao;

    @Autowired
    private CustomersDao customersDao;

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private PhotosDao photosDao;

    @Autowired
    private ProductsDao productsDao;

    @Autowired
    private SizesDao sizesDao;

    @Autowired
    private TypesDao typesDao;

// ADD/REMOVE DATA

    // ADD CATEGORY
    @RequestMapping(value="add/category", method = RequestMethod.POST)
    public String addCategory(@RequestParam("categoryName") String categoryName) {

        if (categoryName.isEmpty()) {
            return "redirect:/admin/categories";
        }

        Categories cat = new Categories();
        cat.setName(categoryName);
        cat.setHidden("no");
        cat.setArchive("no");
        categoriesDao.save(cat);

        Categories newCat = new Categories();

        for (Categories findCat : categoriesDao.findAll()) {
            if (findCat.getName().equals(categoryName)) {
                newCat = findCat;
            }
        }

        int sortId = 0;

        for (Categories catSortMax : categoriesDao.findAll()) {
            if (sortId < catSortMax.getSortId()) {
                sortId = catSortMax.getSortId();
            }
        }

        sortId++;

        newCat.setSortId(sortId);
        categoriesDao.save(newCat);

        return "redirect:/admin/categories";
    }

    // ADD SUBCATEGORY
    @RequestMapping(value="add/subcategory", method = RequestMethod.POST)
    public String addType(@RequestParam("typeName") String typeName, @RequestParam("categoryId") int categoryId) {

        if (typeName.isEmpty() || categoryId == 0) {
            return "redirect:/admin/categories";
        }

        Types type = new Types();
        type.setName(typeName);
        type.setHidden("no");
        type.setArchive("no");
        type.setCatArchive("no");
        type.setCategoryId(categoryId);
        typesDao.save(type);

        Types newType = new Types();

        for (Types findType : typesDao.findAll()) {
            if (findType.getName().equals(typeName)) {
                newType = findType;
            }
        }

        int sortId = 0;

        for (Types typeSortMax : typesDao.findAll()) {
            if (typeSortMax.getCategoryId() == categoryId) {
                if (sortId < typeSortMax.getSortId()) {
                    sortId = typeSortMax.getSortId();
                }
            }
        }

        sortId++;

        newType.setSortId(sortId);
        typesDao.save(newType);

        return "redirect:/admin/categories";
    }

    // ADD SIZE
    @RequestMapping(value="add/size", method = RequestMethod.POST)
    public String addSize(@RequestParam String longName, @RequestParam String shortName) {
        Sizes newSize = new Sizes();
        int maxSortId = 0;

        newSize.setLongName(longName);
        newSize.setShortName(shortName);
        newSize.setArchive("no");
        newSize.setHidden("no");
        sizesDao.save(newSize);

        for (Sizes size : sizesDao.findAll()) {
            if (maxSortId < size.getSortId()) {
                maxSortId = size.getSortId();
            }
        }

        for (Sizes size : sizesDao.findAll()) {
            if (size.getLongName().equals(longName) && size.getShortName().equals(shortName)) {
                size.setSortId(maxSortId + 1);
                sizesDao.save(size);
            }
        }

        return "redirect:/admin/sizes";
    }

    // DELETE CATEGORY AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/cat/{id}")
    public String delCat(@PathVariable int id) {

        Categories removeCat = categoriesDao.findOne(id);

        for (Types type: typesDao.findAll()) {
            if (type.getCategoryId() == removeCat.getId()) {
                typesDao.delete(type);
            }
        }

        categoriesDao.delete(removeCat);

        return "redirect:/admin/archive";
    }

    // DELETE SUBCATEGORY AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/type/{id}")
    public String delType(@PathVariable int id) {

        Types removeType = typesDao.findOne(id);

        typesDao.delete(removeType);

        return "redirect:/admin/archive";
    }

    // DELETE SIZE AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/size/{id}")
    public String delSize(@PathVariable int id) {

        Sizes removeSize = sizesDao.findOne(id);

        sizesDao.delete(removeSize);

        return "redirect:/admin/archive";
    }

// TEMP DATA FORMS

    // SHOW ALL DATABASE DATA
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
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("addresses", addresses);
        model.addAttribute("colors", colors);
        model.addAttribute("customers", customers);
        model.addAttribute("inventory", inventory);
        model.addAttribute("products", products);
        model.addAttribute("sizes", sizes);
        model.addAttribute("types", types);

        return "data";
    }

    // INSERT DEMO DATA
    @RequestMapping(value="init")
    public String addInitData() {

        Categories categoryOne = new Categories();
        categoryOne.setName("shirts");
        categoryOne.setArchive("no");
        categoryOne.setHidden("no");
        categoriesDao.save(categoryOne);

        ArrayList<Categories> catList = new ArrayList<>();

        for (Categories catAll : categoriesDao.findAll()) {
            catList.add(catAll);
        }

        for (Categories cat : catList) {
            if (cat.getId() == catList.size()) {
                cat.setSortId(2);
            }
        }

        Types typeOne = new Types();
        typeOne.setCategoryId(1);
        typeOne.setName("unisex");
        typeOne.setHidden("no");
        typeOne.setArchive("no");
        typeOne.setCatArchive("no");
        typeOne.setSortId(1);
        typesDao.save(typeOne);

        Types typeTwo = new Types();
        typeTwo.setCategoryId(1);
        typeTwo.setName("mens");
        typeTwo.setHidden("no");
        typeTwo.setArchive("no");
        typeTwo.setCatArchive("no");
        typeTwo.setSortId(2);
        typesDao.save(typeTwo);

        Types typeThree = new Types();
        typeThree.setCategoryId(1);
        typeThree.setName("womens");
        typeThree.setHidden("no");
        typeThree.setArchive("no");
        typeThree.setCatArchive("no");
        typeThree.setSortId(3);
        typesDao.save(typeThree);

        Types typeFour = new Types();
        typeFour.setCategoryId(1);
        typeFour.setName("childs");
        typeFour.setHidden("no");
        typeFour.setArchive("no");
        typeFour.setCatArchive("no");
        typeFour.setSortId(4);
        typesDao.save(typeFour);

        Types typeFive = new Types();
        typeFive.setCategoryId(1);
        typeFive.setName("infant");
        typeFive.setHidden("no");
        typeFive.setArchive("no");
        typeFive.setCatArchive("no");
        typeFive.setSortId(5);
        typesDao.save(typeFive);

        Categories categoryTwo = new Categories();
        categoryTwo.setName("pants");
        categoryTwo.setArchive("no");
        categoryTwo.setHidden("no");
        categoriesDao.save(categoryTwo);

        ArrayList<Categories> catList2 = new ArrayList<>();

        for (Categories catAll : categoriesDao.findAll()) {
            catList2.add(catAll);
        }

        for (Categories cat : catList2) {
            if (cat.getId() == catList2.size()) {
                cat.setSortId(1);
            }
        }

        Types typeSix = new Types();
        typeSix.setCategoryId(2);
        typeSix.setName("slacks");
        typeSix.setHidden("no");
        typeSix.setArchive("no");
        typeSix.setCatArchive("no");
        typeSix.setSortId(1);
        typesDao.save(typeSix);

        Types typeSeven = new Types();
        typeSeven.setCategoryId(2);
        typeSeven.setName("sweatpants");
        typeSeven.setHidden("no");
        typeSeven.setArchive("no");
        typeSeven.setCatArchive("no");
        typeSeven.setSortId(2);
        typesDao.save(typeSeven);

        Types typeEight = new Types();
        typeEight.setCategoryId(2);
        typeEight.setName("shorts");
        typeEight.setHidden("no");
        typeEight.setArchive("no");
        typeEight.setCatArchive("no");
        typeEight.setSortId(3);
        typesDao.save(typeEight);

        Categories categoryThree = new Categories();
        categoryThree.setName("hats");
        categoryThree.setArchive("no");
        categoryThree.setHidden("no");
        categoriesDao.save(categoryThree);

        ArrayList<Categories> catList3 = new ArrayList<>();

        for (Categories catAll : categoriesDao.findAll()) {
            catList3.add(catAll);
        }

        for (Categories cat : catList3) {
            if (cat.getId() == catList3.size()) {
                cat.setSortId(3);
            }
        }

        Types typeNine = new Types();
        typeNine.setCategoryId(3);
        typeNine.setName("6-panel");
        typeNine.setHidden("no");
        typeNine.setArchive("no");
        typeNine.setCatArchive("no");
        typeNine.setSortId(1);
        typesDao.save(typeNine);

        Types typeTen = new Types();
        typeTen.setCategoryId(3);
        typeTen.setName("trucker");
        typeTen.setHidden("no");
        typeTen.setArchive("no");
        typeTen.setCatArchive("no");
        typeTen.setSortId(2);
        typesDao.save(typeTen);

        Categories categoryFour = new Categories();
        categoryFour.setName("accessories");
        categoryFour.setArchive("no");
        categoryFour.setHidden("no");
        categoriesDao.save(categoryFour);

        ArrayList<Categories> catList4 = new ArrayList<>();

        for (Categories catAll : categoriesDao.findAll()) {
            catList4.add(catAll);
        }

        for (Categories cat : catList4) {
            if (cat.getId() == catList4.size()) {
                cat.setSortId(4);
            }
        }

        Types typeEleven = new Types();
        typeEleven.setCategoryId(4);
        typeEleven.setName("mugs");
        typeEleven.setHidden("no");
        typeEleven.setArchive("no");
        typeEleven.setCatArchive("no");
        typeEleven.setSortId(1);
        typesDao.save(typeEleven);

        Types typeTwelve = new Types();
        typeTwelve.setCategoryId(4);
        typeTwelve.setName("mouse pads");
        typeTwelve.setHidden("no");
        typeTwelve.setArchive("no");
        typeTwelve.setCatArchive("no");
        typeTwelve.setSortId(2);
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
