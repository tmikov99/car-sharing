package com.coursework.car.repositories;

import com.coursework.car.model.Offer;
import com.coursework.car.model.Reservation;
import com.coursework.car.model.ReservationDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    void deleteByOffer(Offer offer);

    List<Reservation> findAllByOffer(Offer offer);

    List<Reservation> findByOffer_IdAndUser_Id(Long offerId, Long userId);

    List<Reservation> findByOffer_Id(Long offerId);

    List<Reservation> findByUser_Id(Long userId);

    List<Reservation> findAllByEndDateBetween(Date endDateStart, Date endDateEnd);
}
