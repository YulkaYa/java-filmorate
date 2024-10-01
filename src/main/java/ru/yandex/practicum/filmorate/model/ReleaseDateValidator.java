package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ReleaseDateValidator implements
        ConstraintValidator<ReleaseDateConstraint, LocalDate> {
    @Override
    public void initialize(final ReleaseDateConstraint releaseDateAnnotation) {
    }

    @Override
    public boolean isValid(final LocalDate releaseDate,
                           final ConstraintValidatorContext cxt) {
        if (null == releaseDate) {
            return true;
        }
        if (!(releaseDate instanceof LocalDate)) {
            throw new IllegalArgumentException(
                    "Illegal method signature, expected two parameters of type LocalDate.");
        }
        return releaseDate.isAfter(LocalDate.of(1895, 12, 27));
    }
}