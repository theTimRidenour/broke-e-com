package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Addresses;

import java.util.ArrayList;

public class AddressesData {

    static ArrayList<Addresses> addresses = new ArrayList<>();

    public static ArrayList<Addresses> getAll() {
        return addresses;
    }

    public static Addresses getById(int id) {
        Addresses oneAddress = null;

        for (Addresses candidateAddress : addresses) {
            if (candidateAddress.getAddressId() == id) {
                oneAddress = candidateAddress;
            }
        }

        return oneAddress;
    }

    public static void add(Addresses newAddress) {
        addresses.add(newAddress);
    }

    public static void remove(int id) {
        Addresses addressToRemove = getById(id);
        addresses.remove(addressToRemove);
    }

}
