package com.example.demo.exceptions;

import com.example.demo.enums.ReasonCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException{

    public static final String BASE_ERROR_MESSAGE = " Data is not valid : ";
    private final HttpStatus status;
    private final ReasonCode errorReasonCode;

    public CustomException(String message, HttpStatus status, ReasonCode errorReasonCode){
        super(message);
        this.status = status;
        this.errorReasonCode = errorReasonCode;
    }
}
