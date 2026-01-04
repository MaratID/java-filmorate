package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.FilmDesSize;
import ru.yandex.practicum.filmorate.annotations.Filmyear;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {
    int id;
    @NotBlank(message = "Название не может быть пустым")
    String name;
    @FilmDesSize(size = 200)
    String description;
    @Filmyear
    LocalDate releaseDate;
    @Positive
    Integer duration;
    Long likes = 0L;

    public Film(int id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}