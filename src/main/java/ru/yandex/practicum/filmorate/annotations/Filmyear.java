package ru.yandex.practicum.filmorate.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Past;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FilmValidator.class)
@Past
public @interface Filmyear {
    String message() default "Date must not be before {value}";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
    String value() default "1895-12-28";
}