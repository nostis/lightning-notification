package com.nostis.security;

import lombok.Data;

@Data
public class JwtResponse {
    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
