package com.nostis;

import com.nostis.service.ClientAPIService;
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
    public CommandLineRunner demoData(ClientAPIService clientAPIService) {
        return args -> {
          clientAPIService.addClient("client", "password", true);
          clientAPIService.addClient("client2", "password", false);
        };
    }
}