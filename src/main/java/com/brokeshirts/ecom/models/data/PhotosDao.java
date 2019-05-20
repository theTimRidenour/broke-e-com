package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Photos;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PhotosDao extends CrudRepository<Photos, Integer> {
}
