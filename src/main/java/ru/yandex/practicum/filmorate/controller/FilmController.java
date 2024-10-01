package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
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
    public FilmController(final FilmService service) {
        this.filmService = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getAll() {
        FilmController.log.info("Получаем список всех фильмов");
        return this.filmService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film get(@PathVariable final Long id) {
        FilmController.log.info("Получаем фильм с id={}", id);
        return this.filmService.get(id);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<Film> topFilms(@RequestParam(defaultValue = "10") @Positive(message = "Параметр count должен быть больше нуля;") final Long count) {
        FilmController.log.info("Получаем список из {} самых популярных фильмов", count);
        return this.filmService.topFilms(count);
    }

    @PostMapping
    @Validated(Create.class)
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@Valid @RequestBody final Film film) {
        FilmController.log.info("Создаем новый фильм с id={}", film.getId());
        return this.filmService.create(film);
    }

    @PutMapping
    @Validated(Update.class)
    @ResponseStatus(HttpStatus.OK)
    public Film update(@Valid @RequestBody final Film newFilm) {
        FilmController.log.info("Обновляем фильм с id={}", newFilm.getId());
        return this.filmService.update(newFilm);
    }

    @PutMapping("/{filmId}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putLike(@PathVariable final Long filmId, @PathVariable final Long userId) {
        FilmController.log.info("Добавляем лайк к фильму с id={} от юзера с id={}", filmId, userId);
        this.filmService.putLike(userId, filmId);
    }

    @DeleteMapping("/{filmId}/like/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable final Long filmId, @PathVariable final Long id) {
        FilmController.log.info("Удаляем лайк к фильму с id={} от юзера с id={}", filmId, id);
        this.filmService.deleteLike(id, filmId);
    }
}

