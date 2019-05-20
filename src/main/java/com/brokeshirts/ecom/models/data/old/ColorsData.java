package com.brokeshirts.ecom.models.data.old;

import com.brokeshirts.ecom.models.old.Colors;

import java.util.ArrayList;

public class ColorsData {

    static ArrayList<Colors> colors = new ArrayList<>();

    public static ArrayList<Colors> getAll() {
        return colors;
    }

    public static Colors getById(int id) {
        Colors oneColor = null;

        for (Colors candidateColor : colors) {
            if (candidateColor.getColorId() == id) {
                oneColor = candidateColor;
            }
        }

        return oneColor;
    }

    public static void add(Colors newColor) {
        colors.add(newColor);
    }

    public static void remove(int id) {
        Colors colorToRemove = getById(id);
        colors.remove(colorToRemove);
    }


}
