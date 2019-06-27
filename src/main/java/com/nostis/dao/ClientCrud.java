package com.nostis.dao;

import com.nostis.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientCrud extends CrudRepository<Client, Long> {
    Optional<Client> findByName(String name);
}
