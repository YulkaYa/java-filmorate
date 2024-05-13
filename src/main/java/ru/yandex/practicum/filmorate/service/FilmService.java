package ru.yandex.practicum.filmorate.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.model.Create;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Update;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

@Service
public class FilmService {
    Storage<Film> filmStorage;

    @Autowired
    public FilmService(Storage<Film> storage) {
        this.filmStorage = storage;
    }

    public Film create(Film film) {
        filmStorage.create(film);
        return film;
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }
}
