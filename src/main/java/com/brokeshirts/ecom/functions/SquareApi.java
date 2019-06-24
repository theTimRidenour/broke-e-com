package com.brokeshirts.ecom.functions;

import com.squareup.connect.ApiClient;
import com.squareup.connect.ApiException;
import com.squareup.connect.Configuration;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.auth.OAuth;
import com.squareup.connect.models.Location;

import java.util.List;

public class SquareApi {

    public static void main(String[] args) {
        ApiClient apiClient = Configuration.getDefaultApiClient();

        // CONFIGURE  OAUTH2 ACCESS TOKEN FOR AUTHORIZATION: OAUTH2
        OAuth oAuth2 = (OAuth) apiClient.getAuthentication("oauth2");
        oAuth2.setAccessToken("EAAAEBJPSkH6FTIU1yYiHa2IpAHgbk8OtOmbmU3lMynp_VNviayaI2K_5nxT_xeT");

        // LIST ALL LOCATIONS
        LocationsApi locationsApi = new LocationsApi();
        locationsApi.setApiClient(apiClient);

        try {
            List<Location> locations = locationsApi.listLocations().getLocations();
            System.out.println(locations);
        } catch (ApiException e) {
            System.err.println("Exception when calling API");
            e.printStackTrace();
        }
    }
}
