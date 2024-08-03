package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateFlightRequest {
    @NotNull(message = "Flight Number cannot be null")
    private String flightNumber;

    @NotNull(message = "Airline cannot be empty")
    private String airline;

    @NotNull(message = "Origin cannot be empty")
    private String origin;

    @NotNull(message = "Destination cannot be empty")
    private String destination;

    @NotNull(message = "Status cannot be empty")
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private String gate;
}

