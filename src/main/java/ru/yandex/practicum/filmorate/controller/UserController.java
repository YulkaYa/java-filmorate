package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Create;
import ru.yandex.practicum.filmorate.model.Update;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

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
    UserService userService;
    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }
    @GetMapping
    public List<User> findAll() {
        return userService.getAll();
    }

    @PostMapping
    @Validated(Create.class)
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    @Validated(Update.class)
    public User update(@Valid @RequestBody User newUser) {
        return userService.update(newUser);
    }
}