package com.nostis.email;

import com.nostis.lightning_core.service.EmailService;
import com.nostis.lightning_core.util.EmailInformations;
import com.nostis.lightning_core.util.EmailNotification;
import com.nostis.lightning_core.util.Informations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import javax.mail.MessagingException;

@RunWith(MockitoJUnitRunner.class)
public class EmailNotificationTest {
    @Mock
    private EmailService emailService;

    @InjectMocks
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