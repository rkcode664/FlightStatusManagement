package com.example.demo.service.notification;
import com.example.demo.dto.FlightDTO;
import com.example.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private BookingRepository bookingRepository;

    private static final String TOPIC = "flight-updates";

    public void sendNotification(FlightDTO flightDTO) {
        System.out.println("Sended Notification for flightDTO:" + flightDTO);
        List<String> userEmails = getUserEmailsForFlight(flightDTO.getFlightNumber());

        // Send email notifications to all users
        for (String email : userEmails) {
            sendEmail(email, flightDTO);
        }
        System.out.println("Email sent successfully");
    }

    private List<String> getUserEmailsForFlight(String flightNumber) {
        return bookingRepository.findUserEmailsByFlightNumber(flightNumber);
    }
    private void sendEmail(String to, FlightDTO flightDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Flight Status Update for " + flightDTO.getFlightNumber());
        message.setText("The flight " + flightDTO.getFlightNumber() + " status has been updated to: " + flightDTO.getStatus() +
                "\nGate: " + flightDTO.getGate() +
                "\nDeparture Time: " + flightDTO.getDepartureTime() +
                "\nArrival Time: " + flightDTO.getArrivalTime());
        emailSender.send(message);
    }
}
