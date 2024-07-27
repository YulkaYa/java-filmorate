package ru.yandex.practicum.filmorate.storage;

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

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Primary
@RequiredArgsConstructor
@Component
public class FilmDbStorage implements FilmStorage {
    private static GenresFilmsDao genresFilmsDao;
    private static MpaDao mpaDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(final GenresFilmsDao genresFilmsDao, final MpaDao mpaDao, final JdbcTemplate jdbcTemplate) {
        FilmDbStorage.genresFilmsDao = genresFilmsDao;
        FilmDbStorage.mpaDao = mpaDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film film = Film.builder().id(rs.getLong("film_id")).name(rs.getString("name")).description(rs.getString("description")).releaseDate(rs.getDate("releaseDate").toLocalDate()).duration(rs.getLong("duration")).genres(new ArrayList<>()).build();
        if (0 != rs.getInt("mpa_id")) {
            film.setMpa(mpaDao.get(rs.getInt("mpa_id")));
        }
        film.setGenres(GenresFilmsDao.getFilmGenre(film.getId()));
        return film;
    }

    @Override
    public Film create(Film data) {
        final String sqlQuery = """
                insert into films (
                     name, description, releaseDate, duration, mpa_id
                ) values (?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            Mpa mpa = mpaDao.get(data.getMpa().getId());
            data.setMpa(mpa);
        } catch (NotFoundException e) {
            throw new IllegalArgumentException("Ошибка добавления mpa_id = " + data.getMpa().getId());
        }
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, data.getName());
            stmt.setString(2, data.getDescription());
            stmt.setDate(3, Date.valueOf(data.getReleaseDate()));
            stmt.setLong(4, data.getDuration());
            stmt.setInt(5, data.getMpa().getId());
            return stmt;
        }, keyHolder);
        try {
            data.setId(keyHolder.getKey().longValue());
        } catch (NullPointerException e) {
            throw new RuntimeException("Ошибка при добавлении id");
        }
        if (!data.getGenres().isEmpty()) {
            GenresFilmsDao.addFilmGenre(data);
        }
        return data;
    }

    @Override
    public Film update(Film data) {
        get(data.getId());
        final String sqlQuery = "update films SET name = ?, releaseDate = ?, duration = ?, description = ?, mpa_id = ? WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, data.getName(), Date.valueOf(data.getReleaseDate()), data.getDuration(), data.getDescription(), data.getMpa().getId(), data.getId());
        return data;
    }

    @Override
    public Film get(long id) {
        final String sqlQuery = "select * from films WHERE film_id = ?";
        final List<Film> films = jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm, id);
        if (1 != films.size()) {
            throw new NotFoundException("film_id=" + id);
        }
        return films.get(0);
    }

    @Override
    public void delete(long id) {
        final String sqlQuery = "delete from films where film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public List<Film> getAll() {
        return jdbcTemplate.query("select * from films", FilmDbStorage::makeFilm);
    }
}
