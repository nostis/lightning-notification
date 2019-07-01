package com.nostis.service;

import com.nostis.dao.ClientAPICrud;
import com.nostis.model.ClientAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ClientAPIService {
    @Autowired
    private ClientAPICrud clientAPICrud;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addClient(String name, String token, boolean isAdmin) {
        String encoded = bCryptPasswordEncoder.encode(token);
        clientAPICrud.save(new ClientAPI(name, encoded, isAdmin));
    }

    public void deleteClient(String name) {
        clientAPICrud.deleteById(clientAPICrud.findByName(name).get().getId());
    }

    public boolean areCredentialsCorrect(String name, String token) {
        Optional<ClientAPI> client = clientAPICrud.findByName(name);

        return client.filter(value -> bCryptPasswordEncoder.matches(token, value.getPassword())).isPresent();
    }
}
