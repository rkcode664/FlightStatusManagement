package com.example.demo.repository;

import com.example.demo.model.Flight;
import com.example.demo.model.FlightFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID>, JpaSpecificationExecutor<Flight> {

    Flight findByFlightNumber(String flightNumber);

}
