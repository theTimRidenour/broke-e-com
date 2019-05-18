package com.brokeshirts.ecom.models;

import java.util.ArrayList;

public class SizesData {

    static ArrayList<Sizes> sizes = new ArrayList<>();

    public static ArrayList<Sizes> getAll() {
        return sizes;
    }

    public static Sizes getById(int id) {
        Sizes oneSize = null;

        for (Sizes candidateSize : sizes) {
            if (candidateSize.getSizeId() == id) {
                oneSize = candidateSize;
            }
        }

        return oneSize;
    }

    public static void add(Sizes newSize) {
        sizes.add(newSize);
    }

    public static void remove(int id) {
        Sizes sizeToRemove = getById(id);
        sizes.remove(sizeToRemove);
    }

}
