package com.nostis.rest_api.model;

import lombok.Data;

@Data
public class SimpleUser {
    private String name;

    private String password;

    private boolean admin;
}
