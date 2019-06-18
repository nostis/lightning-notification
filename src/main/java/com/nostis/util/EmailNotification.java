package com.nostis.util;

import com.nostis.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;

public class EmailNotification implements Notification {
    @Autowired
    private EmailService emailService;

    @Override
    public void sendNotification(Informations informations) {
        if(informations instanceof EmailInformations){
            try {
                emailService.sendMail(((EmailInformations) informations).getTo(), ((EmailInformations) informations).getSubject(), ((EmailInformations) informations).getContent());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        else{
            throw new UnsupportedOperationException();
        }
    }
}
