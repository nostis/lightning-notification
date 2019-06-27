package com.nostis.email;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.nostis.service.EmailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("email")
public class EmailServiceIT {
    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);

    @Autowired
    private EmailService emailService;

    @Before
    public void setUp() throws MessagingException {
        greenMail.setUser("sender@localhost.com", "sender@localhost.com", "password");
    }

    @Test
    public void whenSendMail_thenShouldBeInInbox() throws MessagingException, IOException {
        emailService.sendMail("user@localhost.com","sender@localhost.com", "subject", "content");

        MimeMessage[] mails = greenMail.getReceivedMessages();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mails[0].writeTo(output);

        Assert.assertEquals("user@localhost.com", mails[0].getRecipients(Message.RecipientType.TO)[0].toString());
        Assert.assertEquals("sender@localhost.com", mails[0].getFrom()[0].toString());
        Assert.assertEquals("subject", mails[0].getSubject());
        Assert.assertTrue(output.toString().contains("content"));
    }
}