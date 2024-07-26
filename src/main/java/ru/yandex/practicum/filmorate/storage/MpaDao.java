package ru.yandex.practicum.filmorate.storage;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class MpaDao {
    private final JdbcTemplate jdbcTemplate;

    static Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(rs.getInt("mpa_id"))
                .name(rs.getString("name"))
                .build();
    }

    public Mpa get(int id) throws NotFoundException {
        final String sqlQuery = "select * from mpa WHERE mpa_id = ?";
        final List<Mpa> mpas = jdbcTemplate.query(sqlQuery, MpaDao::makeMpa, id);
        if (1 != mpas.size()) {
            throw new NotFoundException("mpa_id=" + id);
        }
        return mpas.get(0);
    }

    public List<Mpa> getAll() {
        return jdbcTemplate.query("select * from mpa order by mpa_id", MpaDao::makeMpa);
    }
}
