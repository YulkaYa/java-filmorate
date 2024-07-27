package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@RequiredArgsConstructor
@Component
public class LikesDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;


    public void addLike(Long userId, Long filmId) {
        userStorage.get(userId);
        filmStorage.get(filmId);
        final String sqlQuery = "insert into likes values (?, ?)";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }

    public void deleteLike(Long userId, Long filmId) {
        userStorage.get(userId);
        filmStorage.get(filmId);
        final String sqlQuery = "delete from likes where user_id = ? and film_id = ?";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }

    public List<Film> topFilms(int count) {
        return jdbcTemplate.query("SELECT * FROM (SELECT film_id from likes GROUP BY film_id ORDER BY count(film_id) desc limit ?) AS l JOIN films AS f ON l.film_id = f.film_id ", FilmDbStorage::makeFilm, count);
    }
}
