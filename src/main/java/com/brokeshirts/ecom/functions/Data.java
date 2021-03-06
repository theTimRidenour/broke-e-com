package com.brokeshirts.ecom.functions;

import com.brokeshirts.ecom.models.*;
import com.brokeshirts.ecom.models.data.*;
import com.brokeshirts.ecom.storage.StorageProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.brokeshirts.ecom.controllers.FileUploadController.internalFileUpload;

public class Data {

    private class inventoryCnt {
        int categoryId;
        int typeId;
        int productId;
        int cnt;
        int totalCnt;
    }

//// ADD ITEM

    // ADD CART LIST
    public static HashMap<Integer, Integer> addCartList(HashMap<Integer, Integer> cartList, String addCart) {
        String findNext = "key";
        String key = "";
        String value = "";
        Integer keyInt = 0;
        Integer valueInt = 0;

        for (char c : addCart.toCharArray()) {
            if (c == '/') {
                findNext = "value";
            } else if (c == '.') {
                findNext = "key";
                keyInt = Integer.parseInt(key);
                key = "";
                valueInt = Integer.parseInt(value);
                value = "";
                if (cartList.containsKey(keyInt)) {
                    if (valueInt > cartList.get(keyInt)) {
                        cartList.replace(keyInt, cartList.get(keyInt), valueInt);
                    }
                } else {
                    cartList.put(keyInt, valueInt);
                }
            } else if (findNext.equals("key")) {
                key += c;
            } else {
                value += c;
            }
        }
        findNext = "key";
        keyInt = Integer.parseInt(key);
        key = "";
        valueInt = Integer.parseInt(value);
        value = "";
        if (cartList.containsKey(keyInt)) {
            if (valueInt > cartList.get(keyInt)) {
                cartList.replace(keyInt, cartList.get(keyInt), valueInt);
            }
        } else {
            cartList.put(keyInt, valueInt);
        }

        return cartList;
    }

    // ADD CATEGORY
    public static void addCat(String categoryName, CategoriesDao categoriesDao) {

        Categories cat = new Categories();
        cat.setName(categoryName);
        cat.setHidden("yes");
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
    }

    // ADD COLOR
    public static void addColor(MultipartFile file, String hex, String name, ColorsDao colorsDao) {

        Colors newColor = new Colors();
        int maxSortId = 0;

        if (!file.isEmpty()) {
            internalFileUpload(file);
            newColor.setUrl(file.getOriginalFilename());
        }

        newColor.setName(name);
        newColor.setHex(hex);
        newColor.setArchive("no");
        newColor.setHidden("yes");
        colorsDao.save(newColor);
    }

    // ADD IMAGE
    private static int addImage(MultipartFile file, int productId, PhotosDao photosDao) {

        String inPhotos = "no";
        String checkFileName = file.getOriginalFilename();
        int imageId = 0;

        for (Photos findPhoto : photosDao.findAll()) {

            if (findPhoto.getUrl().equals(checkFileName)) {
                inPhotos.equals("yes");
                imageId = findPhoto.getId();
            }
        }

        if (inPhotos.equals("no")) {

            Photos newPhoto = new Photos();

            internalFileUpload(file);

            newPhoto.setProductId(productId);
            newPhoto.setUrl(file.getOriginalFilename());
            photosDao.save(newPhoto);

            for (Photos findPhoto : photosDao.findAll()) {
                if (findPhoto.getProductId() == productId && findPhoto.getUrl().equals(checkFileName)) {
                    imageId = findPhoto.getId();
                }
            }
        }

        return imageId;
    }

