package com.nostis.config;

import com.nostis.util.EmailNotification;
import com.nostis.util.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Notification EmailNotification(){
        return new EmailNotification();
    }
}
