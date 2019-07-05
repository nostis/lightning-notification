package com.nostis.integration.client_api;

import com.nostis.rest_api.dao.ClientAPICrud;
import com.nostis.rest_api.model.ClientAPI;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientAPICrudTest {
    @Autowired
    private ClientAPICrud clientAPICrud;

    @Before
    public void setUp() {
        clientAPICrud.save(new ClientAPI(1L, "name", "password", false));
        clientAPICrud.save(new ClientAPI(2L, "name2", "token2", false));
    }

    @Test
    public void whenFindByName_thenReturnAppropriateClient() {
        Assert.assertEquals(Long.valueOf(1L), clientAPICrud.findByName("name").get().getId());
        Assert.assertEquals("name", clientAPICrud.findByName("name").get().getName());
        Assert.assertEquals("password", clientAPICrud.findByName("name").get().getPassword());
    }
}
