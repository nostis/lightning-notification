package com.nostis.rest_api_controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nostis.rest_api.model.SimpleUser;
import com.nostis.rest_api.service.ClientAPIService;
import com.nostis.rest_api.util.JwtTokenUtil;
import com.nostis.rest_api.service.JwtUserDetailsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2db")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JwtAuthenticationControllerTest {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientAPIService clientAPIService;


    @Before
    public void setUp() {
        clientAPIService.addClient("client", "password", true);
        clientAPIService.addClient("client2", "password", false);
    }

    @Test
    public void whenAuthenticate_thenGetTokenAndExpirationDate() throws Exception {
        SimpleUser client = new SimpleUser();
        client.setName("client");
        client.setPassword("password");

        String bodyJson = mapObjectToJson(client);

        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andExpect(content().string(containsString("expiration")));
    }

    @Test
    public void whenAuthenticateWithBadCredentials_thenGetForbidden() throws Exception {
        SimpleUser client = new SimpleUser();
        client.setName("different");
        client.setPassword("different");

        String bodyJson = mapObjectToJson(client);

        String error = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyJson))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResolvedException().getMessage();

        Assert.assertTrue(error.contains("Bad credentials"));
    }

    @Test
    public void whenRegister_thenReturnRegisteredClient() throws Exception {
        String token = getTokenForUser("client");

        SimpleUser userToRegister = new SimpleUser();
        userToRegister.setName("somename");
        userToRegister.setPassword("somepassword");
        userToRegister.setAdmin(false);

        String bodyJson = mapObjectToJson(userToRegister);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(bodyJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("name")))
                .andExpect(content().string(containsString("password")))
                .andExpect(content().string(containsString("admin")));
    }

    @Test
    public void whenRegisterWithAlreadyExistedUser_thenGetConflict() throws Exception {
        String token = getTokenForUser("client");

        SimpleUser userToRegister = new SimpleUser();
        userToRegister.setName("client");
        userToRegister.setPassword("password");
        userToRegister.setAdmin(false);

        String bodyJson = mapObjectToJson(userToRegister);

        String error = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(bodyJson))
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn().getResolvedException().getMessage();

        Assert.assertTrue(error.contains("Client with name: 'client' already exists"));
    }

    @Test
    public void whenRegisterWithAdminFalse_thenGetUnauthorized() throws Exception {
        String token = getTokenForUser("client2");

        SimpleUser userToRegister = new SimpleUser();
        userToRegister.setName("client");
        userToRegister.setPassword("password");
        userToRegister.setAdmin(false);

        String bodyJson = mapObjectToJson(userToRegister);

        String error = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(bodyJson))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn().getResolvedException().getMessage();

        Assert.assertTrue(error.contains("You are not allowed to add new api clients"));
    }

    @Test
    public void whenDeleteClient_thenDeleteAndReturnUsername() throws Exception {
        String token = getTokenForUser("client");

        String returned = mockMvc.perform(delete("/delete/{username}", "client2")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(returned.contains("client2"));
    }

    @Test
    public void whenDeleteClientWhoIsNotExist_thenGetNotFound() throws Exception {
        String token = getTokenForUser("client");

        String returned = mockMvc.perform(delete("/delete/{username}", "notexist")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException().getMessage();

        Assert.assertTrue(returned.contains("Client with name: 'notexist' not exists"));
    }

    private String mapObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();

        String bodyJson = ow.writeValueAsString(object);

        return bodyJson;
    }

    private String getTokenForUser(String username) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }
}
