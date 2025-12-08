package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exeptions.NotFoundExeption;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger filmLog = LoggerFactory.getLogger(FilmController.class);
    private final Map<Integer, Film> films = new HashMap<>();


    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        film.setId(getNextId());
        filmLog.info("Фильм создан");
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        if (newFilm.getReleaseDate() == null && newFilm.getName() == null && newFilm.getDescription() == null) {
            return films.get(newFilm.getId());
        }
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            if (newFilm.getName().equals(oldFilm.getName())) {
                filmLog.warn("Этот фильм уже используется");
                throw new ValidationException("Этот имейл уже используется");
            }
            if (films.values().stream().anyMatch(u -> u.getName().equals(newFilm.getName()))) {
                filmLog.warn("Этот имейл уже используется");
                throw new ValidationException("Этот имейл уже используется");
            }

            oldFilm.setName(newFilm.getName());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setDuration(newFilm.getDuration());
            filmLog.info("Сведения о фильме обновлены");
            return oldFilm;
        }
        throw new NotFoundExeption("Фильм с id = " + newFilm.getId() + " не найден");
    }

    private int getNextId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}