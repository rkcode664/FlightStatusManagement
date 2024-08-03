package com.example.demo.controller;

import com.example.demo.dto.CreateFlightRequest;
import com.example.demo.dto.FlightDTO;
import com.example.demo.model.FlightFilter;
import com.example.demo.service.flight.FlightService;
import com.example.demo.validator.FlightRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "http://localhost:3000")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightRequestValidator flightRequestValidator;


    @PostMapping("/update-status")
    public ResponseEntity<FlightDTO> updateFlightStatus(@RequestBody FlightDTO flightDTO) {
        flightRequestValidator.validateUpdateRequest(flightDTO);
        System.out.println("Received FlightDTO: " + flightDTO);
        return ResponseEntity.ok(flightService.updateFlightStatus(flightDTO));
    }

    @PostMapping(value = "/add-flight")
    public ResponseEntity<FlightDTO> addFlight(@RequestBody CreateFlightRequest createFlightRequest) throws IllegalAccessException {
        flightRequestValidator.validateCreateRequest(createFlightRequest);
        return ResponseEntity.ok(flightService.addFlight(createFlightRequest));
    }


    @PostMapping("/filter")
    public ResponseEntity<List<FlightDTO>> getFlightsByFilter(@RequestBody FlightFilter filter) {
        return ResponseEntity.ok(flightService.getFlightsByFilter(filter));
    }
}
