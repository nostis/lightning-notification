package com.nostis.lightning_core.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerAlreadyExist extends RuntimeException {
    public CustomerAlreadyExist(String message) {
        super(message);
    }
}
