package com.coursework.car.repositories;

import com.coursework.car.model.Offer;
import com.coursework.car.model.OfferDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByUser_Id(Long id);
}
