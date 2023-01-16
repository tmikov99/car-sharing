package com.coursework.car.controller;

import com.coursework.car.model.Message;
import com.coursework.car.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping
    public List<Message> getMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/reservation/{id}")
    public List<Message> getMessagesByReservationId(@PathVariable Long id) {
        return messageService.getMessagesByReservationId(id);
    }

    @DeleteMapping("/reservation/{id}")
    public void deleteMessagesByReservationId(@PathVariable Long id) {
        messageService.deleteMessagesByReservationId(id);
    }

    @PostMapping
    public void sendMessage(@Valid @RequestBody Message message) {
        messageService.sendMessage(message);
    }
}
