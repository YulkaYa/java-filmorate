package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
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
import java.util.Set;

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
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getAll() {
        log.info("Получаем список всех фильмов");
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film get(@PathVariable Long id) {
        log.info("Получаем фильм с id=" + id);
        return filmService.get(id);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<Film> topFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получаем список из " + count + " самых популярных фильмов");
            if (count <= 0) {
                throw new ValidationException("Параметр count должен быть больше нуля;");
            } else {
                return filmService.topFilms(count);
            }
        }

    @PostMapping
    @Validated(Create.class)
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@Valid @RequestBody Film film) {
        log.info("Создаем новый фильм с id=" + film.getId());
        return filmService.create(film);
    }

    @PutMapping
    @Validated(Update.class)
    @ResponseStatus(HttpStatus.OK)
    public Film update(@Valid @RequestBody Film newFilm) {
        log.info("Обновляем фильм с id=" + newFilm.getId());
        return filmService.update(newFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Добавляем лайк к фильму с id=" + id + " от юзера с id=" + userId);
        filmService.putLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Удаляем лайк к фильму с id=" + id + " от юзера с id=" + userId);
        filmService.deleteLike(id, userId);
    }
}

