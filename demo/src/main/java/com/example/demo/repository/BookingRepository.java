package com.example.demo.repository;

import com.example.demo.model.Booking;
import com.example.demo.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT b.user.email FROM Booking b WHERE b.flightNumber = :flightNumber")
    List<String> findUserEmailsByFlightNumber(String flightNumber);
}
