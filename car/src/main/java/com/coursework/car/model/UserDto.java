package com.coursework.car.model;

import com.coursework.car.validation.PasswordMatches;
import com.coursework.car.validation.ValidEmail;
import com.coursework.car.validation.ValidPassword;
import com.coursework.car.validation.ValidPhoneNumber;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@PasswordMatches
public class UserDto {
    private Long id;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @ValidPhoneNumber
    @NotNull
    @NotEmpty
    private String phoneNumber;

    private List<OfferDto> offers;

    private List<ReservationDto> reservations;

    public UserDto() {}

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDto(Long id, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserDto(String firstName, String lastName, String password, String matchingPassword, String email, String phoneNumber, List<OfferDto> offers, List<ReservationDto> reservations) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.offers = offers;
        this.reservations = reservations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<OfferDto> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferDto> offers) {
        this.offers = offers;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }

    public User fromDto() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        if (offers != null) {
            user.setOffers(offers.stream().map(OfferDto::fromDto).collect(Collectors.toList()));
        }
        if (reservations != null) {
            user.setReservations(reservations.stream().map(ReservationDto::fromDto).collect(Collectors.toList()));
        }
        return user;
    }
}
