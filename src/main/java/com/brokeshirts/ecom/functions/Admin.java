package com.brokeshirts.ecom.functions;

import com.brokeshirts.ecom.models.*;
import com.brokeshirts.ecom.models.data.*;

import java.util.ArrayList;

public class Admin {

//// CHANGE ORDER

    // MOVE CATEGORY UP OR DOWN LIST
    public static void moveCat(int sortId, String direction, CategoriesDao categoriesDao) {

        if (direction.equals("up")) {
            for (Categories cat : categoriesDao.findAll()) {
                if (cat.getSortId() == sortId) {
                    cat.setSortId(sortId - 1);
                    categoriesDao.save(cat);
                } else if (cat.getSortId() == sortId - 1) {
                    cat.setSortId(sortId);
                    categoriesDao.save(cat);
                }
            }
        } else if (direction.equals("down")) {
            for (Categories cat : categoriesDao.findAll()) {
                if (cat.getSortId() == sortId) {
                    cat.setSortId(sortId + 1);
                    categoriesDao.save(cat);
                } else if (cat.getSortId() == sortId + 1) {
                    cat.setSortId(sortId);
                    categoriesDao.save(cat);
                }
            }
        }
    }

    // MOVE SUB-CATEGORY UP OR DOWN LIST
    public static void moveType(int sortId, String direction, TypesDao typesDao, int categoryId) {
        if (direction.equals("up")) {
            for (Types type : typesDao.findAll()) {
                if (type.getCategory().getId() == categoryId) {
                    if (type.getSortId() == sortId) {
                        type.setSortId(sortId - 1);
                        typesDao.save(type);
                    } else if (type.getSortId() == sortId - 1) {
                        type.setSortId(sortId);
                        typesDao.save(type);
                    }
                }
            }
        } else if (direction.equals("down")) {
            for (Types type : typesDao.findAll()) {
                if (type.getCategory().getId() == categoryId) {
                    if (type.getSortId() == sortId) {
                        type.setSortId(sortId + 1);
                        typesDao.save(type);
                    } else if (type.getSortId() == sortId + 1) {
                        type.setSortId(sortId);
                        typesDao.save(type);
                    }
                }
            }
        }
    }

    // MOVE STYLE UP OR DOWN LIST
    public static void moveStyle(int sortId, String direction, StylesDao stylesDao, int categoryId) {
        if (direction.equals("up")) {
            for (Styles style : stylesDao.findAll()) {
                if (style.getCategory().getId() == categoryId) {
                    if (style.getSortId() == sortId) {
                        style.setSortId(sortId - 1);
                        stylesDao.save(style);
                    } else if (style.getSortId() == sortId - 1) {
                        style.setSortId(sortId);
                        stylesDao.save(style);
                    }
                }
            }
        } else if (direction.equals("down")) {
            for (Styles style : stylesDao.findAll()) {
                if (style.getCategory().getId() == categoryId) {
                    if (style.getSortId() == sortId) {
                        style.setSortId(sortId + 1);
                        stylesDao.save(style);
                    } else if (style.getSortId() == sortId + 1) {
                        style.setSortId(sortId);
                        stylesDao.save(style);
                    }
                }
            }
        }
    }

    // MOVE SIZE UP OR DOWN LIST
    public static void moveSize(int sortId, String direction, SizesDao sizesDao) {

        if (direction.equals("up")) {
            for (Sizes size : sizesDao.findAll()) {
                if (size.getSortId() == sortId) {
                    size.setSortId(sortId - 1);
                    sizesDao.save(size);
                } else if (size.getSortId() == sortId - 1) {
                    size.setSortId(sortId);
                    sizesDao.save(size);
                }
            }
        } else if (direction.equals("down")) {
            for (Sizes size : sizesDao.findAll()) {
                if (size.getSortId() == sortId) {
                    size.setSortId(sortId + 1);
                    sizesDao.save(size);
                } else if (size.getSortId() == sortId + 1) {
                    size.setSortId(sortId);
                    sizesDao.save(size);
                }
            }
        }
    }

//// HIDE OR MAKE VISIBLE TO CUSTOMERS

