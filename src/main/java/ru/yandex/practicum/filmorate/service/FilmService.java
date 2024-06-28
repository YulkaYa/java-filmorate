package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikesDao;

import java.util.List;

@Service
public class FilmService {
    private FilmStorage filmStorage;
    private LikesDao likesDao;

    @Autowired
    public FilmService(FilmStorage filmStorage, LikesDao likesDao) {
        this.filmStorage = filmStorage;
        this.likesDao = likesDao;
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

    public void putLike(Long userId, Long filmId) {
        likesDao.addLike(userId, filmId);
    }

    public void deleteLike(Long userId, Long filmId) {
        likesDao.deleteLike(userId, filmId);
    }

    public List<Film> topFilms(int count) {
        return likesDao.topFilms(count);
    }
}
