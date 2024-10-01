package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.base.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.relations.interfaces.LikesStorage;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikesStorage likesStorage;

    public Film create(final Film film) {
        this.filmStorage.create(film);
        return film;
    }

    public Film get(final Long id) {
        return this.filmStorage.get(id);
    }

    public Film update(final Film film) {
        return this.filmStorage.update(film);
    }

    public List<Film> getAll() {
        return this.filmStorage.getAll();
    }

    public void putLike(final Long userId, final Long filmId) {
        this.likesStorage.addRelation(userId, filmId);
    }

    public void deleteLike(final Long userId, final Long filmId) {
        this.likesStorage.deleteRelation(userId, filmId);
    }

    public List<Film> topFilms(final Long count) {
        return this.likesStorage.topFilms(count);
    }
}
