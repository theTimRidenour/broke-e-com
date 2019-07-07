package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrdersDao extends CrudRepository<Orders, Integer> {
    Orders findByToken(String token);
}
