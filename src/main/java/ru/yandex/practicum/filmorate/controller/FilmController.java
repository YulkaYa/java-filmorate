package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Create;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Update;

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
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    @Validated(Create.class)
    public Film create(@Valid @RequestBody Film film) {
        // формируем дополнительные данные
        film.setId(getNextId());
        // сохраняем новый фильм в памяти приложения
        films.put(film.getId(), film);
        log.info("Новый фильм с id = " + film.getId() + " создан");
        return film;
    }

    // вспомогательный метод для генерации идентификатора нового пользователя
    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @PutMapping
    @Validated(Update.class)
    public Film update(@Valid @RequestBody Film newFilm) throws IllegalAccessException {
        // проверяем необходимые условия
        Long newFilmId = newFilm.getId();
        if (films.containsKey(newFilmId)) {
            Film oldFilm = films.get(newFilmId);
            Film film = Film.buildNewFilm(oldFilm, newFilm);
            films.put(newFilmId, film);
            log.info("Фильм с id = " + newFilm.getId() + " обновлен");
            return film;
        }
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }
}