    // CATEGORY
    public static void hideCat(int id, String choice, CategoriesDao categoriesDao) {

        Categories hiddenCat = categoriesDao.findById(id).orElse(new Categories());
        hiddenCat.setHidden(choice);
        categoriesDao.save(hiddenCat);
    }

    // COLOR
    public static void hideColor(int id, String choice, ColorsDao colorsDao) {

        Colors hiddenColor = colorsDao.findById(id).orElse(new Colors());
        hiddenColor.setHidden(choice);
        colorsDao.save(hiddenColor);
    }

    // PRODUCT
    public static void hideProduct(int productId, ProductsDao productsDao, String choice) {

        Products hiddenProduct = productsDao.findById(productId).orElse(new Products());
        hiddenProduct.setHidden(choice);
        productsDao.save(hiddenProduct);
    }

    // SUB-CATEGORY
    public static void hideType(int typeId, String choice, TypesDao typesDao) {

        Types hiddenType = typesDao.findById(typeId).orElse(new Types());
        hiddenType.setHidden(choice);
        typesDao.save(hiddenType);
    }

    // STYLE
    public static void hideStyle(int styleId, String choice, StylesDao stylesDao) {

        Styles hiddenStyle = stylesDao.findById(styleId).orElse(new Styles());
        hiddenStyle.setHidden(choice);
        stylesDao.save(hiddenStyle);
    }

    // SIZE
    public static void hideSize(int id, String choice, SizesDao sizesDao) {

        Sizes hiddenSize = sizesDao.findById(id).orElse(new Sizes());
        hiddenSize.setHidden(choice);
        sizesDao.save(hiddenSize);

    }

//// ARCHIVE (NOT VISIBLE TO CUSTOMERS OR ADMIN)

    // CATEGORY
    public static void archiveCat(int categoryId, CategoriesDao categoriesDao, TypesDao typesDao) {

        Categories updateCat = categoriesDao.findById(categoryId).orElse(new Categories());

        for (Categories sortCat : categoriesDao.findAll()) {
            if (sortCat.getSortId() > updateCat.getSortId()) {
                sortCat.setSortId(sortCat.getSortId() - 1);
                categoriesDao.save(sortCat);
            }
        }

        updateCat.setSortId(0);
        updateCat.setArchive("yes");
        categoriesDao.save(updateCat);

        for (Types findTypes : typesDao.findAll()) {
            if (findTypes.getCategory().getId() == categoryId) {
                findTypes.setCatArchive("yes");
                typesDao.save(findTypes);
            }
        }

    }

    // COLOR
    public static void archiveColor(int colorId, ColorsDao colorsDao) {

        Colors updateColor = colorsDao.findById(colorId).orElse(new Colors());

        updateColor.setArchive("yes");
        colorsDao.save(updateColor);
    }

    // PRODUCT
    public static void archiveProduct(int productId, ProductsDao productsDao) {

        Products updateProduct = productsDao.findById(productId).orElse(new Products());

        updateProduct.setArchive("yes");
        productsDao.save(updateProduct);
    }

    // SUB-CATEGORY
    public static void archiveType(int typeId, int categoryId, TypesDao typesDao) {

        Types updateType = typesDao.findById(typeId).orElse(new Types());

        for (Types sortType : typesDao.findAll()) {
            if (sortType.getCategory().getId() == categoryId) {
                if (sortType.getSortId() > updateType.getSortId()) {
                    sortType.setSortId(sortType.getSortId() - 1);
                    typesDao.save(sortType);
                }
            }
        }

        updateType.setSortId(0);
        updateType.setArchive("yes");
        typesDao.save(updateType);
    }

    // STYLE
    public static void archiveStyle(int styleId, int categoryId, StylesDao stylesDao) {

        Styles updateStyle = stylesDao.findById(styleId).orElse(new Styles());

        for (Styles sortStyle : stylesDao.findAll()) {
            if (sortStyle.getCategory().getId() == categoryId) {
                if (sortStyle.getSortId() > updateStyle.getSortId()) {
                    sortStyle.setSortId(sortStyle.getSortId() - 1);
                    stylesDao.save(sortStyle);
                }
            }
        }

        updateStyle.setSortId(0);
        updateStyle.setArchive("yes");
        stylesDao.save(updateStyle);
    }

