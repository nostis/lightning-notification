package com.nostis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Runnable implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Runnable.class);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.currentThread().join();
    }
}
