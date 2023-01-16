package com.coursework.car.permission;

import com.coursework.car.model.Offer;
import com.coursework.car.model.Reservation;
import com.coursework.car.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private OfferPermissionStore offerPermissionStore;

    @Autowired
    private ReservationPermissionStore reservationPermissionStore;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        User principal = (User) authentication.getPrincipal();

        if (targetDomainObject instanceof Offer) {
            return hasOfferPermission(principal, (Offer) targetDomainObject, permission);
        }

        if (targetDomainObject instanceof Reservation) {
            return hasReservationPermission(principal, (Reservation) targetDomainObject, permission);
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object permission) {
        User principal = (User) authentication.getPrincipal();

        if (targetType.equals(Offer.class.getName())) {
            return hasOfferPermission(principal, targetId, permission);
        }

        if (targetType.equals(Reservation.class.getName())) {
            return hasReservationPermission(principal, targetId, permission);
        }

        return false;
    }

    private boolean hasOfferPermission(User principal, Offer offer, Object permission) {
        return offerPermissionStore.findAll().stream().anyMatch(p -> p.getUser().equals(principal)
                && p.getOffer().equals(offer)
                && p.getLevel().equals(permission));
    }

    private boolean hasOfferPermission(User principal, Serializable targetId, Object permission) {
        return offerPermissionStore.findAll().stream().anyMatch(p -> p.getUser().equals(principal)
                && p.getOffer().getId().equals(targetId)
                && p.getLevel().equals(permission));
    }

    private boolean hasReservationPermission(User principal, Reservation reservation, Object permission) {
        return reservationPermissionStore.findAll().stream().anyMatch(p -> p.getUser().getId().equals(principal.getId())
                && p.getReservation().equals(reservation)
                && p.getLevel().equals(permission));
    }

    private boolean hasReservationPermission(User principal, Serializable targetId, Object permission) {
        return reservationPermissionStore.findAll().stream().anyMatch(p -> p.getUser().getId().equals(principal.getId())
                && p.getReservation().getId().equals(targetId)
                && p.getLevel().equals(permission));
    }
}
