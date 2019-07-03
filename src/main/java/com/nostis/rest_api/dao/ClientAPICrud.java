package com.nostis.rest_api.dao;

import com.nostis.rest_api.model.ClientAPI;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientAPICrud extends CrudRepository<ClientAPI, Long> {
    Optional<ClientAPI> findByName(String name);
}
