package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    Storage<Film> filmStorage;
    Storage<User> userStorage;

    @Autowired
    public FilmService(Storage<Film> storage, Storage<User> userStorage) {
        this.filmStorage = storage;
        this.userStorage = userStorage;
    }

    public Film create(Film film) {
        filmStorage.create(film);
        return film;
    }

    public Film get(Long id) {
        return filmStorage.get(id);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public void putLike(Long id, Long userId) {
        userStorage.get(userId);
        filmStorage.get(id).getLikes().add(userId);
    }

    public void deleteLike(Long id, Long userId) {
        userStorage.get(userId);
        filmStorage.get(id).getLikes().remove(userId);
    }

    public List<Film> topFilms(int count) {
        List<Film> films = filmStorage.getAll();
        if (count > films.size()) {
            count = films.size();
        }
        return films.stream().sorted((s1, s2) -> {
                    return s2.getLikes().size() - s1.getLikes().size();
                })
                .collect(Collectors.toList()).subList(0, count);
    }
}
