package com.example.demo.model;

import com.example.demo.dto.FlightDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


import lombok.Data;

@Entity
@Table(name = "flight")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "flight_number")
    private String flightNumber;
    private String status;
    private String airline;
    private String origin;
    private String destination;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    @Column(name = "departure_time")
    private Timestamp departureTime;

    @Column(name = "arrival_time")
    private Timestamp arrivalTime;

    private String gate;
    @Column(name = "updated_at")
    private Date updatedAt;

    public Flight(FlightDTO flightDTO) {
        this.flightNumber=flightDTO.getFlightNumber();
        this.airline=flightDTO.getAirline();
        this.origin=flightDTO.getOrigin();
        this.destination= flightDTO.getDestination();
        this.status=flightDTO.getStatus();
        this.date=flightDTO.getDate();
        this.gate=flightDTO.getGate();
        this.arrivalTime=flightDTO.getArrivalTime();
        this.departureTime=flightDTO.getDepartureTime();
    }


    @PrePersist
    public void prePersist() {
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }
}
