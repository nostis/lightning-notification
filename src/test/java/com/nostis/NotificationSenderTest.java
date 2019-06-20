package com.nostis;

import com.nostis.service.LightningService;
import com.nostis.task.NotificationSender;
import com.nostis.util.EmailNotification;
import com.nostis.util.Informations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationSenderTest {
    @MockBean
    private EmailNotification emailNotification;

    @MockBean
    private LightningService lightningService;

    @Autowired
    private NotificationSender notificationSender;

    @Before
    public void setUp() {
        Mockito.doNothing().when(emailNotification).sendNotification(Mockito.any(Informations.class), Mockito.anyString());
    }

    @Test
    public void whenMoreThanOneLightning_thenSendNotification() {
        Mockito.when(lightningService.getAmountOfLightnings()).thenReturn(3L);
        notificationSender.sendNotification();

        Mockito.verify(emailNotification, Mockito.times(1)).sendNotification(Mockito.any(Informations.class), Mockito.anyString());
    }

    @Test
    public void whenZeroLightnings_thenDoNothing() {
        Mockito.when(lightningService.getAmountOfLightnings()).thenReturn(0L);
        notificationSender.sendNotification();

        Mockito.verify(emailNotification, Mockito.times(0)).sendNotification(Mockito.any(Informations.class), Mockito.anyString());
    }
}
