package com.nostis.integration.resti_api_controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nostis.lightning_core.dao.CustomerCrud;
import com.nostis.lightning_core.model.Customer;
import com.nostis.rest_api.service.ClientAPIService;
import com.nostis.rest_api.service.JwtUserDetailsService;
import com.nostis.rest_api.util.JwtTokenUtil;
import io.jsonwebtoken.MalformedJwtException;
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

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("h2db")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LightningControllerTest {

    private static final String PRE_URL = "/lightning";

    @Autowired
    private ClientAPIService clientAPIService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerCrud customerCrud;

    @Before
    public void setUp() {
        clientAPIService.addClient("api_client", "password", true);
    }

    @Test
    public void whenAddCustomer_thenAddCustomerAndReturnCustomer() throws Exception {
        String token = getTokenForUser("api_client");


        Customer customer = new Customer("email", new ArrayList<>(Arrays.asList(5F, 5F)));
        String customerJson = mapObjectToJson(customer);

        String returned = mockMvc.perform(post(PRE_URL + "/addcustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(customerJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(returned.contains("email"));
        Assert.assertTrue(returned.contains("5.0"));
    }

    @Test(expected = MalformedJwtException.class)
    public void whenAddCustomerWithNotValidToken_thenThrowException() throws Exception {
        String token = "bad token";

        Customer customer = new Customer("email", new ArrayList<>(Arrays.asList(5F, 5F)));
        String customerJson = mapObjectToJson(customer);

        mockMvc.perform(post(PRE_URL + "/addcustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(customerJson))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void whenAddCustomerWithAlreadyExistingEmail_thenThrowException() throws Exception {
        Customer customerOne = new Customer("email", new ArrayList<>(Arrays.asList(5F, 5F)));
        Customer customerTwo = new Customer("email", new ArrayList<>(Arrays.asList(5F, 5F)));
        customerCrud.save(customerOne);

        String token = getTokenForUser("api_client");

        String customerTwoJson = mapObjectToJson(customerTwo);

        String error = mockMvc.perform(post(PRE_URL + "/addcustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(customerTwoJson))
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn().getResolvedException().getMessage();

        Assert.assertTrue(error.contains("Customer with email 'email' already exists"));
    }

    @Test
    public void whenRemoveCustomer_thenReturnEmail() throws Exception {
        String token = getTokenForUser("api_client");

        Customer customer = new Customer("email", new ArrayList<>(Arrays.asList(5F, 5F)));

        customerCrud.save(customer);

        String returned = mockMvc.perform(delete(PRE_URL + "/removecustomer/{email}", "email")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(returned.contains("email"));
    }

    @Test
    public void whenRemoveNotExistingCustomer_thenThrowException() throws Exception {
        String token = getTokenForUser("api_client");

        String error = mockMvc.perform(delete(PRE_URL + "/removecustomer/{email}", "notexists")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException().getMessage();

        Assert.assertTrue(error.contains("Customer with email 'notexists' not exists"));
    }

    private String getTokenForUser(String username) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    private String mapObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();

        String bodyJson = ow.writeValueAsString(object);

        return bodyJson;
    }
}
