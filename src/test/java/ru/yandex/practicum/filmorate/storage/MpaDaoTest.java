package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = MpaDao.class)
class MpaDaoTest {
    private final MpaDao mpaDao;

    @Test
    protected void get() {
        final Mpa mpa = this.mpaDao.get(1);
        assertEquals(1, mpa.getId());
        assertEquals("G", mpa.getName());
    }

    @Test
    protected void getAll() {
        final List<Mpa> mpas = this.mpaDao.getAll();
        assertEquals("G", mpas.get(0).getName());
        assertEquals("PG", mpas.get(1).getName());
        assertEquals("PG-13", mpas.get(2).getName());
        assertEquals("R", mpas.get(3).getName());
        assertEquals("NC-17", mpas.get(4).getName());
    }
}