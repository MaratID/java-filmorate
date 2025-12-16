package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * User.
 */
@Data
public class User {
    int id;
    @NotBlank(message = "Почта не может быть пустой")
    @Email
    String email;
    @Pattern(regexp = "^(?!\\s)(?!.*\\s).+$", message = "Логин не может быть пустым и содержать пробелы")
    String login;
    String name;
    @PastOrPresent
    LocalDate birthday;
    Set<Integer> friends = new HashSet<>();

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.login = login;
        this.birthday = birthday;
    }
}