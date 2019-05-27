package com.brokeshirts.ecom.functions;

import com.brokeshirts.ecom.models.*;
import com.brokeshirts.ecom.models.data.*;
import com.brokeshirts.ecom.storage.StorageProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static com.brokeshirts.ecom.controllers.FileUploadController.internalFileUpload;

public class Data {

//// ADD ITEM

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
    public static void addStyle(String styleName, int categoryId, StylesDao stylesDao) {

        Styles style = new Styles();
        style.setName(styleName);
        style.setHidden("yes");
        style.setArchive("no");
        style.setArchiveCat("no");
        style.setCategoryId(categoryId);
        stylesDao.save(style);

        Styles newStyle = new Styles();

        for (Styles findStyle : stylesDao.findAll()) {
            if (findStyle.getName().equals(styleName)) {
                newStyle = findStyle;
            }
        }

        int sortId = 0;

        for (Styles styleSortMax : stylesDao.findAll()) {
            if (styleSortMax.getCategoryId() == categoryId) {
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
    public static void addType(String typeName, int categoryId, TypesDao typesDao) {

        Types type = new Types();
        type.setName(typeName);
        type.setHidden("yes");
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
    }


//// DELETE ITEM AND ASSOCIATED FILES

    // DELETE CATEGORY
    public static void delCat(int categoryId, CategoriesDao categoriesDao, TypesDao typesDao, StylesDao stylesDao) {

        categoriesDao.delete(categoryId);

        for (Types type : typesDao.findAll()) {
            if (type.getCategoryId() == categoryId) {
                delType(type.getId(), typesDao);
            }
        }

        for (Styles style : stylesDao.findAll()) {
            if (style.getCategoryId() == categoryId) {
                delStyle(style.getId(), stylesDao);
            }
        }
    }

    // DELETE COLOR
    public static void delColor(int colorId, ColorsDao colorsDao) {

        Colors color = colorsDao.findOne(colorId);
        // IF COLOR HAS AN IMAGE ASSOCIATION, DELETE IMAGE
        if (color.getUrl() != null) {
            System.out.println(color.getUrl());
            File file = new File(StorageProperties.getLocation() + color.getUrl());
            file.delete();
        }

        colorsDao.delete(color);
    }

    // DELETE SIZE
    public static void delSize(int sizeId, SizesDao sizesDao) {

        sizesDao.delete(sizeId);
    }

    // DELETE STYLE
    public static void delStyle(int styleId, StylesDao stylesDao) {

        stylesDao.delete(styleId);
    }

    // DELETE SUB-CATEGORY
    public static void delType(int typeId, TypesDao typesDao) {

        typesDao.delete(typeId);
    }
}
