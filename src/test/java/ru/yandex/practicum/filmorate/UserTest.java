package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserTest extends ModelTest<User> {
    private User user;

    @BeforeEach
    public void intFilm() {
        user = new User(1, "email@mail.com", "login", "User_1", LocalDate.of(2002,
                10,10));
    }

    @Nested
    class UserNameTest {
        @Test
        public void shouldViolateNameWhenNull()  {
            user.setName(null);
            Assertions.assertEquals(0, validateModel(user));
        }

        @Test
        public void shouldViolateNameWhenIsEmpty() {
            user.setName("");
            Assertions.assertEquals(0, validateModel(user));
        }

        @Test
        public void shouldViolateNameWhenIsBlank() {
            user.setName(" ");
            Assertions.assertEquals(0, validateModel(user));
        }

        @Test
        public void shouldViolateNameWhenIsValid() {
            user.setName("User_2");
            Assertions.assertEquals(0, validateModel(user));
        }
    }
}