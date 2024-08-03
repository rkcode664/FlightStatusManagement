package com.example.demo.exceptions;

import com.example.demo.enums.ReasonCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<BaseError> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(new BaseError(
                ex.getErrorReasonCode()
                        .getCode(),
                CustomException.BASE_ERROR_MESSAGE.concat(ex.getErrorReasonCode()
                                .getDescription())
                        .concat(". ")
                        .concat(ex.getMessage())
        ), ex.getStatus());
    }
    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<BaseError> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        List<String> errors = new ArrayList<>();
        return new ResponseEntity<>(new BaseError(
                ReasonCode.INVALID_REQUEST_DATA.getCode(),
                ReasonCode.INVALID_REQUEST_DATA.getDescription(),
                List.of(illegalArgumentException.getMessage())
        ), HttpStatus.BAD_REQUEST);
    }
}
