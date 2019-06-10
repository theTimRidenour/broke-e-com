package com.brokeshirts.ecom.service;

import com.brokeshirts.ecom.models.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
