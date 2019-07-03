package com.nostis.rest_api.service;

import com.nostis.rest_api.dao.ClientAPICrud;
import com.nostis.rest_api.exception.ClientAlreadyExist;
import com.nostis.rest_api.model.ClientAPI;
import com.nostis.rest_api.model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private ClientAPICrud clientAPICrud;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientAPI> clientAPI = clientAPICrud.findByName(username);

        if(clientAPI.isPresent()){
            return new User(clientAPI.get().getName(), clientAPI.get().getPassword(), Collections.emptyList());
        }
        else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public boolean isUserAdmin(String username) throws UsernameNotFoundException {
        Optional<ClientAPI> clientAPI = clientAPICrud.findByName(username);

        if(clientAPI.isPresent()){
            return clientAPI.get().isAdmin();
        }
        else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public ClientAPI save(SimpleUser simpleUser){
        if(clientAPICrud.findByName(simpleUser.getName()).isPresent()){
            throw new ClientAlreadyExist("Client with name: '" + simpleUser.getName() + "' already exists");
        }

        ClientAPI clientAPI = new ClientAPI();

        clientAPI.setName(simpleUser.getName());
        clientAPI.setAdmin(simpleUser.isAdmin());
        clientAPI.setPassword(bCryptPasswordEncoder.encode(simpleUser.getPassword()));

        return clientAPICrud.save(clientAPI);
    }
}
