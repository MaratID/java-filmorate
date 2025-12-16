package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundExeption;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static final Logger filmLog = LoggerFactory.getLogger(InMemoryFilmStorage.class);
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film create(Film film) {
        film.setId(getNextId());
        filmLog.info("Фильм создан");
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
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

    @Override
    public Film delete(Film film) {
        return null;
    }

    @Override
    public Optional<Film> findByFilmId(int filmId) {
        return Optional.ofNullable(films.get(filmId));
    }

    @Override
    public List<Film> findTopByLikes(Integer count) {
        if (count == null || count <= 0) {
            count = 10; // значение по умолчанию
        }
        return films.values().stream()
                .sorted(Comparator.comparingLong(Film::getLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
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