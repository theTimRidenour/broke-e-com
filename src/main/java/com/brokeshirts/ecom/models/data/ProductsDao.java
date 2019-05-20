package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Products;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProductsDao extends CrudRepository<Products, Integer> {
}
