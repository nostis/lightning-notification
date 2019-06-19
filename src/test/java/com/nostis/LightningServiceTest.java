package com.nostis;

import com.nostis.dao.LightningCrud;
import com.nostis.model.Lightning;
import com.nostis.service.LightningService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
public class LightningServiceTest {
    @TestConfiguration
    static class LightningServiceTestConfiguration{
        @Bean
        public LightningService lightningService(){
            return new LightningService();
        }
    }

    @Autowired
    private LightningService lightningService;

    @MockBean
    private LightningCrud lightningCrud;

    private ArrayList<Float> location;
    private List<Lightning> lightnings;

    @Before
    public void setUp() {
        location = new ArrayList<>();
        lightnings = new ArrayList<>();

        location.add(1.23F);
        location.add(3.45F);

        Lightning lightningOne = new Lightning(1L, new GregorianCalendar(2019, Calendar.JULY, 19), "provider", "type", 0, 0, location);
        Lightning lightningTwo = new Lightning(2L, new GregorianCalendar(2019, Calendar.JULY, 18), "provider", "type", 0, 0, location);
        Lightning lightningThree = new Lightning(3L, new GregorianCalendar(2019, Calendar.JULY, 17), "provider", "type", 0, 0, location);

        lightnings.add(lightningOne);
        lightnings.add(lightningTwo);
        lightnings.add(lightningThree);
    }
    
    @Test
    public void whenSaveLightning_thenSaveLightnings() {
        Mockito.when(lightningCrud.saveAll(Mockito.anyList())).thenReturn(lightnings);

        lightningService.saveLightnings(lightnings);

        Mockito.verify(lightningCrud, Mockito.times(1)).saveAll(lightnings);
    }

    @Test
    public void whenGetAmountOfLightnings_thenReturnAmountOfLightnings() {
        Mockito.when(lightningCrud.findAll()).thenReturn(lightnings);
        Assert.assertEquals(lightnings.size(), lightningService.getAmountOfLightnings());
    }

    @Test
    public void whenDeleteLightningsBefore_thenRemainOnlyYounger() {
        Mockito.when(lightningCrud.findAll()).thenReturn(lightnings);

        lightningService.deleteLightningsBefore(new GregorianCalendar(2019, Calendar.JULY, 19));

        Mockito.verify(lightningCrud, Mockito.times(0)).deleteById(1L);
        Mockito.verify(lightningCrud, Mockito.times(1)).deleteById(2L);
        Mockito.verify(lightningCrud, Mockito.times(1)).deleteById(3L);
    }

}

