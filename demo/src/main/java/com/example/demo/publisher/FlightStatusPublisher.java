package com.example.demo.publisher;

import com.example.demo.dto.FlightDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FlightStatusPublisher {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String flightStatusTopic = "flight-updates";

    public void publish(FlightDTO flightDTO) {
//        kafkaTemplate.send(TOPIC, message);
        try {
            String message = objectMapper.writeValueAsString(flightDTO);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(flightStatusTopic, flightDTO.getFlightNumber(), message);
            SendResult<String, String> result = kafkaTemplate.send(producerRecord).get();
            RecordMetadata metadata = result.getRecordMetadata();
            int partition = metadata.partition();
            long offset = metadata.offset();
            System.out.println("Pushed successfully partition :"+partition +" offset : "+offset);
        } catch (JsonProcessingException ex) {
            System.out.println("exception");
        } catch (ExecutionException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
