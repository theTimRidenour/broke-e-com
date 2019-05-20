package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Addresses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AddressesDao extends CrudRepository<Addresses, Integer> {
}
