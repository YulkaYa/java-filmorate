package ru.yandex.practicum.filmorate.storage.relations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.base.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.relations.interfaces.GenresFilmsStorage;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
@Primary
public class GenresFilmsDbStorage implements GenresFilmsStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;

    @Override
    public List<Genre> getRelations(final Long filmId) {
        final String sqlQuery = "select * from genres as g join genres_films as gf on  g.genre_id = gf.genre_id where gf.film_id = ?";
        return this.jdbcTemplate.query(sqlQuery, this.genreDbStorage::makeObject, filmId);
    }

    @Override
    public void addFilmGenre(final Film film) {
        final StringBuilder stringBuilder = new StringBuilder("MERGE INTO GENRES_FILMS (GENRE_ID, FILM_ID) KEY (genre_id, film_id) VALUES ");
        for (final Genre genre : film.getGenres()) {
            try {
                this.genreDbStorage.get(genre.getId());
            } catch (final NotFoundException e) {
                throw new IllegalArgumentException("Ошибка добавления genre_id = " + genre.getId());
            }

            stringBuilder.append("(" + genre.getId() + ", " + film.getId() + "), ");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        String sqlQuery = stringBuilder.toString();
        this.jdbcTemplate.update(sqlQuery);
    }
}
