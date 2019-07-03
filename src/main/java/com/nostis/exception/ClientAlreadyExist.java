package com.nostis.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.CONFLICT)
public class ClientAlreadyExist extends RuntimeException {
    public ClientAlreadyExist(String message) {
        super(message);
    }
}
