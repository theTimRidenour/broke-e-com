package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Categories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoriesDao extends CrudRepository<Categories, Integer> {
}
