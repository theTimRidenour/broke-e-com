package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Photos;

import java.util.ArrayList;

public class PhotosData {

    static ArrayList<Photos> photos = new ArrayList<>();

    public static ArrayList<Photos> getAll() {
        return photos;
    }

    public static Photos getById(int id) {
        Photos onePhoto = null;

        for (Photos candidatePhoto : photos) {
            if (candidatePhoto.getPhotoId() == id) {
                onePhoto = candidatePhoto;
            }
        }

        return onePhoto;
    }

    public static void add(Photos newPhoto) {
        photos.add(newPhoto);
    }

    public static void remove(int id) {
        Photos photoToRemove = getById(id);
        photos.remove(photoToRemove);
    }

}
