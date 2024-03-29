package com.nostis.unit.lightning;

import com.nostis.lightning_core.service.LightningService;
import com.nostis.lightning_core.task.NotificationSender;
import com.nostis.lightning_core.util.EmailNotification;
import com.nostis.lightning_core.util.Informations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NotificationSenderTest {
    @Mock
    private EmailNotification emailNotification;

    @Mock
    private LightningService lightningService;

    @InjectMocks
    private NotificationSender notificationSender;

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
