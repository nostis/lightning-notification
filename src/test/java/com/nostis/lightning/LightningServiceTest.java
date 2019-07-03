package com.nostis.lightning;

import com.nostis.lightning_core.dao.LightningCrud;
import com.nostis.lightning_core.model.Lightning;
import com.nostis.lightning_core.service.LightningService;
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

    private ArrayList<Float> area;
    private List<Lightning> lightnings;

    @Before
    public void setUp() {
        area = new ArrayList<>();
        lightnings = new ArrayList<>();

        area.add(1.23F);
        area.add(3.45F);

        Lightning lightningOne = new Lightning(1L, 0, 0F, "direction", 0, new GregorianCalendar(2019, Calendar.JULY, 19), area);
        Lightning lightningTwo = new Lightning(2L, 0, 0F, "direction", 0, new GregorianCalendar(2019, Calendar.JULY, 18), area);
        Lightning lightningThree = new Lightning(3L, 0, 0F, "direction", 0, new GregorianCalendar(2019, Calendar.JULY, 17), area);
        Lightning lightningFour = new Lightning(4L, 0, 0F, "direction", 0, new GregorianCalendar(2019, Calendar.JULY, 16), area);

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

