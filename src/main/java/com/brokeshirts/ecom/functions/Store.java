package com.brokeshirts.ecom.functions;

import com.brokeshirts.ecom.models.Categories;
import com.brokeshirts.ecom.models.Inventory;
import com.brokeshirts.ecom.models.Products;
import com.brokeshirts.ecom.models.Types;
import com.brokeshirts.ecom.models.data.CategoriesDao;
import com.brokeshirts.ecom.models.data.ProductsDao;
import com.brokeshirts.ecom.models.data.TypesDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Store {

//// FIND BY NAME

    // FIND SINGLE SUB-CATEGORY BY NAME
    public static Types oneTypeByName(String typeName, TypesDao typesDao) {
        Types oneType = null;

        for (Types type : typesDao.findAll()) {
            if (type.getName().equals(typeName)) {
                oneType = type;
            }
        }

        return oneType;
    }

    // FIND SINGLE CATEGORY BY NAME
    public static Categories oneCatByName(String categoryName, CategoriesDao categoriesDao) {

        Categories oneCategory = null;

        for (Categories cat : categoriesDao.findAll()) {
            if (cat.getName().equals(categoryName)) {
                oneCategory = cat;
            }
        }

        return oneCategory;
    }


//// CREATE LIST

    // LIST ALL PRODUCTS FROM SINGLE CATEGORY BY CATEGORY NAME
    public static ArrayList<Products> oneCatProducts(String typeName, ProductsDao productsDao, TypesDao typesDao) {

        ArrayList<Products> allProducts = new ArrayList<>();
        Types oneType = oneTypeByName(typeName, typesDao);

        for (Products product : productsDao.findAll()) {
            if (product.getType() == oneType) {
                allProducts.add(product);
            }
        }

        return allProducts;
    }

    // LIST ALL SUB-CATEGORIES FROM SINGLE CATEGORY BY CATEGORY NAME
    public static ArrayList<Types> allCatTypes(String categoryName, TypesDao typesDao, CategoriesDao categoriesDao) {

        ArrayList<Types> categoryTypes = new ArrayList<>();
        Categories oneCategory = oneCatByName(categoryName, categoriesDao);

        for (Types type : typesDao.findAll()) {
            if (type.getCategory() == oneCategory) {
                categoryTypes.add(type);
            }
        }

        return categoryTypes;
    }

    // LIST FIRST FOUR PRODUCTS FROM EACH SUB-CATEGORY OF A SINGLE CATEGORY BY CATEGORY NAME
    public static ArrayList<Products> limitedProductListByType(String categoryName, TypesDao typesDao, CategoriesDao categoriesDao, ProductsDao productsDao) {

        ArrayList<Products> allProducts = new ArrayList<>();
        ArrayList<Types> categoryTypes = allCatTypes(categoryName, typesDao, categoriesDao);

        int limit = 0;
        for (Types type : categoryTypes) {
            limit = 4;
            for (Products product : productsDao.findAll()) {
                if (limit > 0) {
                    if (product.getType() == type) {
                        allProducts.add(product);
                        limit--;
                    }
                }
            }
        }

        return allProducts;
    }

    // LIST OF FEATURED PRODUCTS
    public static ArrayList<HashMap<Products, HashMap<Float, Float>>> featuredProducts(ProductsDao productsDao) {

        ArrayList<Products> revProds = revProducts(productsDao);
        ArrayList<Products> availableProds = availableProducts(revProds);
        ArrayList<HashMap<Products, HashMap<Float, Float>>> productsWithPriceRange = productPriceRange(availableProds);
        ArrayList<HashMap<Products, HashMap<Float, Float>>> featured = new ArrayList<>();
        int counter = 0;

        if (availableProds.size() < 4) {
            counter = availableProds.size();
        } else {
            counter = 4;
        }

        for (HashMap<Products, HashMap<Float, Float>> item : productsWithPriceRange) {
            if (counter > 0) {
                featured.add(item);
                counter--;
            }
        }

        return featured;
    }

    // LIST OF LAST FOUR PRODUCTS FROM EACH SUB-CATEGORY
    public static ArrayList<Products> revProdsByType(TypesDao typesDao, ProductsDao productsDao) {

        ArrayList<Products> indexProducts = new ArrayList<>();
        int counter = 0;

        for (Types type : typesDao.findAll()) {
            counter = 4;
            for (Products product : revProducts(productsDao)) {
                if (counter > 0) {
                    if (product.getType() == type) {
                        indexProducts.add(product);
                        counter--;
                    }
                }
            }
        }

        return indexProducts;
    }

    // LIST OF AVAILABLE PRODUCTS
    private static ArrayList<Products> availableProducts(ArrayList<Products> products) {
        ArrayList<Products> availableProds = new ArrayList<>();
        int addProd = 0;

        for (Products checkProduct : products) {
            addProd = 0;
            for (Inventory item : checkProduct.getInventory()) {
                if (item.getQuantity() > 0) {
                    addProd = 1;
                }
            }
            if (addProd == 1) {
                availableProds.add(checkProduct);
            }
        }

        return availableProds;
    }

    // LIST OF PRODUCTS WITH RANGE OF PRICES
    private static ArrayList<HashMap<Products, HashMap<Float, Float>>> productPriceRange(ArrayList<Products> products) {
        ArrayList<HashMap<Products, HashMap<Float, Float>>> prodsWithPriceRange = new ArrayList<>();
        HashMap<Products, HashMap<Float, Float>> prosWithPriceRange = new HashMap<>();
        HashMap<Float, Float> prices = new HashMap<>();

        Float maxPrice = Float.valueOf(0);
        Float minPrice = Float.valueOf(0);

        for (Products findPrices : products) {
            for (Inventory item : findPrices.getInventory()) {
                if (maxPrice == 0) {
                    maxPrice = item.getPrice();
                    minPrice = item.getPrice();
                } else if (maxPrice < item.getPrice()) {
                    maxPrice = item.getPrice();
                } else if (minPrice > item.getPrice()) {
                    minPrice = item.getPrice();
                }
            }
            prices.put(minPrice, maxPrice);
            prosWithPriceRange.put(findPrices, prices);
            prodsWithPriceRange.add(prosWithPriceRange);
        }

        return prodsWithPriceRange;
    }


//// MODIFY LIST

    // REVERSE PRODUCTS LIST
    public static ArrayList<Products> revProducts(ProductsDao productsDao) {

        ArrayList<Products> reverseList = new ArrayList<>();

        for (Products product : productsDao.findAll()) {
            reverseList.add(product);
        }

        Collections.reverse(reverseList);

        return reverseList;
    }
}
