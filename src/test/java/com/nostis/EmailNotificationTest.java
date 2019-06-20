package com.nostis;

import com.nostis.service.EmailService;
import com.nostis.util.EmailInformations;
import com.nostis.util.EmailNotification;
import com.nostis.util.Informations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailNotificationTest {
    @MockBean
    private EmailService emailService;

    @Autowired
    private EmailNotification emailNotification;

    @Test
    public void whenSendNotification_thenSendEmail() throws MessagingException {
        emailNotification.sendNotification(new EmailInformations("to", "from", "subject"), "content");
        Mockito.verify(emailService).sendMail("to", "from", "subject", "content");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenSendNotificationWithWrongTypeOfInformations_thenThrowException() {
        Informations differentType = new Informations() {
        };

        emailNotification.sendNotification(differentType, "content");
    }
}