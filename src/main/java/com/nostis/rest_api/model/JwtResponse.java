package com.nostis.rest_api.model;

import lombok.Data;

@Data
public class JwtResponse {
    private final String token;
    private final String expiration;

    public JwtResponse(String token, String expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}
