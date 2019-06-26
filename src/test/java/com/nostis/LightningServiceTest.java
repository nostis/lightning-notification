package com.nostis;

import com.nostis.dao.LightningCrud;
import com.nostis.model.Lightning;
import com.nostis.service.LightningService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class LightningServiceTest {
    @InjectMocks
    private LightningService lightningService;

    @Mock
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
        Lightning lightningFour = new Lightning(4L, new GregorianCalendar(2019, Calendar.JULY, 16), "provider", "type", 0, 0, location);

        lightnings.add(lightningOne);
        lightnings.add(lightningTwo);
        lightnings.add(lightningThree);
        lightnings.add(lightningFour);
    }
    
    @Test
    public void whenSaveLightning_thenSaveLightnings() {
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

        lightningService.deleteLightningsBefore(new GregorianCalendar(2019, Calendar.JULY, 18));

        Mockito.verify(lightningCrud, Mockito.times(0)).deleteById(1L);
        Mockito.verify(lightningCrud, Mockito.times(0)).deleteById(2L);
        Mockito.verify(lightningCrud, Mockito.times(1)).deleteById(3L);
        Mockito.verify(lightningCrud, Mockito.times(1)).deleteById(4L);
    }

}

