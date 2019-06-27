package com.nostis.service;

import com.nostis.dao.ClientCrud;
import com.nostis.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientCrud clientCrud;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addClient(String name, String token) {
        String encoded = bCryptPasswordEncoder.encode(token);
        clientCrud.save(new Client(name, encoded));
    }

    public void deleteClient(String name) {
        clientCrud.deleteById(clientCrud.findByName(name).get().getId());
    }

    public boolean areCredentialsCorrect(String name, String token) {
        Optional<Client> client = clientCrud.findByName(name);

        return client.filter(value -> bCryptPasswordEncoder.matches(token, value.getToken())).isPresent();
    }
}
