package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exeptions.NotFoundExeption;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ErrorResponse handle(final ValidationException e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(final NotFoundExeption e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.toString());
    }

    @ExceptionHandler
    public ErrorResponse handle(final RuntimeException e) {
        return new ErrorResponse("Произошла непредвиденная ошибка", HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }
}