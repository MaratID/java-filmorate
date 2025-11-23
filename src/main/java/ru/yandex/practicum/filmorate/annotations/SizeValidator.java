package ru.yandex.practicum.filmorate.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class SizeValidator implements ConstraintValidator<FilmDesSize, String> {
    private int length;

    @Override
    public void initialize(FilmDesSize constraintAnnotation) {
        length = constraintAnnotation.toString().length();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        return s.length() <= length;
    }
    /* f */
}