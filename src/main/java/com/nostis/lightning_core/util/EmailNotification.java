package com.nostis.lightning_core.util;

import com.nostis.lightning_core.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class EmailNotification implements Notification {
    @Autowired
    private EmailService emailService;

    @Override
    public void sendNotification(Informations informations, String content) {
        if(informations instanceof EmailInformations){
            try {
                emailService.sendMail(((EmailInformations) informations).getTo(), ((EmailInformations) informations).getFrom(), ((EmailInformations) informations).getSubject(), content);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        else{
            throw new UnsupportedOperationException();
        }
    }
}
