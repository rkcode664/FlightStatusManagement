package com.example.demo.validator;

import com.example.demo.dto.CreateFlightRequest;
import com.example.demo.dto.FlightDTO;
import com.example.demo.enums.FlightStatus;
import com.example.demo.model.FlightFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightRequestValidator {
    public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String value) {
        EnumSet<E> enumSet = EnumSet.allOf(enumClass);
        return enumSet.stream().anyMatch(e -> e.name().equals(value));
    }

    public static <E extends Enum<E>> List<String> getValidEnumValues(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
    public void validateCreateRequest(CreateFlightRequest createFlightRequest) throws IllegalAccessException {
        if(ObjectUtils.isEmpty(createFlightRequest.getFlightNumber())){
            throw new IllegalArgumentException("Flight number is required");
        }
        if(ObjectUtils.isEmpty(createFlightRequest.getAirline())){
            throw new IllegalArgumentException("Airline is required");
        }
        if(ObjectUtils.isEmpty(createFlightRequest.getDestination())){
            throw new IllegalArgumentException("Destination is required");
        }
        if(ObjectUtils.isEmpty(createFlightRequest.getStatus())){
            throw new IllegalArgumentException("Status is required");
        }
        if(!ObjectUtils.isEmpty(createFlightRequest.getStatus())){

            if (!isValidEnum(FlightStatus.class, createFlightRequest.getStatus())) {
                List<String> validValues = getValidEnumValues(FlightStatus.class);

                throw  new IllegalArgumentException("invalid status type " + createFlightRequest.getStatus()+ ". Valid values are: " + validValues);
            }
        }
        if(ObjectUtils.isEmpty(createFlightRequest.getDate())){
            throw new IllegalArgumentException("Date is required");
        }
        if(ObjectUtils.isEmpty(createFlightRequest.getDepartureTime())){
            throw new IllegalArgumentException("Departure Time is required");
        }
        if(ObjectUtils.isEmpty(createFlightRequest.getArrivalTime())){
            throw new IllegalArgumentException("Arrival Time is required");
        }
        if(ObjectUtils.isEmpty(createFlightRequest.getOrigin())){
            throw new IllegalArgumentException("Origin is required");
        }

    }

    public void validateUpdateRequest(FlightDTO flightDTO){
        if(ObjectUtils.isEmpty(flightDTO.getFlightNumber())){
            throw new IllegalArgumentException("Flight number is required");
        }
        if(!ObjectUtils.isEmpty(flightDTO.getStatus())){
            if (!isValidEnum(FlightStatus.class, flightDTO.getStatus())) {
                List<String> validValues = getValidEnumValues(FlightStatus.class);

                throw  new IllegalArgumentException("invalid status type " + flightDTO.getStatus()+ ". Valid values are: " + validValues);
            }
        }
        if (!ObjectUtils.isEmpty(flightDTO.getArrivalTime()) && !ObjectUtils.isEmpty(flightDTO.getDepartureTime())){
            if(flightDTO.getArrivalTime().before(flightDTO.getDepartureTime())){
                throw new IllegalArgumentException("Arrival time should be after departure time");
            }
        }
    }
}
