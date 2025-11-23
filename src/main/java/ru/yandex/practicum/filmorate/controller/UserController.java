package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.NotFoundExeption;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger userLog = LoggerFactory.getLogger(UserController.class);
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(getNextId());
        userLog.info("Пользователь создан");

        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        if (newUser.getEmail() == null && newUser.getName() == null && newUser.getLogin() == null) {
            return users.get(newUser.getId());
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            if (newUser.getEmail().equals(oldUser.getEmail())) {
                userLog.warn("Этот имейл уже используется");
                throw new ValidationException("Этот имейл уже используется");
            }
            if (users.values().stream().anyMatch(u -> u.getEmail().equals(newUser.getEmail()))) {
                userLog.warn("Этот имейл уже используется");
                throw new ValidationException("Этот имейл уже используется");
            }

            oldUser.setName(newUser.getName());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setBirthday(newUser.getBirthday());
            userLog.info("Сведения о пользователе обновлены");
            return oldUser;
        }
        throw new NotFoundExeption("Пользователь с id = " + newUser.getId() + " не найден");
    }

    private int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}