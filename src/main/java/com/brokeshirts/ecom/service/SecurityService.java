package com.brokeshirts.ecom.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
