package com.coursework.car.controller;

import com.coursework.car.model.Reservation;
import com.coursework.car.model.ReservationDto;
import com.coursework.car.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<ReservationDto> getReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/offer/{offerId}/user/{userId}")
    public List<ReservationDto> getReservationsByOfferAndUser(@PathVariable Long offerId, @PathVariable Long userId) {
        return reservationService.getReservationByUserAndOffer(offerId, userId);
    }

    @GetMapping("/offer/{offerId}")
    public List<ReservationDto> getReservationsByOffer(@PathVariable Long offerId) {
        return reservationService.getReservationByOffer(offerId);
    }

    @GetMapping("/user/{userId}")
    public List<ReservationDto> getReservationsByUser(@PathVariable Long userId) {
        return reservationService.getReservationByUser(userId);
    }

    @GetMapping("/{id}")
    public ReservationDto getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping
    public void saveReservation(@Valid @RequestBody ReservationDto newReservation) {
        reservationService.saveReservation(newReservation);
    }

    @PutMapping("/{id}")
    public void approveReservation(@PathVariable Long id, @RequestParam boolean isApproved) {
        reservationService.approveReservation(id, isApproved);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

    @DeleteMapping("/byOffer/{id}")
    public void deleteReservationsByOffer(@PathVariable Long id) {
        reservationService.deleteReservationsByOffer(id);
    }
}
