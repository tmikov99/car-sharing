package com.coursework.car.validation;

import com.coursework.car.model.ReservationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class StartEndDateValidator implements ConstraintValidator<StartBeforeEndDate, Object> {
    @Override
    public void initialize(final StartBeforeEndDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final ReservationDto reservation = (ReservationDto) obj;
        Date startDate = reservation.getStartDate();
        Date endDate = reservation.getEndDate();

        if (isStartBeforeToday(startDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Start date must not be in the past")
                    .addPropertyNode( "startDate" )
                    .addConstraintViolation();
            return false;
        }

        if (startDate.equals(endDate) || startDate.before(endDate)) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Start date must be before end date")
                    .addPropertyNode( "startDate" )
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate("End date must be after start date")
                    .addPropertyNode( "endDate" )
                    .addConstraintViolation();
            return false;
        }
    }

    private boolean isStartBeforeToday(Date startDate) {
        Date now = new Date();
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        return !(startDate.after(now));
    }
}
