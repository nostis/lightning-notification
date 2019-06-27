package com.nostis.dao;

import com.nostis.model.ClientAPI;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientAPICrud extends CrudRepository<ClientAPI, Long> {
    Optional<ClientAPI> findByName(String name);
}
