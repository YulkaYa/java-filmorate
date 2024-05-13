package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Create;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.StorageData;
import ru.yandex.practicum.filmorate.model.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage extends StorageData implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long idGenerator = 0L;

    @Override
    @Validated(Create.class)
    @Valid
    public Film create(Film film) {
        // формируем дополнительные данные
        film.setId(++idGenerator);
        // сохраняем новый фильм в памяти приложения
        films.put(film.getId(), film);
        log.info("Новый фильм с id = " + film.getId() + " создан");
        return film;
    }

    @Override
    @Validated(Update.class)
    @Valid
    public Film update(Film newFilm) {
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

    @Override
    public Film get(long id) {
        final Film film = films.get(id);
        if (film == null) {
            throw new NotFoundException("film with id=" + id + " does not exist");
        }
        return film;
    }

    @Override
    public void delete(long id) {
        films.remove(id);
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }
}
