package ru.yandex.practicum.filmorate.storage.base;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.base.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.relations.GenresFilmsDbStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Primary
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class FilmDbStorage implements FilmStorage {
    private final MpaDbStorage mpaDbStorage;
    private final JdbcTemplate jdbcTemplate;
    private final GenresFilmsDbStorage genresFilmsDbStorage;

    public Film makeObject(final ResultSet rs, final int rowNum) throws SQLException {
        final Film film = Film.builder()
                .id(rs.getLong("film_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("releaseDate").toLocalDate())
                .duration(rs.getLong("duration"))
                .genres(new ArrayList<>()).build();
        if (0 != rs.getInt("mpa_id")) {
            film.setMpa(this.mpaDbStorage.get(rs.getLong("mpa_id")));
        }
        film.setGenres(this.genresFilmsDbStorage.getRelations(film.getId()));
        return film;
    }

    @Override
    public Film create(final Film data) {
        final String sqlQuery = """
                insert into films (
                     name, description, releaseDate, duration, mpa_id
                ) values (?, ?, ?, ?, ?)
                """;

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            final Mpa mpa = this.mpaDbStorage.get(data.getMpa().getId());
            data.setMpa(mpa);
        } catch (final NotFoundException e) {
            throw new IllegalArgumentException("Ошибка добавления mpa_id = " + data.getMpa().getId());
        }
        this.jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, data.getName());
            stmt.setString(2, data.getDescription());
            stmt.setDate(3, Date.valueOf(data.getReleaseDate()));
            stmt.setLong(4, data.getDuration());
            stmt.setLong(5, data.getMpa().getId());
            return stmt;
        }, keyHolder);
        try {
            data.setId(keyHolder.getKey().longValue());
        } catch (final NullPointerException e) {
            throw new RuntimeException("Ошибка при добавлении id");
        }
        if (!data.getGenres().isEmpty()) {
            this.genresFilmsDbStorage.addFilmGenre(data);
        }
        return data;
    }

    @Override
    public Film update(final Film data) {
        this.get(data.getId());
        final String sqlQuery = "update films SET name = ?, releaseDate = ?, duration = ?, description = ?, mpa_id = ? WHERE film_id = ?";
        this.jdbcTemplate.update(sqlQuery, data.getName(), Date.valueOf(data.getReleaseDate()), data.getDuration(), data.getDescription(), data.getMpa().getId(), data.getId());
        return data;
    }

    @Override
    public Film get(final long id) {
        final String sqlQuery = "select * from films WHERE film_id = ?";
        List<Film> films = this.jdbcTemplate.query(sqlQuery, this::makeObject, id);
        if (1 != films.size()) {
            throw new NotFoundException("film_id=" + id);
        }
        return films.get(0);
    }

    @Override
    public void delete(final long id) {
        final String sqlQuery = "delete from films where film_id = ?";
        this.jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public List<Film> getAll() {
        return this.jdbcTemplate.query("select * from films", this::makeObject);
    }
}
