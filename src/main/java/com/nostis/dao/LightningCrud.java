package com.nostis.dao;

import com.nostis.model.Lightning;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightningCrud extends CrudRepository<Lightning, Long> {
}