    // SIZE
    public static void archiveSize(int id, SizesDao sizesDao) {

        Sizes updateSize = sizesDao.findById(id).orElse(new Sizes());

        for (Sizes sortSize : sizesDao.findAll()) {
            if (sortSize.getSortId() > updateSize.getSortId()) {
                sortSize.setSortId(sortSize.getSortId() - 1);
                sizesDao.save(sortSize);
            }
        }

        updateSize.setSortId(0);
        updateSize.setArchive("yes");
        sizesDao.save(updateSize);

    }

//// REACTIVATE (RESTORE ARCHIVED ITEM)

    // CATEGORY
    public static void reactivateCat(int id, CategoriesDao categoriesDao, TypesDao typesDao) {

        Categories reactivate = categoriesDao.findById(id).orElse(new Categories());
        int maxSortId = 0;

        for (Categories cat : categoriesDao.findAll()) {
            if (maxSortId < cat.getSortId()) {
                maxSortId = cat.getSortId();
            }
        }

        reactivate.setArchive("no");
        reactivate.setHidden("yes");
        reactivate.setSortId(maxSortId + 1);
        categoriesDao.save(reactivate);

        for (Types type : typesDao.findAll()) {
            if (type.getCategory() == reactivate) {
                type.setCatArchive("no");
                typesDao.save(type);
            }
        }

    }

    // COLOR
    public static void reactivateColor(int id, ColorsDao colorsDao) {

        Colors updateColor = colorsDao.findById(id).orElse(new Colors());

        updateColor.setArchive("no");
        updateColor.setHidden("yes");
        colorsDao.save(updateColor);
    }

    // PRODUCT
    public static void reactivateProduct(int id, ProductsDao productsDao) {

        Products updateProduct = productsDao.findById(id).orElse(new Products());

        updateProduct.setArchive("no");
        updateProduct.setHidden("yes");
        productsDao.save(updateProduct);
    }

    // SUB-CATEGORY
    public static void reactivateType(int id, TypesDao typesDao) {

        Types reactivate = typesDao.findById(id).orElse(new Types());
        int maxSortId = 0;

        for (Types type : typesDao.findAll()) {
            if (type.getCategory() == reactivate.getCategory()) {
                if (maxSortId < type.getSortId()) {
                    maxSortId = type.getSortId();
                }
            }
        }

        reactivate.setArchive("no");
        reactivate.setHidden("yes");
        reactivate.setSortId(maxSortId + 1);
        typesDao.save(reactivate);
    }

    // STYLE
    public static void reactivateStyle(int id, StylesDao stylesDao) {

        Styles reactivate = stylesDao.findById(id).orElse(new Styles());
        int maxSortId = 0;

        for (Styles style : stylesDao.findAll()) {
            if (style.getCategory() == reactivate.getCategory()) {
                if (maxSortId < style.getSortId()) {
                    maxSortId = style.getSortId();
                }
            }
        }

        reactivate.setArchive("no");
        reactivate.setHidden("yes");
        reactivate.setSortId(maxSortId + 1);
        stylesDao.save(reactivate);
    }

    // SIZE
    public static void reactivateSize(int id, SizesDao sizesDao) {

        Sizes reactivate = sizesDao.findById(id).orElse(new Sizes());
        int maxSortId = 0;

        for (Sizes size : sizesDao.findAll()) {
            if (maxSortId < size.getSortId()) {
                maxSortId = size.getSortId();
            }
        }

        reactivate.setArchive("no");
        reactivate.setHidden("yes");
        reactivate.setSortId(maxSortId + 1);
        sizesDao.save(reactivate);

    }

//// EXTRA FUNCTIONS

    // CONVERT IMAGE FILES FOR COLORS
    public static ArrayList<Colors> convertColorImages(ColorsDao colorsDao) {

        ArrayList<Colors> convertedImages = new ArrayList<>();

        for (Colors color : colorsDao.findAll()) {
            color.setUrl("'" + color.getUrl() + "'");
            convertedImages.add(color);
        }
        return convertedImages;
    }

    // CHECK IF ADMIN
    public static boolean adminCheck(String user, UserDao userDao) {

        if (!user.equals("guest")) {
            User theUser = userDao.findByToken(user);
            if (theUser.getRole().equals("ADMIN")) {
                return true;
            }
        }

        return false;
    }
}
