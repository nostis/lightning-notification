package com.nostis.lightning_core.controller;

import com.nostis.lightning_core.model.Customer;
import com.nostis.lightning_core.service.CustomerService;
import com.nostis.rest_api.exception.AccessDeniedException;
import com.nostis.rest_api.service.JwtUserDetailsService;
import com.nostis.rest_api.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lightning")
public class LightningController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/addcustomer")
    public Customer addCustomer(@RequestHeader String authorization, @RequestBody Customer customer) {
        String username = jwtTokenUtil.getUsernameFromToken(authorization.substring(7));

        if(jwtTokenUtil.validateToken(authorization, jwtUserDetailsService.loadUserByUsername(username))) {
            return customerService.addCustomer(customer.getEmail(), customer.getLocation());
        }
        else {
            throw new AccessDeniedException("Something is wrong with your token");
        }
    }
}
