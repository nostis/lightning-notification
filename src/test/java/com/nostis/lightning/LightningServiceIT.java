package com.nostis;

import com.nostis.lightning_core.dao.LightningCrud;
import com.nostis.lightning_core.model.Lightning;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("lightning")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LightningServiceIT {
    @Autowired
    private LightningCrud lightningCrud;

    private ArrayList<Float> area;
    private List<Lightning> lightnings;

    @Before
    public void setUp() {
        area = new ArrayList<>();
        lightnings = new ArrayList<>();

        area.add(1.23F);
        area.add(3.45F);

        Calendar date = new GregorianCalendar(2019, Calendar.JULY, 19);

        Lightning lightningOne = new Lightning(1L, 0, 0F, "direction", 0, date, area);
        Lightning lightningTwo = new Lightning(2L, 0, 0F, "direction", 0, date, area);
        Lightning lightningThree = new Lightning(3L, 0, 0F, "direction", 0, date, area);

        lightnings.add(lightningOne);
        lightnings.add(lightningTwo);
        lightnings.add(lightningThree);
    }

    @Test
    @Transactional
    public void whenSaveAllAndFindAll_thenSaveAndFind() {
        lightningCrud.saveAll(lightnings);

        List<Lightning> result = StreamSupport
                .stream(lightningCrud.findAll().spliterator(), false)
                .collect(Collectors.toList());

        Assert.assertEquals(lightnings, result);
        Assert.assertNotSame(lightnings, result);
    }

    @Test
    @Transactional
    public void whenDeleteById_thenDeleteRecordWithThatId() {
        lightningCrud.saveAll(lightnings);
        lightningCrud.deleteById(1L);

        List<Lightning> result = StreamSupport
                .stream(lightningCrud.findAll().spliterator(), false)
                .collect(Collectors.toList());

        Assert.assertEquals(2, result.size());
        Assert.assertFalse(result.contains(lightnings.get(0)));
    }
}