    // ADD INVENTORY ITEM
    public static void addItem(float price, int productId, int colorId, int[] sizeIds, MultipartFile file, String sku, ProductsDao productsDao, PhotosDao photosDao, InventoryDao inventoryDao) {

        if (sizeIds != null && !file.isEmpty() && colorId != 0 && productId != 0 && !sku.isEmpty()) {

            int imageId = addImage(file, productId, photosDao);
            Inventory newItem = new Inventory();
            Products oneProduct = productsDao.findById(productId).orElse(new Products());

            for (int i : sizeIds) {
                newItem = new Inventory();

                newItem.setProducts(oneProduct);
                newItem.setColorId(colorId);
                newItem.setSizeId(i);
                newItem.setImageId(imageId);
                newItem.setSku(String.format("%s%s", sku, i));
                newItem.setPrice(price);
                newItem.setQuantity(0);
                newItem.setHidden("yes");
                newItem.setArchive("no");
                newItem.setArchiveSize("no");
                newItem.setArchiveColor("no");

                inventoryDao.save(newItem);
            }

        }
    }

    // ADD PRODUCT
    public static void addProduct (int categoryId, int typeId, int[] styleId, StylesDao stylesDao, String name, MultipartFile file, PhotosDao photosDao, ProductsDao productsDao, TypesDao typesDao) {

        Products newProduct = new Products();
        Types oneType = typesDao.findById(typeId).orElse(new Types());

        newProduct.setName(name);
        newProduct.setCategoryId(categoryId);
        newProduct.setArchive("no");
        newProduct.setArchiveCat("no");
        newProduct.setArchiveStyle("no");
        newProduct.setArchiveType("no");
        newProduct.setHidden("yes");
        newProduct.setType(oneType);

        if (styleId != null) {

            for (int i : styleId) {
                if (stylesDao.findById(i).orElse(new Styles()).getCategory().getId() == categoryId) {
                    newProduct.addStyleId(i);
                }
            }
        }

        productsDao.save(newProduct);

        Types type = typesDao.findById(typeId).orElse(new Types());

        int productId = 0;

        for (Products findProduct : productsDao.findAll()) {
            if (findProduct.getCategoryId() == categoryId && findProduct.getType() == type && findProduct.getName().equals(name)) {
                productId = findProduct.getId();
            }
        }

        if (!file.isEmpty()) {
            int imageId = addImage(file, productId, photosDao);

            Products updateProduct = productsDao.findById(productId).orElse(new Products());
            updateProduct.setImageId(imageId);

            productsDao.save(updateProduct);
        }
    }

    // ADD SIZE
    public static void addSize(String longName, String shortName, SizesDao sizesDao) {

        Sizes newSize = new Sizes();
        int maxSortId = 0;

        newSize.setLongName(longName);
        newSize.setShortName(shortName);
        newSize.setArchive("no");
        newSize.setHidden("yes");
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
    }

    // ADD STYLE
    public static void addStyle(String styleName, int categoryId, StylesDao stylesDao, CategoriesDao categoriesDao) {

        Categories cat = categoriesDao.findById(categoryId).orElse(new Categories());

        Styles style = new Styles();
        style.setName(styleName);
        style.setHidden("yes");
        style.setArchive("no");
        style.setArchiveCat("no");
        style.setCategory(cat);
        stylesDao.save(style);

        Styles newStyle = new Styles();

        for (Styles findStyle : stylesDao.findAll()) {
            if (findStyle.getName().equals(styleName)) {
                newStyle = findStyle;
            }
        }

        int sortId = 0;

        for (Styles styleSortMax : stylesDao.findAll()) {
            if (styleSortMax.getCategory() == cat) {
                if (sortId < styleSortMax.getSortId()) {
                    sortId = styleSortMax.getSortId();
                }
            }
        }

        sortId++;

        newStyle.setSortId(sortId);
        stylesDao.save(newStyle);

    }

    // ADD SUB-CATEGORY
    public static void addType(String typeName, int categoryId, TypesDao typesDao, CategoriesDao categoriesDao) {

        Types type = new Types();
        type.setName(typeName);
        type.setHidden("yes");
        type.setArchive("no");
        type.setCatArchive("no");
        Categories cat = categoriesDao.findById(categoryId).orElse(new Categories());
        type.setCategory(cat);
        typesDao.save(type);

        Types newType = new Types();

        for (Types findType : typesDao.findAll()) {
            if (findType.getName().equals(typeName)) {
                newType = findType;
            }
        }

        int sortId = 0;

        for (Types typeSortMax : typesDao.findAll()) {
            if (typeSortMax.getCategory() == cat) {
                if (sortId < typeSortMax.getSortId()) {
                    sortId = typeSortMax.getSortId();
                }
            }
        }

        sortId++;

        newType.setSortId(sortId);
        typesDao.save(newType);
    }

//// UPDATE DATA

