package com.nostis.service;

import com.nostis.dao.ClientAPICrud;
import com.nostis.model.ClientAPI;
import com.nostis.model.SimpleUser;
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
        ClientAPI clientAPI = new ClientAPI();

        clientAPI.setName(simpleUser.getName());
        clientAPI.setPassword(bCryptPasswordEncoder.encode(simpleUser.getPassword()));

        return clientAPICrud.save(clientAPI);
    }
}
