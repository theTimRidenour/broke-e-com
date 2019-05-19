package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Customers;

import java.util.ArrayList;

public class CustomersData {

    static ArrayList<Customers> customers = new ArrayList<>();

    public static ArrayList<Customers> getAll() {
        return customers;
    }

    public static Customers getById(int id) {
        Customers oneCustomer = null;

        for (Customers candidateCustomer : customers) {
            if (candidateCustomer.getCustomerId() == id) {
                oneCustomer = candidateCustomer;
            }
        }

        return oneCustomer;
    }

    public static void add(Customers newCustomer) {
        customers.add(newCustomer);
    }

    public static void remove(int id) {
        Customers customerToRemove = getById(id);
        customers.remove(customerToRemove);
    }

}
