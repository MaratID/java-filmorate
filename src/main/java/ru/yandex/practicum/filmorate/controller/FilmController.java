package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage filmStorage;
    private final FilmService filmService;
    
    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.filmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }
    
    @GetMapping
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        return filmStorage.update(newFilm);
    }
    
    @PutMapping(value = "/{id}/like/{userId}")
    public void likeFilm(@RequestBody @PathVariable int id, @PathVariable int userId) {
        filmService.likeFilm(userId, id);
    }

    @DeleteMapping(value = "/{id}/like/{userId}") 
    public void deleteLike(@RequestBody @PathVariable int id, @PathVariable int userId) {
        filmService.unlikeFilm(userId, id);
    }
    
    @GetMapping("/popular")
    public Collection<Film> getMostLiked (@RequestParam int count) {
        return filmService.getPopularFilms(count);
    }
}