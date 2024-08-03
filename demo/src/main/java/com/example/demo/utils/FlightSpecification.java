package com.example.demo.utils;
import com.example.demo.dto.FlightDTO;
import com.example.demo.model.FlightFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.model.Flight;


public class FlightSpecification implements Specification<Flight>{
    private final FlightDTO flightDTO;

    public FlightSpecification(FlightDTO flightDTO) {
        this.flightDTO = flightDTO;
    }
    @lombok.Generated
    @Override
    public Predicate toPredicate(Root<Flight> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder){
        List<Predicate> predicates = new ArrayList<>();
        if (!ObjectUtils.isEmpty(flightDTO.getFlightNumber())) {
        predicates.add(criteriaBuilder.equal(root.get("flightNumber"), flightDTO.getFlightNumber()));
        }
        if (!ObjectUtils.isEmpty(flightDTO.getDate())) {
            predicates.add(criteriaBuilder.equal(root.get("date"), flightDTO.getDate()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
