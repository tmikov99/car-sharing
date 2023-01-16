package com.coursework.car.model;

import com.coursework.car.enums.FuelType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String model;

    @Enumerated(EnumType.STRING)
    private FuelType fuel;

    private boolean isAutomatic;

    private int year;

    private String additionalInformation;

    @OneToOne(mappedBy = "car")
    private Offer offer;

    public Car(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public FuelType getFuel() {
        return fuel;
    }

    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }

    public boolean isAutomatic() {
        return isAutomatic;
    }

    public void setAutomatic(boolean automatic) {
        isAutomatic = automatic;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

}
