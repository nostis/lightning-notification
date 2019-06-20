package com.nostis.task;

import com.nostis.service.LightningService;
import com.nostis.util.EmailInformations;
import com.nostis.util.EmailNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationSender {
    @Autowired
    private EmailNotification emailNotification;

    @Autowired
    private LightningService lightningService;

    @Scheduled(fixedRate = 60000)
    public void sendNotification(){
        if(lightningService.getAmountOfLightnings() > 0){
            emailNotification.sendNotification(new EmailInformations("<e-mail>", "<e-mail>", "Lightning registered"), "There is storm around your city!");
        }
    }
}
