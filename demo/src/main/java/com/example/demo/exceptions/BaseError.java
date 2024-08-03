package com.example.demo.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseError {
    private Integer code;
    private String description;
    private List<String> errorMessage;

    public BaseError(Integer code, String description){
        this.code = code;
        this.description = description;
    }
}
