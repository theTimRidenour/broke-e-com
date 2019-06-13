package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}