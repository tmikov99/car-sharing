package com.coursework.car.model;

import com.coursework.car.validation.StartBeforeEndDate;

import javax.validation.constraints.NotNull;
import java.util.Date;

@StartBeforeEndDate
public class ReservationDto {
    private Long id;

    private OfferDto offer;

    @NotNull
    private UserDto user;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private Double totalCost;

    private boolean approved;

    public ReservationDto(Long id, OfferDto offer, UserDto user, Date startDate, Date endDate, double totalCost, boolean approved) {
        this.id = id;
        this.offer = offer;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OfferDto getOffer() {
        return offer;
    }

    public void setOffer(OfferDto offer) {
        this.offer = offer;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Reservation fromDto() {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        if (user != null) {
            reservation.setUser(user.fromDto());
        }
        if (offer != null) {
            reservation.setOffer(offer.fromDto());
        }
        if (startDate != null) {
            reservation.setStartDate(startDate);
        }
        if (endDate != null) {
            reservation.setEndDate(endDate);
        }
        if (totalCost != null) {
            reservation.setTotalCost(totalCost);
        }
        reservation.setApproved(approved);
        return reservation;
    }
}
