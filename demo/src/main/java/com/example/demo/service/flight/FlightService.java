package com.example.demo.service.flight;

import com.example.demo.dto.CreateFlightRequest;
import com.example.demo.dto.FlightDTO;
import com.example.demo.enums.ReasonCode;
import com.example.demo.enums.FlightStatus;
import com.example.demo.exceptions.CustomException;
import com.example.demo.model.Flight;
import com.example.demo.model.FlightFilter;
import com.example.demo.publisher.FlightStatusPublisher;
import com.example.demo.repository.FlightRepository;
import com.example.demo.utils.FlightSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FlightService {
    @Autowired
    FlightRepository flightRepository;

    @Autowired
    FlightStatusPublisher flightStatusPublisher;

    public FlightDTO createFlightRecord(FlightDTO flightDTO){
        Flight flight = new Flight(flightDTO);
        Flight existingFlight =flightRepository.findByFlightNumber(flightDTO.getFlightNumber());
        if(!ObjectUtils.isEmpty(existingFlight)){
            throw new CustomException(" Flight number exist already: " + flightDTO.getFlightNumber(), HttpStatus.BAD_REQUEST, ReasonCode.BAD_ARGUMENT_ERROR);
        }
        Flight savedFlight = flightRepository.save(flight);
        FlightDTO savedFlightDTO = new FlightDTO();
        BeanUtils.copyProperties(savedFlight, savedFlightDTO);
        return savedFlightDTO;

    }
    public  FlightDTO createFlightDTO(CreateFlightRequest createFlightRequest){
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightNumber(createFlightRequest.getFlightNumber());
        flightDTO.setAirline(createFlightRequest.getAirline());
        flightDTO.setOrigin(createFlightRequest.getOrigin());
        flightDTO.setDestination(createFlightRequest.getDestination());
        flightDTO.setStatus(createFlightRequest.getStatus());
        flightDTO.setDate(createFlightRequest.getDate());
        flightDTO.setDepartureTime(createFlightRequest.getDepartureTime());
        flightDTO.setArrivalTime(createFlightRequest.getArrivalTime());
        flightDTO.setGate(createFlightRequest.getGate());
        return flightDTO;
    }


    public FlightDTO addFlight(CreateFlightRequest createFlightRequest) {
        FlightDTO flightDTO = createFlightDTO(createFlightRequest);
        return createFlightRecord(flightDTO);
    }

    public FlightDTO updateFlightStatus(FlightDTO flightDTO) {

        Flight existingFlight =flightRepository.findByFlightNumber(flightDTO.getFlightNumber());
        if(ObjectUtils.isEmpty(existingFlight)){
            throw new CustomException(" Flight number does not exist: " + flightDTO.getFlightNumber(), HttpStatus.BAD_REQUEST, ReasonCode.BAD_ARGUMENT_ERROR);
        }
        if(flightDTO.getStatus()!=null){
            if(flightDTO.getStatus().equals(FlightStatus.CANCELLED.toString())) {
                existingFlight.setGate(null);
            }
            existingFlight.setStatus(flightDTO.getStatus());
        }
        if(flightDTO.getGate()!=null){
            existingFlight.setGate(flightDTO.getGate());
        }
        if(flightDTO.getArrivalTime()!=null){
            existingFlight.setArrivalTime(flightDTO.getArrivalTime());
        }
        if(flightDTO.getDepartureTime()!=null){
            if(flightDTO.getDepartureTime().before(existingFlight.getDepartureTime())){
                throw new CustomException(" Departure time must be updated to future date: " + flightDTO.getDepartureTime(), HttpStatus.BAD_REQUEST, ReasonCode.BAD_ARGUMENT_ERROR);
            }
            existingFlight.setDepartureTime(flightDTO.getDepartureTime());
        }
        Flight savedFlight = flightRepository.save(existingFlight);
        FlightDTO savedFlightDTO = new FlightDTO();
        BeanUtils.copyProperties(savedFlight, savedFlightDTO);
        //Publishing the event to kafka
        flightStatusPublisher.publish(savedFlightDTO);
        return savedFlightDTO;
    }


    public List<FlightDTO> getFlightsByFilter(FlightFilter filter) {
        FlightDTO flightDTO = new FlightDTO();
        BeanUtils.copyProperties(filter, flightDTO);
        Specification<Flight> specification = new FlightSpecification(flightDTO);
         List<Flight> flights = flightRepository.findAll(specification);
        List<FlightDTO> flightDTOs = new ArrayList<>();
         for(Flight flight : flights){
             FlightDTO dto = new FlightDTO();
             BeanUtils.copyProperties(flight, dto);
             flightDTOs.add(dto);
         }
        return flightDTOs;
    }
}
