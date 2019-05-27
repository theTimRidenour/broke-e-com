package com.brokeshirts.ecom.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    // FOLDER LOCATION FOR STORING IMAGES
    // private String location = "upload-dir";
    private static String location = "src/main/resources/static/images/upload/";

    public static String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
