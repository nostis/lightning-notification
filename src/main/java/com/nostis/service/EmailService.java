package com.nostis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendMail(String to, String from, String subject, String text) throws MessagingException {
        MimeMessage mail = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(text);

        emailSender.send(mail);
    }

}