    // UPDATE QUANTITY OF INVENTORY ITEM
    public static void updateItemQuantity(int itemId, int quantity, InventoryDao inventoryDao) {

        Inventory item = inventoryDao.findById(itemId).orElse(new Inventory());

        if (quantity < 0) {
            quantity = 0;
        }

        item.setQuantity(quantity);
        inventoryDao.save(item);
    }

    // CONVERT CART HASH MAP TO CART STRING
    public static String cartHashToString(HashMap<Integer, Integer>cart) {
        String newCartItems = "";
        int first = 1;

        for (Map.Entry<Integer, Integer> singleItem : cart.entrySet()) {
            if (first == 1) {
                newCartItems = singleItem.getKey() + "/" + singleItem.getValue();
                first++;
            } else {
                newCartItems += "." + singleItem.getKey() + "/" + singleItem.getValue();
            }
        }

        return newCartItems;
    }

//// DELETE ITEM AND ASSOCIATED FILES

    // DELETE CATEGORY
    public static void delCat(int categoryId, CategoriesDao categoriesDao, TypesDao typesDao, StylesDao stylesDao) {

        Categories cat = categoriesDao.findById(categoryId).orElse(new Categories());

        categoriesDao.delete(cat);

        for (Types type : typesDao.findAll()) {
            if (type.getCategory() == cat) {
                delType(type.getId(), typesDao);
            }
        }

        for (Styles style : stylesDao.findAll()) {
            if (style.getCategory() == cat) {
                delStyle(style.getId(), stylesDao);
            }
        }
    }

    // DELETE COLOR
    public static void delColor(int colorId, ColorsDao colorsDao) {

        Colors color = colorsDao.findById(colorId).orElse(new Colors());
        // IF COLOR HAS AN IMAGE ASSOCIATION, DELETE IMAGE
        if (color.getUrl() != null) {
            File file = new File(StorageProperties.getLocation() + color.getUrl());
            file.delete();
        }

        colorsDao.delete(color);
    }

    // DELETE PRODUCT
    public static void delProduct(int productId, ProductsDao productsDao, PhotosDao photosDao) {

        Products product = productsDao.findById(productId).orElse(new Products());
        // IF PRODUCT HAS AN IMAGE ASSOCIATED, DELETE IMAGE
        if (product.getImageId() != 0) {
            Photos photo = photosDao.findById(product.getImageId()).orElse(new Photos());
            File file = new File(StorageProperties.getLocation() + photo.getUrl());
            file.delete();
            photosDao.delete(photo);
        }

        productsDao.delete(product);
    }

    // DELETE SIZE
    public static void delSize(int sizeId, SizesDao sizesDao) {

        sizesDao.deleteById(sizeId);
    }

    // DELETE STYLE
    public static void delStyle(int styleId, StylesDao stylesDao) {

        stylesDao.deleteById(styleId);
    }

    // DELETE SUB-CATEGORY
    public static void delType(int typeId, TypesDao typesDao) {

        typesDao.deleteById(typeId);
    }

//// CHECKOUT

    // CHECK IF ITEMS STILL AVAILABLE
    public static boolean checkCart(String cartItems, InventoryDao inventoryDao) {
        int tracker = 0;
        String itemId = "";
        String quantity = "";
        Inventory checkItem = new Inventory();

        for (char c : cartItems.toCharArray()) {
            if (c == '/') {
                tracker = 1;
            } else if (c == '.') {
                tracker = 0;
                checkItem = inventoryDao.findById(Integer.valueOf(itemId)).orElse(new Inventory());
                if (Integer.valueOf(quantity) > checkItem.getQuantity()) {
                    return false;
                }
                itemId = "";
                quantity = "";
                checkItem = new Inventory();
            } else if (tracker == 0) {
                itemId += c;
            } else {
                quantity += c;
            }
        }
        checkItem = inventoryDao.findById(Integer.valueOf(itemId)).orElse(new Inventory());
        if (Integer.valueOf(quantity) > checkItem.getQuantity()) {
            return false;
        }

        return true;
    }

