package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Sizes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SizesDao extends CrudRepository<Sizes, Integer> {
}
