package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.base.MpaDbStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = MpaDbStorage.class)
class MpaDbStorageTest {
    private final MpaDbStorage mpaDbStorage;

    @Test
    protected void get() {
        Mpa mpa = this.mpaDbStorage.get(1L);
        assertEquals(1, mpa.getId());
        assertEquals("G", mpa.getName());
    }

    @Test
    protected void getAll() {
        List<Mpa> mpas = this.mpaDbStorage.getAll();
        assertEquals("G", mpas.get(0).getName());
        assertEquals("PG", mpas.get(1).getName());
        assertEquals("PG-13", mpas.get(2).getName());
        assertEquals("R", mpas.get(3).getName());
        assertEquals("NC-17", mpas.get(4).getName());
    }
}