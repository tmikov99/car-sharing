package com.coursework.car.controller;

import com.coursework.car.model.LoginRequest;
import com.coursework.car.model.Offer;
import com.coursework.car.model.User;
import com.coursework.car.model.UserDto;
import com.coursework.car.repositories.OfferRepository;
import com.coursework.car.repositories.UserRepository;
import com.coursework.car.request.UpdateUserRequest;
import com.coursework.car.security.JwtResponse;
import com.coursework.car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(consumes = {APPLICATION_JSON_VALUE})
    public void saveUser(@Valid @RequestBody UserDto newUser) {
        userService.saveUser(newUser);
    }

    @PostMapping("/logon")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = userService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest newUser) {
        userService.updateUser(id, newUser);
    }
}
