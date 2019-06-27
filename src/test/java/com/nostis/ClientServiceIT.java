package com.nostis;

import com.nostis.dao.ClientCrud;
import com.nostis.model.Client;
import com.nostis.service.ClientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("h2db")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientServiceIT {
    @Autowired
    private ClientCrud clientCrud;

    @Autowired
    private ClientService clientService;

    @Test
    public void whenAddClient_thenAddClientWithHashedToken() {
        clientService.addClient("name", "token");

        Assert.assertEquals("name", clientCrud.findById(1L).get().getName());
        Assert.assertEquals(60, clientCrud.findById(1L).get().getToken().length());
        Assert.assertNotEquals("token", clientCrud.findById(1L).get().getToken());
        Assert.assertEquals(1, StreamSupport.stream(clientCrud.findAll().spliterator(), false).count());
    }

    @Test
    public void whenDeleteClient_thenDeleteClient() {
        clientService.addClient("name", "token");
        clientService.deleteClient("name");

        Assert.assertEquals(0, StreamSupport.stream(clientCrud.findAll().spliterator(), false).count());
    }

    @Test
    public void whenCheckCredentials_thenShouldBeValid() {
        Client client = new Client(1L, "name", "$2a$12$sqZmvf/Xw2LGNpPxsp87ueEz0.FmSXzwyX02YpQSB8w2V5baGfal2"); //hashed 'password' with strength 12
        clientCrud.save(client);
        clientService.addClient("name2", "password2");

        Assert.assertTrue(clientService.areCredentialsCorrect("name", "password"));
        Assert.assertTrue(clientService.areCredentialsCorrect("name2", "password2"));
    }

}
