package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.List;


@Service
public class FilmService {
    FilmStorage filmStorage;
    InMemoryUserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }
    //добавление лайка
    public void likeFilm(int userId, int filmId) {
        User user = userStorage.findById(userId);
        if (user == null) {
            throw new NotFoundExeption("User not found.");
        }
        Film film = filmStorage.findByFilmId(filmId).orElseThrow(() -> new NotFoundExeption("Фильм не найден."));
        film.setLikes(film.getLikes() + 1);
        filmStorage.update(film);
    }
    //удаление лайка
    public void unlikeFilm(int userId, int filmId) {
        User user = userStorage.findById(userId);
        if (user == null) {
            throw new NotFoundExeption("User not found.");
        }
        Film film = filmStorage.findByFilmId(filmId).orElseThrow(() -> new NotFoundExeption("Фильм не найден."));
        film.setLikes(film.getLikes() - 1);
        filmStorage.update(film);
    }
    //вывод 10 наиболее популярных фильмов по количеству лайков
    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.findTopByLikes(count);
    }
}