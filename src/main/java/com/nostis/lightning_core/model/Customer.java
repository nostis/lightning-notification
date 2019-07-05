package com.nostis.lightning_core.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    @ElementCollection
    private List<Float> location;

    public Customer(String email, List<Float> location) {
        this.email = email;
        this.location = location;
    }
}
