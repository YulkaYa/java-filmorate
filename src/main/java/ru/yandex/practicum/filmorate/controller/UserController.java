package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Create;
import ru.yandex.practicum.filmorate.model.Update;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Функции UserController:
 * создание пользователя;
 * обновление пользователя;
 * получение списка всех пользователей
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    @Validated(Create.class)
    public User create(@Valid @RequestBody User user) {
        // проверяем выполнение необходимых условий
        if (users.values().stream().anyMatch(user1 -> user1.getEmail().equals(user.getEmail()))) {

            throw new DuplicatedDataException("Имейл " + user.getEmail() + " уже используется");
        }
        if (users.values().stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin()))) {
            throw new DuplicatedDataException("Логин " + user.getLogin() + " уже используется");
        }
        // формируем дополнительные данные
        user.setId(getNextId());
        // сохраняем нового пользователя в памяти приложения
        users.put(user.getId(), user);
        log.info("Новый пользователь с id = " + user.getId() + " создан");
        return user;
    }

    // вспомогательный метод для генерации идентификатора нового пользователя
    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @PutMapping
    @Validated(Update.class)
    public User update(@Valid @RequestBody User newUser) throws IllegalAccessException {
        // проверяем необходимые условия
        Long newUserId = newUser.getId();
        if (users.containsKey(newUserId)) {
            User oldUser = users.get(newUserId);
            if (users.values().stream().anyMatch(user1 -> user1.getEmail().equals(newUser.getEmail()) && !user1.getId().equals(newUserId))) {
                throw new DuplicatedDataException("Имейл " + newUser.getEmail() + " уже используется");
            }
            if (users.values().stream().anyMatch(user1 -> user1.getLogin().equals(newUser.getLogin()) && !user1.getId().equals(newUserId))) {
                throw new DuplicatedDataException("Логин " + newUser.getLogin() + " уже используется");
            }
            User user = User.buildNewUser(oldUser, newUser);
            users.put(newUserId, user);
            log.info("Пользователь с id = " + newUserId + " обновлен");
            return user;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }
}