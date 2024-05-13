package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Create;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Update;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Функции FilmController:
 * добавление фильма;
 * обновление фильма;
 * получение всех фильмов.
 */
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    FilmService filmService;
    @Autowired
    public FilmController(FilmService service) {
        this.filmService = service;
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.getAll();
    }

    @PostMapping
    @Validated(Create.class)
    public Film create(@Valid @RequestBody Film film) {
        log.info("Создаем новый фильм");
        return filmService.create(film);
    }

    @PutMapping
    @Validated(Update.class)
    public Film update(@Valid @RequestBody Film newFilm) {
        return filmService.update(newFilm);
    }
}

