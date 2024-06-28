package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Primary
@RequiredArgsConstructor
@Component
public class GenresFilmsDao {
    private static JdbcTemplate jdbcTemplate;
    private static GenreDao genreDao;

    @Autowired
    public GenresFilmsDao(GenreDao genreDao, JdbcTemplate jdbcTemplate) {
        this.genreDao = genreDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    public static List<Genre> getFilmGenre(Long filmId) {
        final String sqlQuery = "select * from genres as g join genres_films as gf on  g.genre_id = gf.genre_id where gf.film_id = ?";
        return jdbcTemplate.query(sqlQuery, GenreDao::makeGenre, filmId);
    }

    public static void addFilmGenre(Film film) {
        StringBuilder stringBuilder = new StringBuilder("MERGE INTO GENRES_FILMS (GENRE_ID, FILM_ID) KEY (genre_id, film_id) VALUES ");
        for (Genre genre : film.getGenres()) {
            try {
                genreDao.get(genre.getId());
            } catch (NotFoundException e) {
                throw new IllegalArgumentException("Ошибка добавления genre_id = " + genre.getId());
            }

            stringBuilder.append("(" + genre.getId() + ", " + film.getId() + "), ");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        final String sqlQuery = stringBuilder.toString();
        jdbcTemplate.update(sqlQuery);
    }
}
