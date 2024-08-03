package com.example.demo.listner;
import com.example.demo.dto.FlightDTO;
import com.example.demo.service.notification.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class FlightStatusListener implements AcknowledgingMessageListener<String,String> {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    @KafkaListener(topics = "flight-updates", groupId = "flight-status-group")
    public void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        String key = StringUtils.EMPTY;
        String message = StringUtils.EMPTY;
        long offset = -1L;
        int partition = -1;

        try {
            key = consumerRecord.key();
            partition = consumerRecord.partition();
            offset = consumerRecord.offset();
            message = consumerRecord.value();
            System.out.println("Flight Status Stamping event received with key {} partition {} offset {} Message {}"+ key + partition + offset + message);
            FlightDTO flightStatus = objectMapper.readValue(message, FlightDTO.class);
            notificationService.sendNotification(flightStatus);
            System.out.println("Processing event completed key {}, partition {}, offset {}, eventType {} "+ key+ partition+ offset);
        } catch (Exception ex) {
            System.out.println("Exception while processing");
//            auditService.auditFailedMessages(message, ex.getMessage(), ExceptionUtils.getStackTrace(ex));
        }

        try {
            acknowledgment.acknowledge();
            System.out.println("Acknowledged Proforma stamping event message offset {} partition {} key {} "+ offset+ partition+ key);
        } catch (Exception ex) {
            System.out.println("Error occurred");
        }
    }
    }
