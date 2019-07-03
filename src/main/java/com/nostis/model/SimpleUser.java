package com.nostis.model;

import lombok.Data;

@Data
public class SimpleUser {
    private String name;

    private String password;

    private boolean admin;
}
