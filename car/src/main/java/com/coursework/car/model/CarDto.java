package com.coursework.car.model;

import com.coursework.car.enums.FuelType;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarDto {
    private Long id;

    @NotNull
    @NotEmpty(message = "Car model can not be empty")
    private String model;

    @NotNull
    private FuelType fuel;

    @NotNull
    private Boolean isAutomatic;

    @Min(value = 1, message = "Invalid year")
    private Integer year;

    private String additionalInformation;

    private OfferDto offer;

    public CarDto() {}

    public CarDto(Long id, String model, FuelType fuel, boolean isAutomatic, int year, String additionalInformation) {
        this.id = id;
        this.model = model;
        this.fuel = fuel;
        this.isAutomatic = isAutomatic;
        this.year = year;
        this.additionalInformation = additionalInformation;
    }

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

    public OfferDto getOffer() {
        return offer;
    }

    public void setOffer(OfferDto offer) {
        this.offer = offer;
    }

    public Car fromDto() {
        Car car = new Car();
        car.setId(id);
        car.setModel(model);
        car.setAutomatic(isAutomatic);
        car.setFuel(fuel);
        car.setYear(year);
        if (offer != null) {
            car.setOffer(offer.fromDto());
        }
        return car;
    }
}
