package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

/**
 * Функции MpaController:
 * получение списка рейтингов фильмов
 * получение рейтинга по id
 */
@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {
    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService service) {
        this.mpaService = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Mpa> findAll() {
        log.info("Получаем список всех жанров");
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mpa get(@PathVariable Integer id) {
        log.info("Получаем жанр с id={}", id);
        return mpaService.get(id);
    }
}
