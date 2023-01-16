package com.coursework.car.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendReservationExpirationReminder(String email, String model) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Car Sharing reservation expiration");
        message.setText("Hello,\nWe are sending this email to remind you that your reservation for the following " +
                "vehicle: " + model + " will expire in 1 day.\nVisit your reservations on 'http://localhost:3000/myReservations'.");
        emailSender.send(message);
    }
}
