package com.coursework.car.repositories;

import com.coursework.car.model.Car;
import com.coursework.car.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
