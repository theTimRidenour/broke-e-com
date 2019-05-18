package com.brokeshirts.ecom.models;

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

    public static void add(Types newType) {
        types.add(newType);
    }

    public static void remove(int id) {
        Types typeToRemove = getById(id);
        types.remove(typeToRemove);
    }

}
