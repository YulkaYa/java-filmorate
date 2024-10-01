package ru.yandex.practicum.filmorate.storage.base;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.base.interfaces.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
@Primary
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa makeObject(final ResultSet rs, final int rowNum) throws SQLException {
        return Mpa.builder()
                .id(rs.getLong("mpa_id"))
                .name(rs.getString("name"))
                .build();
    }

    @Override
    public Mpa get(final long id) throws NotFoundException {
        final String sqlQuery = "select * from mpa WHERE mpa_id = ?";
        List<Mpa> mpas = this.jdbcTemplate.query(sqlQuery, this::makeObject, id);
        if (1 != mpas.size()) {
            throw new NotFoundException("mpa_id=" + id);
        }
        return mpas.get(0);
    }

    @Override
    public List<Mpa> getAll() {
        return this.jdbcTemplate.query("select * from mpa order by mpa_id", this::makeObject);
    }
}
