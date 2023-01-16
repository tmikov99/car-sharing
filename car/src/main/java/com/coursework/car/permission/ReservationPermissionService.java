package com.coursework.car.permission;

import com.coursework.car.model.Offer;
import com.coursework.car.model.Reservation;
import com.coursework.car.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationPermissionService {

    @Autowired
    ReservationPermissionStore reservationPermissionStore;

    public void addToStore(User user, Reservation reservation, String level) {
        ReservationPermission reservationPermission = new ReservationPermission();
        reservationPermission.setReservation(reservation);
        reservationPermission.setUser(user);
        reservationPermission.setLevel(level);
        reservationPermissionStore.save(reservationPermission);
    }

    public void removeFromStoreByReservation(Reservation reservation) {
        reservationPermissionStore.deleteByReservation(reservation);
    }
}
