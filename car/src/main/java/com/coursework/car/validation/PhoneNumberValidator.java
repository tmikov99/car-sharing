package com.coursework.car.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private static final String PHONE_PATTERN ="^(0)(\\d{2})[-. )]*(\\d{3})[-. ]*(\\d{4})$";
    private static final String REGIONAL_PHONE_PATTERN = "^(?:\\+?(359))[-. (]*(\\d{2})[-. )]*(\\d{3})[-. ]*(\\d{4})$";
    private static final Pattern PATTERN = Pattern.compile(PHONE_PATTERN);
    private static final Pattern PATTERN_REGIONAL = Pattern.compile(REGIONAL_PHONE_PATTERN);

    @Override
    public boolean isValid(final String phoneNumber, final ConstraintValidatorContext context) {
        return (validatePhoneNumber(phoneNumber));
    }

    private boolean validatePhoneNumber(final String phoneNumber) {
        Matcher matcher = PATTERN.matcher(phoneNumber);
        Matcher regionalMatcher = PATTERN_REGIONAL.matcher(phoneNumber);
        return matcher.matches() || regionalMatcher.matches();
    }
}