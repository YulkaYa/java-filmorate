package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService service) {
        this.genreService = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> findAll() {
        log.info("Получаем список всех жанров");
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre get(@PathVariable Integer id) {
        log.info("Получаем жанр с id={}", id);
        return genreService.get(id);
    }
}