    // CREATE INVENTORY STRING FOR ORDER
    public static String createInventoryString(String cartItems, InventoryDao inventoryDao) {
        String itemString = "";
        Inventory item = new Inventory();
        int tracker = 0;
        String itemId = "";
        String quantity = "";

        for (char c : cartItems.toCharArray()) {
            if (c == '/') {
                tracker = 1;
            } else if (c == '.') {
                tracker = 0;
                item = inventoryDao.findById(Integer.valueOf(itemId)).orElse(new Inventory());
                if (itemString.equals("")) {
                    itemString += item.getPrice() + "/" + itemId + "/" + quantity;
                } else {
                    itemString += "." + item.getPrice() + "/" + itemId + "/" + quantity;
                }
                itemId = "";
                quantity = "";
                item = new Inventory();
            } else if (tracker == 0) {
                itemId += c;
            } else {
                quantity += c;
            }
        }
        item = inventoryDao.findById(Integer.valueOf(itemId)).orElse(new Inventory());
        if (itemString.equals("")) {
            itemString += item.getPrice() + "/" + itemId + "/" + quantity;
        } else {
            itemString += "." + item.getPrice() + "/" + itemId + "/" + quantity;
        }

        return itemString;
    }

    // UPDATE CART IF NOT AVAILABLE

    // REMOVE CART ITEMS FROM INVENTORY
    public static void removeCartFromInventory(String cartItems, InventoryDao inventoryDao) {
        int tracker = 0;
        String itemId = "";
        String quantity = "";
        Inventory updateItem = new Inventory();

        for (char c : cartItems.toCharArray()) {
            if (c == '/') {
                tracker = 1;
            } else if (c == '.') {
                tracker = 0;
                updateItem = inventoryDao.findById(Integer.valueOf(itemId)).orElse(new Inventory());
                updateItem.setQuantity(updateItem.getQuantity() - Integer.valueOf(quantity));
                inventoryDao.save(updateItem);
                itemId = "";
                quantity = "";
                updateItem = new Inventory();
            } else if (tracker == 0) {
                itemId += c;
            } else {
                quantity += c;
            }
        }
        updateItem = inventoryDao.findById(Integer.valueOf(itemId)).orElse(new Inventory());
        updateItem.setQuantity(updateItem.getQuantity() - Integer.valueOf(quantity));
        inventoryDao.save(updateItem);
    }

//// EXTRA DATA FUNCTIONS

    // REPLACE "_" WITH " "
    public static String replaceUnderscore(String underscoreString) {
        String replaced = underscoreString.replace('_',' ');
        return replaced;
    }

    // FIND INVENTORY ITEM BY PRODUCT_ID, SIZE_ID, AND COLOR_ID
    public static Inventory findItem(int productId, int sizeId, int colorId, ProductsDao productsDao, InventoryDao inventoryDao) {
        Inventory theItem = new Inventory();
        Products theProduct = productsDao.findById(productId).orElse(new Products());

        for (Inventory item : theProduct.getInventory()) {
            if (item.getColorId() == colorId) {
                if (item.getSizeId() == sizeId) {
                    return item;
                }
            }
        }
        return theItem;
    }

    // CREATE USERNAME FOR HEADER
    public static String userHeaderName(String user, UserDao userDao) {
        String username = "guest";

        if (!user.equals("guest")) {
            User theUser = userDao.findByToken(user);
            username = theUser.getEmail();
            if (username.length() > 12) {
                int charCnt = 0;
                String usernameTwo = username;
                username = "";
                for (char c : usernameTwo.toCharArray()) {
                    if (charCnt < 10) {
                        username += c;
                        charCnt++;
                    }
                }
                username += "...";
            }
        }

        return username;
    }
}
