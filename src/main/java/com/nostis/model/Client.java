package com.nostis.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String token;

    public Client(Long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    public Client(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public Client() {}
}
