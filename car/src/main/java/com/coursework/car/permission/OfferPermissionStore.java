package com.coursework.car.permission;

import com.coursework.car.model.Offer;
import org.springframework.beans.PropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferPermissionStore extends JpaRepository<OfferPermission, Long> {
    void deleteByOffer(Offer offer);
}
