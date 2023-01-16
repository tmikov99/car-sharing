package com.coursework.car.permission;

import com.coursework.car.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationPermissionStore extends JpaRepository<ReservationPermission, Long> {
    void deleteByReservation(Reservation reservation);
}
