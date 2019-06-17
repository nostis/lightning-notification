package com.nostis.service;

import com.nostis.dao.LightningCrud;
import com.nostis.model.Lightning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LightningService {
    @Autowired
    private LightningCrud lightningCrud;

    public void saveLightnings(List<Lightning> lightnings){
        lightningCrud.saveAll(lightnings);
    }

    public void deleteLightningsOlderThan(Date date){
        Iterable<Lightning> iterable = lightningCrud.findAll();

        List<Lightning> allLightnings = StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        List<Lightning> lightningsToDelete = allLightnings
                .stream()
                .filter(lightning -> lightning.getOccurredAt().compareTo(date) <= 0)
                .collect(Collectors.toList());

        lightningsToDelete.forEach(lightning -> lightningCrud.deleteById(lightning.getId()));
    }
}