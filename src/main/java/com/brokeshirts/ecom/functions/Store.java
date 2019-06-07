package com.brokeshirts.ecom.functions;

import com.brokeshirts.ecom.models.*;
import com.brokeshirts.ecom.models.data.CategoriesDao;
import com.brokeshirts.ecom.models.data.PhotosDao;
import com.brokeshirts.ecom.models.data.ProductsDao;
import com.brokeshirts.ecom.models.data.TypesDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Store {

    public static class listedProducts {
        private String name;

        private String imageUrl;

        private String maxPrice;

        private String minPrice;

        private int categoryId;

        public listedProducts() {}

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public String getImageUrl() { return imageUrl; }

        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

        public String getMaxPrice() { return maxPrice; }

        public void setMaxPrice(String maxPrice) { this.maxPrice = maxPrice; }

        public String getMinPrice() { return minPrice; }

        public void setMinPrice(String minPrice) { this.minPrice = minPrice; }

        public int getCategoryId() { return categoryId; }

        public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    }

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
    public static ArrayList<listedProducts> featuredProducts(ProductsDao productsDao, PhotosDao photosDao) {
        ArrayList<listedProducts> returnProducts = new ArrayList<>();
        listedProducts newProd = new listedProducts();
        String name = "";
        int imageId = 0;
        Photos photo = new Photos();
        Float maxPrice = (float) 0;
        Float minPrice = (float) 0;
        int isAvailable = 0;

        for (Products available : revProducts(productsDao)) {
            for (Inventory itemCheck : available.getInventory()) {
                if (itemCheck.getQuantity() > 0) {
                    isAvailable = 1;
                }
            }
            if (isAvailable == 1) {
                name = available.getName();

                for (Inventory items : available.getInventory()) {
                    if (maxPrice == 0) {
                        maxPrice = (float) items.getPrice();
                        minPrice = (float) items.getPrice();
                        imageId = items.getImageId();
                    } else if (maxPrice < items.getPrice()) {
                        maxPrice = (float) items.getPrice();
                    } else if (minPrice > items.getPrice()) {
                        minPrice = (float) items.getPrice();
                    }
                }
                photo = photosDao.findById(imageId).orElse(new Photos());

                newProd.setName(name);
                newProd.setImageUrl(photo.getUrl());
                newProd.setMaxPrice(String.format("%.2f", maxPrice));
                newProd.setMinPrice(String.format("%.2f", minPrice));
                returnProducts.add(newProd);
                newProd = new listedProducts();
                name = "";
                imageId = 0;
                maxPrice = (float) 0;
                minPrice = (float) 0;
            }
            isAvailable = 0;
        }

        ArrayList<listedProducts> cntReturnProducts = new ArrayList<>();

        int cnt = 1;
        int maxCnt = 4;

        if (returnProducts.size() < maxCnt) {
            maxCnt = returnProducts.size();
        }

        for (listedProducts listed : returnProducts) {
            if (cnt <= maxCnt) {
                cntReturnProducts.add(listed);
                cnt++;
            }
        }

        return cntReturnProducts;
    }

    // LIST OF LAST FOUR PRODUCTS FROM EACH SUB-CATEGORY
    public static ArrayList<listedProducts> revProdsByType(TypesDao typesDao, ProductsDao productsDao, PhotosDao photosDao) {
        ArrayList<listedProducts> returnList = new ArrayList<>();
        ArrayList<Products> sortListOne = revProducts(productsDao);
        ArrayList<Products> sortListTwo = availableProducts(sortListOne);
        int cnt = 1;
        int maxCnt = 4;

        for (Types type : typesDao.findAll()) {
            for (Products product : sortListTwo) {
                if (product.getType() == type) {
                    if (cnt <= maxCnt) {
                        sortListOne.add(product);
                        cnt++;
                    }
                }
            }
            cnt = 1;
        }

        listedProducts newProd = new listedProducts();
        String name = "";
        int categoryId = 0;
        int imageId = 0;
        Photos photo = new Photos();
        Float maxPrice = (float) 0;
        Float minPrice = (float) 0;

        for (Products product : sortListOne) {
            name = product.getName();
            categoryId = product.getCategoryId();

            for (Inventory items : product.getInventory()) {
                if (maxPrice == 0) {
                    maxPrice = (float) items.getPrice();
                    minPrice = (float) items.getPrice();
                    imageId = items.getImageId();
                } else if (maxPrice < items.getPrice()) {
                    maxPrice = (float) items.getPrice();
                } else if (minPrice > items.getPrice()) {
                    minPrice = (float) items.getPrice();
                }
            }
            photo = photosDao.findById(imageId).orElse(new Photos());

            newProd.setName(name);
            newProd.setCategoryId(categoryId);
            newProd.setImageUrl(photo.getUrl());
            newProd.setMaxPrice(String.format("%.2f", maxPrice));
            newProd.setMinPrice(String.format("%.2f", minPrice));
            returnList.add(newProd);
            newProd = new listedProducts();
            name = "";
            imageId = 0;
            maxPrice = (float) 0;
            minPrice = (float) 0;
        }

        return returnList;
    }

    // LIST OF LAST FOUR PRODUCTS FROM EACH CATEGORY
    public static ArrayList<listedProducts> revProdsByCat(CategoriesDao categoriesDao, ProductsDao productsDao, PhotosDao photosDao) {
        ArrayList<listedProducts> returnList = new ArrayList<>();
        ArrayList<Products> sortListOne = revProducts(productsDao);

        for (Products prod : sortListOne) {
        }

        ArrayList<Products> sortListTwo = availableProducts(sortListOne);
        sortListOne.clear();

        for (Products prod : sortListTwo) {
        }

        int cnt = 1;
        int maxCnt = 4;

        for (Categories cat : categoriesDao.findAll()) {
            for (Products product : sortListTwo) {
                if (product.getCategoryId() == cat.getId()) {
                    if (cnt <= maxCnt) {
                        sortListOne.add(product);
                        cnt++;
                    }
                }
            }
            cnt = 1;
        }

        listedProducts newProd = new listedProducts();
        String name = "";
        int categoryId = 0;
        int imageId = 0;
        Photos photo = new Photos();
        Float maxPrice = (float) 0;
        Float minPrice = (float) 0;

        for (Products product : sortListOne) {

            name = product.getName();
            categoryId = product.getCategoryId();

            for (Inventory items : product.getInventory()) {
                if (maxPrice == 0) {
                    maxPrice = (float) items.getPrice();
                    minPrice = (float) items.getPrice();
                    imageId = items.getImageId();
                } else if (maxPrice < items.getPrice()) {
                    maxPrice = (float) items.getPrice();
                } else if (minPrice > items.getPrice()) {
                    minPrice = (float) items.getPrice();
                }
            }
            photo = photosDao.findById(imageId).orElse(new Photos());

            newProd.setName(name);
            newProd.setCategoryId(categoryId);
            newProd.setImageUrl(photo.getUrl());
            newProd.setMaxPrice(String.format("%.2f", maxPrice));
            newProd.setMinPrice(String.format("%.2f", minPrice));
            returnList.add(newProd);
            newProd = new listedProducts();
            name = "";
            imageId = 0;
            maxPrice = (float) 0;
            minPrice = (float) 0;
        }

        return returnList;
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

    // ADD IMAGE_IDs
    private static ArrayList<HashMap<Products, HashMap<Integer, HashMap<Float, Float>>>> addImages(ArrayList<HashMap<Products, HashMap<Float, Float>>> products) {
        ArrayList<HashMap<Products, HashMap<Integer, HashMap<Float, Float>>>> returnList = new ArrayList<>();
        HashMap<Integer, HashMap<Float, Float>> addedImage = new HashMap<>();
        HashMap<Products, HashMap<Integer, HashMap<Float, Float>>> singleListItem = new HashMap<>();
        Integer imageId = 0;

        for (HashMap<Products, HashMap<Float, Float>> prods : products) {
            for (Map.Entry<Products, HashMap<Float, Float>> singleProd : prods.entrySet()) {
                if (singleProd.getKey().getImageId() != 0) {
                    imageId = singleProd.getKey().getImageId();
                } else {
                    for (Inventory item : singleProd.getKey().getInventory()) {
                        imageId = item.getImageId();
                    }
                }
                addedImage.put(imageId, singleProd.getValue());
                singleListItem.put(singleProd.getKey(), addedImage);
                returnList.add(singleListItem);
            }
        }

        return returnList;
    }
}
