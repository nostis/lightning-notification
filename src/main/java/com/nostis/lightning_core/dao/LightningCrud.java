package com.nostis.lightning_core.dao;

import com.nostis.lightning_core.model.Lightning;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightningCrud extends CrudRepository<Lightning, Long> {
}
