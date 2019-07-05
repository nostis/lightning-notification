package com.nostis.lightning_core.dao;

import com.nostis.lightning_core.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerCrud extends CrudRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
