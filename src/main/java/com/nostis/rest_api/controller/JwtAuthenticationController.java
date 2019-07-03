package com.nostis.rest_api.controller;

import com.nostis.exception.AccessDeniedException;
import com.nostis.model.ClientAPI;
import com.nostis.model.SimpleUser;
import com.nostis.security.JwtResponse;
import com.nostis.security.JwtTokenUtil;
import com.nostis.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;;import java.util.Date;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody SimpleUser authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getName(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getName());

        final String token = jwtTokenUtil.generateToken(userDetails);
        final Long expiration = jwtTokenUtil.getExpirationDateFromToken(token).getTime() / 100;

        return new JwtResponse(token, expiration.toString());
    }

    @PostMapping("/register")
    public ClientAPI saveUser(@RequestBody SimpleUser simpleUser, @RequestHeader String authorization) {

        String username = jwtTokenUtil.getUsernameFromToken(authorization.substring(7));
        UserDetails user =  userDetailsService.loadUserByUsername(username);
        if(!userDetailsService.isUserAdmin(username)){
            throw new AccessDeniedException("You are not allowed to add new api clients");
        }

        return userDetailsService.save(simpleUser);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e){
            throw new Exception("User disabled", e);
        }
        catch (BadCredentialsException e) {
            throw new com.nostis.exception.BadCredentialsException("Bad credentials");
        }
    }
}
