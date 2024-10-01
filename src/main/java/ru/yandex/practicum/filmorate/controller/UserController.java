package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Create;
import ru.yandex.practicum.filmorate.model.Update;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

/**
 * Функции UserController:
 * создание пользователя
 * обновление пользователя
 * получение списка всех пользователей
 * добавление пользователей в друзья
 * удаление пользователей из друзей
 * получение списка общих друзей
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService service) {
        this.userService = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        UserController.log.info("Получаем список всех пользователей");
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User get(@PathVariable final Long id) {
        UserController.log.info("Получаем пользователя с id={}", id);
        return this.userService.get(id);
    }

    @PostMapping
    @Validated(Create.class)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody final User user) {
        UserController.log.info("Создаем нового пользователя с id={}", user.getId());
        return this.userService.create(user);
    }

    @PutMapping
    @Validated(Update.class)
    @ResponseStatus(HttpStatus.OK)
    public User update(@Valid @RequestBody final User newUser) {
        UserController.log.info("Обновляем пользователя с id={}", newUser.getId());
        return this.userService.update(newUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFriend(@PathVariable final Long id, @PathVariable final Long friendId) {
        UserController.log.info("Добавляем пользователю с id={} друга с id={}", id, friendId);
        this.userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFromFriends(@PathVariable final Long id, @PathVariable final Long friendId) {
        UserController.log.info("Удаляем из друзей пользователя с id={} друга с id={}", id, friendId);
        this.userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getFriends(@PathVariable final Long id) {
        UserController.log.info("Получаем друзей пользователя с id={}", id);
        return this.userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getCommonFriends(@PathVariable final Long id, @PathVariable final Long otherId) {
        UserController.log.info("Получаем общих друзей пользователей с id={} и с id={}", id, otherId);
        return this.userService.getCommonFriends(id, otherId);
    }
}