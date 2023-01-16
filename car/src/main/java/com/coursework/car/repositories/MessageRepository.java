package com.coursework.car.repositories;

import com.coursework.car.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReservationId(Long id);
    void deleteByReservationId(Long id);
}
