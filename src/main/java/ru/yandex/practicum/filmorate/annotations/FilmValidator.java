package ru.yandex.practicum.filmorate.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FilmValidator implements ConstraintValidator<Filmyear, LocalDate> {
    private LocalDate minimumDate;

    @Override
    public void initialize(Filmyear constraintAnnotation) {
        minimumDate = LocalDate.parse(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value == null || !value.isBefore(minimumDate);
    }
}