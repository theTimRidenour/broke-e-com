package com.brokeshirts.ecom.service;

import com.brokeshirts.ecom.models.Role;
import com.brokeshirts.ecom.models.User;
import com.brokeshirts.ecom.models.data.RoleDao;
import com.brokeshirts.ecom.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<com.brokeshirts.ecom.models.Role>((Collection<? extends Role>) roleDao.findAll()));
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
