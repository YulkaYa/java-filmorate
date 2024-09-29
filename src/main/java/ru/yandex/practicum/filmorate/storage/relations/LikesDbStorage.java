package ru.yandex.practicum.filmorate.storage.relations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.base.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.base.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.relations.interfaces.LikesStorage;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
@Primary
public class LikesDbStorage implements LikesStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;

    @Override
    public void addRelation(final Long userId, final Long filmId) {
        this.userStorage.get(userId);
        this.filmStorage.get(filmId);
        final String sqlQuery = "insert into likes values (?, ?)";
        this.jdbcTemplate.update(sqlQuery, userId, filmId);
    }

    @Override
    public void deleteRelation(final Long userId, final Long filmId) {
        this.userStorage.get(userId);
        this.filmStorage.get(filmId);
        final String sqlQuery = "delete from likes where user_id = ? and film_id = ?";
        this.jdbcTemplate.update(sqlQuery, userId, filmId);
    }

    @Override
    public List<Film> topFilms(final Long count) {
        return this.jdbcTemplate.query("SELECT * FROM (SELECT film_id from likes GROUP BY film_id ORDER BY count(film_id) desc limit ?) AS l JOIN films AS f ON l.film_id = f.film_id ", filmStorage::makeObject, count);
    }
}
