package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Colors;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ColorsDao extends CrudRepository<Colors, Integer> {
}
