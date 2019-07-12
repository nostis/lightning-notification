package com.nostis.lightning_core.task;

import com.nostis.lightning_core.service.CustomerService;
import com.nostis.lightning_core.service.LightningService;
import com.nostis.lightning_core.util.EmailInformations;
import com.nostis.lightning_core.util.EmailNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NotificationSender {

    @Autowired
    private EmailNotification emailNotification;

    @Autowired
    private LightningService lightningService;

    @Autowired
    private CustomerService customerService;

    @Transactional
    public void sendNotification(){
        customerService.getAllCustomers().forEach(c ->{
            lightningService.getAllLightnings().forEach(l -> {
                if(l.getCount() > 0 && c.getLocation().get(0).equals(l.getArea().get(0)) && c.getLocation().get(0).equals(l.getArea().get(0))){
                    emailNotification.sendNotification(new EmailInformations(c.getEmail(), "<from>", "Lightning registered"), "There is storm around your city! \n Is in distance: " + l.getDistance() + " from you \n Is going to: " + l.getDirection() + " direction");
                }
            });
        });
    }
}
