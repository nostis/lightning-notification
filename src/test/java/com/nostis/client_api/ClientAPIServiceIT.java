package com.nostis.client_api;

import com.nostis.rest_api.dao.ClientAPICrud;
import com.nostis.rest_api.model.ClientAPI;
import com.nostis.rest_api.service.ClientAPIService;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientAPIServiceIT {
    @Autowired
    private ClientAPICrud clientAPICrud;

    @Autowired
    private ClientAPIService clientAPIService;

    @Test
    public void whenAddClient_thenAddClientWithHashedToken() {
        clientAPIService.addClient("name", "password", false);

        Assert.assertEquals("name", clientAPICrud.findById(1L).get().getName());
        Assert.assertEquals(60, clientAPICrud.findById(1L).get().getPassword().length());
        Assert.assertNotEquals("password", clientAPICrud.findById(1L).get().getPassword());
        Assert.assertEquals(1, StreamSupport.stream(clientAPICrud.findAll().spliterator(), false).count());
    }

    @Test
    public void whenDeleteClient_thenDeleteClient() {
        clientAPIService.addClient("name", "password", false);
        clientAPIService.deleteClient("name");

        Assert.assertEquals(0, StreamSupport.stream(clientAPICrud.findAll().spliterator(), false).count());
    }

    @Test
    public void whenCheckCredentials_thenShouldBeValid() {
        ClientAPI clientAPI = new ClientAPI(1L, "name", "$2a$12$sqZmvf/Xw2LGNpPxsp87ueEz0.FmSXzwyX02YpQSB8w2V5baGfal2", false); //hashed 'password' with strength 12
        clientAPICrud.save(clientAPI);
        clientAPIService.addClient("name2", "password2", false);

        Assert.assertTrue(clientAPIService.areCredentialsCorrect("name", "password"));
        Assert.assertTrue(clientAPIService.areCredentialsCorrect("name2", "password2"));
    }

}
