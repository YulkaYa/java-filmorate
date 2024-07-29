package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Create;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Update;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

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
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService service) {
        filmService = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getAll() {
        log.info("Получаем список всех фильмов");
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film get(@PathVariable Long id) {
        log.info("Получаем фильм с id={}", id);
        return filmService.get(id);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<Film> topFilms(@RequestParam(defaultValue = "10") @Positive(message = "Параметр count должен быть больше нуля;" ) int count) {
        log.info("Получаем список из {} самых популярных фильмов", count);
            return filmService.topFilms(count);
    }

    @PostMapping
    @Validated(Create.class)
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@Valid @RequestBody Film film) {
        log.info("Создаем новый фильм с id={}", film.getId());
        return filmService.create(film);
    }

    @PutMapping
    @Validated(Update.class)
    @ResponseStatus(HttpStatus.OK)
    public Film update(@Valid @RequestBody Film newFilm) {
        log.info("Обновляем фильм с id={}", newFilm.getId());
        return filmService.update(newFilm);
    }

    @PutMapping("/{filmId}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.info("Добавляем лайк к фильму с id={} от юзера с id={}", filmId, userId);
        filmService.putLike(userId, filmId);
    }

    @DeleteMapping("/{filmId}/like/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable Long filmId, @PathVariable Long id) {
        log.info("Удаляем лайк к фильму с id={} от юзера с id={}", filmId, id);
        filmService.deleteLike(id, filmId);
    }
}

