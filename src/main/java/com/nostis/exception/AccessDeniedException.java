package com.nostis.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;

//@ResponseStatus(HttpStatus.FORBIDDEN)
@Data
public class AccessDeniedException extends RuntimeException {
    private Timestamp timestamp;
    private String message;
    private String path;

    public AccessDeniedException(Timestamp timestamp, String message, String path) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }
}
