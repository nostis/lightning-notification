package com.nostis.lightning_core.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
public class CustomerNotExist extends RuntimeException {
    public CustomerNotExist(String message) {
        super(message);
    }
}
