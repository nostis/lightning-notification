package com.nostis.task;

import com.nostis.service.LightningService;
import com.nostis.util.EmailInformations;
import com.nostis.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationSender {

    @Autowired
    @Qualifier("EmailNotification")
    private Notification notification;

    @Autowired
    private LightningService lightningService;

    @Scheduled(fixedRate = 60000)
    public void sendNotification(){
        if(lightningService.getAmountOfLightnings() > 0){
            notification.sendNotification(new EmailInformations("<e-mail>", "Lightning registered"), "There is storm around your city!");
        }
    }
}
