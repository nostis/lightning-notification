package com.nostis.client;

import com.nostis.dao.ClientAPICrud;
import com.nostis.model.ClientAPI;
import com.nostis.service.ClientAPIService;
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
public class ClientAPIServiceIT {
    @Autowired
    private ClientAPICrud clientAPICrud;

    @Autowired
    private ClientAPIService clientAPIService;

    @Test
    public void whenAddClient_thenAddClientWithHashedToken() {
        clientAPIService.addClient("name", "token");

        Assert.assertEquals("name", clientAPICrud.findById(1L).get().getName());
        Assert.assertEquals(60, clientAPICrud.findById(1L).get().getToken().length());
        Assert.assertNotEquals("token", clientAPICrud.findById(1L).get().getToken());
        Assert.assertEquals(1, StreamSupport.stream(clientAPICrud.findAll().spliterator(), false).count());
    }

    @Test
    public void whenDeleteClient_thenDeleteClient() {
        clientAPIService.addClient("name", "token");
        clientAPIService.deleteClient("name");

        Assert.assertEquals(0, StreamSupport.stream(clientAPICrud.findAll().spliterator(), false).count());
    }

    @Test
    public void whenCheckCredentials_thenShouldBeValid() {
        ClientAPI clientAPI = new ClientAPI(1L, "name", "$2a$12$sqZmvf/Xw2LGNpPxsp87ueEz0.FmSXzwyX02YpQSB8w2V5baGfal2"); //hashed 'password' with strength 12
        clientAPICrud.save(clientAPI);
        clientAPIService.addClient("name2", "password2");

        Assert.assertTrue(clientAPIService.areCredentialsCorrect("name", "password"));
        Assert.assertTrue(clientAPIService.areCredentialsCorrect("name2", "password2"));
    }

}
