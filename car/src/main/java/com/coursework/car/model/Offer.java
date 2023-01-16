package com.coursework.car.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String town;

    private int minDays;

    private int maxDays;

    private double dayCost;

    @ElementCollection
    @Column(length = 10000)
    private Set<String> imageUrls;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Offer() {}

    public Offer(String town, Car car, User user) {
        this.town = town;
        this.car = car;
        this.user = user;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<String> getImageUrls() {
        if (imageUrls == null) {
            return new HashSet<>();
        }
        return imageUrls;
    }

    public void setImageUrls(Set<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return minDays == offer.minDays && maxDays == offer.maxDays && Double.compare(offer.dayCost, dayCost) == 0 && Objects.equals(id, offer.id) && Objects.equals(town, offer.town) && Objects.equals(car, offer.car) && Objects.equals(user, offer.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, town, minDays, maxDays, dayCost, car, user);
    }
}
