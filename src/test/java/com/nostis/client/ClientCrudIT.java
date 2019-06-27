package com.nostis.client;

import com.nostis.dao.ClientCrud;
import com.nostis.model.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("h2db")
@RunWith(SpringRunner.class)
public class ClientCrudIT {
    @Autowired
    private ClientCrud clientCrud;

    @Before
    public void setUp() {
        clientCrud.save(new Client(1L, "name", "token"));
        clientCrud.save(new Client(2L, "name2", "token2"));
    }

    @Test
    public void whenFindByName_thenReturnAppropriateClient() {
        Assert.assertEquals(Long.valueOf(1L), clientCrud.findByName("name").get().getId());
        Assert.assertEquals("name", clientCrud.findByName("name").get().getName());
        Assert.assertEquals("token", clientCrud.findByName("name").get().getToken());
    }
}
