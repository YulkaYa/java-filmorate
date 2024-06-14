package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Primary
@RequiredArgsConstructor
@Component
public class FilmDbStorage implements FilmStorage {
    @Override
    public Film create(Film data) {
        return null;
    }

    @Override
    public Film update(Film data) {
        return null;
    }

    @Override
    public Film get(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Film> getAll() {
        return null;
    }
}
