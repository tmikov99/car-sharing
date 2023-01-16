package com.coursework.car.service;

import com.coursework.car.exceptions.UserNotFoundException;
import com.coursework.car.model.LoginRequest;
import com.coursework.car.model.User;
import com.coursework.car.model.UserDto;
import com.coursework.car.repositories.UserRepository;
import com.coursework.car.request.UpdateUserRequest;
import com.coursework.car.security.JwtResponse;
import com.coursework.car.security.JwtTokenUtil;
import com.coursework.car.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder encoder;


    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toUserDto).collect(Collectors.toList());
    }

    @PreAuthorize("#id == authentication.principal.id")
    public UserDto getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("Cannot find user with id: " + id);
        }

        User user = optionalUser.get();
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber());
    }

    public UserDto getUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("Cannot find user with email: " + email);
        }
        User user = userOptional.get();
        return new UserDto(user.getEmail(), user.getPassword());
    }

    public void saveUser(UserDto user) throws EmailExistsException {
        if (emailExist(user.getEmail())) {
            throw new EmailExistsException("Email already in use: " + user.getEmail());
        }
        User newUser = user.fromDto();
        newUser.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(newUser);
    }

    @PreAuthorize("#id == authentication.principal.id")
    public void updateUser(Long id, UpdateUserRequest newUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("Cannot find user with id: " + id);
        }
        User user = userOptional.get();
        if (newUser.getFirstName() != null) {
            user.setFirstName(newUser.getFirstName());
        }
        if (newUser.getLastName() != null) {
            user.setLastName(newUser.getLastName());
        }
        userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                null);
    }

    UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber());
    }
}
