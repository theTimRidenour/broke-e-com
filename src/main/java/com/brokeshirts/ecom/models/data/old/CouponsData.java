package com.brokeshirts.ecom.models.data.old;

import com.brokeshirts.ecom.models.old.Coupons;

import java.util.ArrayList;

public class CouponsData {

    static ArrayList<Coupons> coupons = new ArrayList<>();

    public static ArrayList<Coupons> getAll() {
        return coupons;
    }

    public static Coupons getById(int id) {
        Coupons oneCoupon = null;

        for (Coupons candidateCoupon : coupons) {
            if (candidateCoupon.getCouponId() == id) {
                oneCoupon = candidateCoupon;
            }
        }

        return oneCoupon;
    }

    public static void add(Coupons newCoupon) {
        coupons.add(newCoupon);
    }

    public static void remove(int id) {
        Coupons couponToRemove = getById(id);
        coupons.remove(couponToRemove);
    }

}
