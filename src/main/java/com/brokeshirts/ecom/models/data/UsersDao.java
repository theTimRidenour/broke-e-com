package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UsersDao extends CrudRepository<Users, Integer> {
    Users findByEmail(String email);
}
