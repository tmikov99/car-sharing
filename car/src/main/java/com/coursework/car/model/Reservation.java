package com.coursework.car.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Date startDate;

    private Date endDate;

    private double totalCost;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean approved;

    public Reservation() {}

    public Reservation(Date startDate, Date endDate, double totalCost, Offer offer, User user, boolean approved) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.offer = offer;
        this.user = user;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Double.compare(that.totalCost, totalCost) == 0 && approved == that.approved && Objects.equals(id, that.id) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(offer, that.offer) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, totalCost, offer, user, approved);
    }
}
