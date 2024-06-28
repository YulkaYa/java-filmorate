package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GenreDao {
    private final JdbcTemplate jdbcTemplate;

    public Genre get(int id) throws NotFoundException {
        final String sqlQuery = "select * from genres WHERE genre_id = ?";
        final List<Genre> genres = jdbcTemplate.query(sqlQuery, GenreDao::makeGenre, id);
        if (genres.size() != 1) {
            throw new NotFoundException("genre_id=" + id);
        }
        return genres.get(0);
    }

    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("genre_id"))
                .name(rs.getString("name"))
                .build();
    }

    public List<Genre> getAll() {
        return jdbcTemplate.query("select * from genres order by genre_id", GenreDao::makeGenre);
    }
}
