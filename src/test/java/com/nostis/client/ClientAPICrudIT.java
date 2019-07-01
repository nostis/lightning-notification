package com.nostis.client;

import com.nostis.dao.ClientAPICrud;
import com.nostis.model.ClientAPI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("h2db")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientAPICrudIT {
    @Autowired
    private ClientAPICrud clientAPICrud;

    @Before
    public void setUp() {
        clientAPICrud.save(new ClientAPI(1L, "name", "token"));
        clientAPICrud.save(new ClientAPI(2L, "name2", "token2"));
    }

    @Test
    public void whenFindByName_thenReturnAppropriateClient() {
        Assert.assertEquals(Long.valueOf(1L), clientAPICrud.findByName("name").get().getId());
        Assert.assertEquals("name", clientAPICrud.findByName("name").get().getName());
        Assert.assertEquals("token", clientAPICrud.findByName("name").get().getToken());
    }
}