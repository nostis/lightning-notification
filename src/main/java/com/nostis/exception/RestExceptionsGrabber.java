package com.nostis.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class RestExceptionsGrabber {
    /*@ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(Exception e, WebRequest request){
        return new ResponseEntity<>("Bad credentials", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }*/
    private HttpHeaders header = new HttpHeaders();

    public RestExceptionsGrabber() {
        header.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> handle(AccessDeniedException e, WebRequest request) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("timestamp", e.getTimestamp().toString());
        body.put("message", e.getMessage());
        body.put("path", e.getPath());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(header).body(body);

        //return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
}
