package com.coursework.car.service;

import com.coursework.car.model.Message;
import com.coursework.car.model.OfferDto;
import com.coursework.car.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return new ArrayList<>(messageRepository.findAll());
    }

    public void sendMessage(Message message) {
        messageRepository.save(message);
    }

    @PreAuthorize("hasPermission(#id, 'com.coursework.car.model.Reservation', 'WRITE') || hasPermission(#id, 'com.coursework.car.model.Reservation', 'APPROVE')")
    public List<Message> getMessagesByReservationId(Long id) {
        return new ArrayList<>(messageRepository.findAllByReservationId(id));
    }

    @Transactional
    @PreAuthorize("hasPermission(#id, 'com.coursework.car.model.Reservation', 'WRITE') || hasPermission(#id, 'com.coursework.car.model.Reservation', 'APPROVE')")
    public void deleteMessagesByReservationId(Long id) {
        messageRepository.deleteByReservationId(id);
    }
}
