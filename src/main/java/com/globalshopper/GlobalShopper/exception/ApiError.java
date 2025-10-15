package com.globalshopper.GlobalShopper.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ApiError {
    private int status;
    private String error;
    private String message;
    private List<String> details;

    public ApiError(HttpStatus status, String message) {
        this(status, message, List.of());
    }

    public ApiError(HttpStatus status, String message, List<String> details) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.details = details;
    }

}
