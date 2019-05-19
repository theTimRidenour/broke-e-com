package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.ReturnAuthorizations;

import java.util.ArrayList;

public class ReturnAuthorizationsData {

    static ArrayList<ReturnAuthorizations> returnAuthorizations = new ArrayList<>();

    public static ArrayList<ReturnAuthorizations> getAll() {
        return returnAuthorizations;
    }

    public static ReturnAuthorizations getById(int id) {
        ReturnAuthorizations oneReturn = null;

        for (ReturnAuthorizations candidateReturn : returnAuthorizations) {
            if (candidateReturn.getReturnAuthorizationId() == id) {
                oneReturn = candidateReturn;
            }
        }

        return oneReturn;
    }

    public static void add(ReturnAuthorizations newReturn) {
        returnAuthorizations.add(newReturn);
    }

    public static void remove(int id) {
        ReturnAuthorizations returnToRemove = getById(id);
        returnAuthorizations.remove(returnToRemove);
    }

}
