package com.coursework.car.validation;

import com.coursework.car.model.UserDto;
import com.google.common.base.Joiner;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDto user = (UserDto) obj;
        if (user.getPassword().equals(user.getMatchingPassword())) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords must match").addPropertyNode( "password" ).addConstraintViolation();
            return false;
        }
    }

}