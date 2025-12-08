package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmTest extends ModelTest<Film> {
    private Film film;

    @BeforeEach
    public void intFilm() {
        film = new Film(1, "name", "Description_1", LocalDate.now(), 10);
    }

    @Nested
    class FilmNameTest {
        @Test
        public void shouldViolateNameWhenNull()  {
            film.setName(null);
            Assertions.assertEquals(2, validateModel(film));
        }

        @Test
        public void shouldViolateNameWhenIsEmpty() {
            film.setName("");
            Assertions.assertEquals(2, validateModel(film));
        }

        @Test
        public void shouldViolateNameWhenIsBlank() {
            film.setName(" ");
            Assertions.assertEquals(2, validateModel(film));
        }

        @Test
        public void shouldViolateNameWhenIsValid() {
            film.setName("filmIsGood");
            Assertions.assertEquals(1, validateModel(film));
        }
    }
}