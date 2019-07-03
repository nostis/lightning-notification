package com.nostis.rest_api.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotExist extends RuntimeException {
    public ClientNotExist(String message) {
        super(message);
    }
}
