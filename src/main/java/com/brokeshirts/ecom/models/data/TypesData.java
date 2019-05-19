package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Types;

import java.util.ArrayList;

public class TypesData {

    static ArrayList<Types> types = new ArrayList<>();

    public static ArrayList<Types> getAll() {
        return types;
    }

    public static Types getById(int id) {
        Types oneType = null;

        for (Types candidateType : types) {
            if (candidateType.getTypeId() == id) {
                oneType = candidateType;
            }
        }

        return oneType;
    }

    public static Types getByName(String typeName) {
        Types oneType = null;

        for (Types candidateType : types) {
            if (candidateType.getName().equals(typeName)) {
                oneType = candidateType;
            }
        }

        return oneType;

    }

    public static ArrayList<Types> getByCategory(int categoryId) {
        ArrayList<Types> categoryTypes = new ArrayList<>();

        for (Types type : types) {
            if (type.getCategoryId() == categoryId) {
                categoryTypes.add(type);
            }
        }

        return categoryTypes;
    }

    public static void add(Types newType) {
        types.add(newType);
    }

    public static void remove(int id) {
        Types typeToRemove = getById(id);
        types.remove(typeToRemove);
    }

}
