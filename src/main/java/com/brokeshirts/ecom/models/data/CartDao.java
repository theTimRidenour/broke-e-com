package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CartDao extends CrudRepository<Cart, Integer> {
    Cart findByUserId(int userId);
}
