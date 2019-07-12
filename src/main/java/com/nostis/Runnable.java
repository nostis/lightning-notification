package com.nostis;

import com.nostis.rest_api.dao.ClientAPICrud;
import com.nostis.rest_api.model.ClientAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Runnable {
    public static void main(String[] args) {
        SpringApplication.run(Runnable.class);
    }

    @Bean
    CommandLineRunner commandLineRunner(ClientAPICrud clientAPICrud) {
        return args -> {
            clientAPICrud.save(new ClientAPI(0L, "client", "$2a$12$O1iIOHJ4ZaLJ4gfVlCQjhuGw9JpehgD4TqjxObaYYfgiMFcOb1AKS", true));
        };
    }
}