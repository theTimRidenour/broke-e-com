package com.brokeshirts.ecom.functions;

import com.brokeshirts.ecom.models.*;
import com.brokeshirts.ecom.models.data.*;

public class Admin {

//// CHANGE ORDER

    // MOVE CATEGORY UP OR DOWN LIST.
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

    // MOVE SUBCATEGORY UP OR DOWN LIST
    public static void moveType(int sortId, String direction, TypesDao typesDao, int categoryId) {
        if (direction.equals("up")) {
            for (Types type : typesDao.findAll()) {
                if (type.getCategoryId() == categoryId) {
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
                if (type.getCategoryId() == categoryId) {
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

        Categories hiddenCat = categoriesDao.findOne(id);
        hiddenCat.setHidden(choice);
        categoriesDao.save(hiddenCat);

    }

    // SUB-CATEGORY
    public static void hideType(int typeId, String choice, TypesDao typesDao) {

        Types hiddenType = typesDao.findOne(typeId);
        hiddenType.setHidden(choice);
        typesDao.save(hiddenType);

    }

    // SIZE
    public static void hideSize(int id, String choice, SizesDao sizesDao) {

        Sizes hiddenSize = sizesDao.findOne(id);
        hiddenSize.setHidden(choice);
        sizesDao.save(hiddenSize);

    }

//// ARCHIVE (NOT VISIBLE TO CUSTOMERS OR ADMIN)

    // CATEGORY
    public static void archiveCat(int categoryId, CategoriesDao categoriesDao, TypesDao typesDao) {

        Categories updateCat = categoriesDao.findOne(categoryId);

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
            if (findTypes.getCategoryId() == categoryId) {
                findTypes.setCatArchive("yes");
                typesDao.save(findTypes);
            }
        }

    }

    // SUB-CATEGORY
    public static void archiveType(int typeId, int categoryId, TypesDao typesDao) {

        Types updateType = typesDao.findOne(typeId);

        for (Types sortType : typesDao.findAll()) {
            if (sortType.getCategoryId() == categoryId) {
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

    // SIZE
    public static void archiveSize(int id, SizesDao sizesDao) {

        Sizes updateSize = sizesDao.findOne(id);

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

        Categories reactivate = categoriesDao.findOne(id);
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
            if (type.getCategoryId() == reactivate.getId()) {
                type.setCatArchive("no");
                typesDao.save(type);
            }
        }

    }

    // SUB-CATEGORY
    public static void reactivateType(int id, TypesDao typesDao) {

        Types reactivate = typesDao.findOne(id);
        int maxSortId = 0;

        for (Types type : typesDao.findAll()) {
            if (type.getCategoryId() == reactivate.getCategoryId()) {
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

    // SIZE
    public static void reactivateSize(int id, SizesDao sizesDao) {

        Sizes reactivate = sizesDao.findOne(id);
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
}
