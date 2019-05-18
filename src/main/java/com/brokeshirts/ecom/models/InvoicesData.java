package com.brokeshirts.ecom.models;

import java.util.ArrayList;

public class InvoicesData {

    static ArrayList<Invoices> invoices = new ArrayList<>();

    public static ArrayList<Invoices> getAll() {
        return invoices;
    }

    public static Invoices getById(int id) {
        Invoices oneInvoice = null;

        for (Invoices candidateInvoice : invoices) {
            if (candidateInvoice.getInvoiceId() == id) {
                oneInvoice = candidateInvoice;
            }
        }

        return oneInvoice;
    }

    public static void add(Invoices newInvoice) {
        invoices.add(newInvoice);
    }

    public static void remove(int id) {
        Invoices invoiceToRemove = getById(id);
        invoices.remove(invoiceToRemove);
    }

}
