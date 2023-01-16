package com.coursework.car.model;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class OfferDto {
    private Long id;

    @NotNull(message = "Please enter a pick up town")
    @NotEmpty(message = "Pick up town can not be empty")
    private String town;

    @Min(value = 1, message = "Rent period must be greater than 0")
    private Integer minDays;

    @Min(value = 1, message = "Rent period must be greater than 0")
    private Integer maxDays;

    @Min(value = 1, message = "Daily cost must be greater than 0")
    private Double dayCost;

    @Valid
    private CarDto car;

    @NotNull(message = "Missing creator user")
    private UserDto user;

    private Set<String> imageUrls;

    public OfferDto() {}

    public OfferDto(Long id) {
        this.id = id;
    }

    public OfferDto(Long id, String town, int minDays, int maxDays, double dayCost, CarDto car, UserDto user, Set<String> imageUrls) {
        this.id = id;
        this.town = town;
        this.minDays = minDays;
        this.maxDays = maxDays;
        this.dayCost = dayCost;
        this.car = car;
        this.user = user;
        this.imageUrls = imageUrls;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public int getMinDays() {
        return minDays;
    }

    public void setMinDays(int minDays) {
        this.minDays = minDays;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    public double getDayCost() {
        return dayCost;
    }

    public void setDayCost(double dayCost) {
        this.dayCost = dayCost;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Set<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(Set<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Offer fromDto() {
        Offer offer = new Offer();
        offer.setId(id);
        offer.setTown(town);
        if (dayCost != null) {
            offer.setDayCost(dayCost);
        }
        if (maxDays != null) {
            offer.setMaxDays(maxDays);
        }
        if (minDays != null) {
            offer.setMinDays(minDays);
        }
        if (user != null) {
            offer.setUser(user.fromDto());
        }
        if (car != null) {
            offer.setCar(car.fromDto());
        }
        if (imageUrls != null) {
            offer.setImageUrls(imageUrls);
        }

        return offer;
    }
}
