package ru.yandex.practicum.filmorate.storage.base;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.base.interfaces.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
@Primary
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre makeObject(final ResultSet rs, final int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("genre_id"))
                .name(rs.getString("name"))
                .build();
    }

    @Override
    public Genre get(final long id) throws NotFoundException {
        final String sqlQuery = "select * from genres WHERE genre_id = ?";
        List<Genre> genres = this.jdbcTemplate.query(sqlQuery, this::makeObject, id);
        if (1 != genres.size()) {
            throw new NotFoundException("genre_id=" + id);
        }
        return genres.get(0);
    }

    @Override
    public List<Genre> getAll() {
        return this.jdbcTemplate.query("select * from genres order by genre_id", this::makeObject);
    }
}
