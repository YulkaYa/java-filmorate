package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.base.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.base.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.base.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.base.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.relations.GenresFilmsDbStorage;
import ru.yandex.practicum.filmorate.storage.relations.LikesDbStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {LikesDbStorage.class, UserDbStorage.class, FilmDbStorage.class, GenresFilmsDbStorage.class, GenreDbStorage.class, MpaDbStorage.class})
class LikesDbStorageTest {
    private static final String SQL_SCRIPT_SOME_FILMS_USERS_IN_DB = "/tests/likes/some-films-users.sql";
    private final LikesDbStorage likesDbStorage;

    @Test
    @Sql(scripts = LikesDbStorageTest.SQL_SCRIPT_SOME_FILMS_USERS_IN_DB)
    protected void addLike() {
        this.likesDbStorage.addRelation(2L, 1L);
        this.likesDbStorage.addRelation(3L, 0L);
        final List<Film> topFilms = this.likesDbStorage.topFilms(5L);
        assertEquals(2, topFilms.size());
        assertEquals(0L, topFilms.get(0).getId());
        assertEquals(1L, topFilms.get(1).getId());
    }

    @Test
    @Sql(scripts = LikesDbStorageTest.SQL_SCRIPT_SOME_FILMS_USERS_IN_DB)
    protected void deleteLike() {
        this.likesDbStorage.addRelation(3L, 0L);

        this.likesDbStorage.addRelation(0L, 1L);
        this.likesDbStorage.addRelation(2L, 1L);
        this.likesDbStorage.addRelation(4L, 1L);

        this.likesDbStorage.addRelation(0L, 3L);
        this.likesDbStorage.addRelation(1L, 3L);
        this.likesDbStorage.addRelation(2L, 3L);
        this.likesDbStorage.addRelation(4L, 3L);

        this.likesDbStorage.addRelation(2L, 4L);
        this.likesDbStorage.addRelation(1L, 4L);
        this.likesDbStorage.addRelation(3L, 4L);
        this.likesDbStorage.deleteRelation(2L, 1L);
        this.likesDbStorage.deleteRelation(3L, 0L);
        final List<Film> topFilms = this.likesDbStorage.topFilms(5L);
        assertEquals(3, topFilms.size());
        assertEquals(3L, topFilms.get(0).getId());
        assertEquals(4L, topFilms.get(1).getId());
        assertEquals(1L, topFilms.get(2).getId());
    }

    @Test
    @Sql(scripts = LikesDbStorageTest.SQL_SCRIPT_SOME_FILMS_USERS_IN_DB)
    protected void topFilms() {
        this.likesDbStorage.addRelation(3L, 0L);

        this.likesDbStorage.addRelation(0L, 1L);
        this.likesDbStorage.addRelation(2L, 1L);
        this.likesDbStorage.addRelation(4L, 1L);

        this.likesDbStorage.addRelation(0L, 3L);
        this.likesDbStorage.addRelation(1L, 3L);
        this.likesDbStorage.addRelation(2L, 3L);
        this.likesDbStorage.addRelation(4L, 3L);

        this.likesDbStorage.addRelation(2L, 4L);
        this.likesDbStorage.addRelation(1L, 4L);
        this.likesDbStorage.addRelation(3L, 4L);
        List<Film> topFilms = this.likesDbStorage.topFilms(5L);
        assertEquals(4, topFilms.size());
        assertEquals(3L, topFilms.get(0).getId());
        assertEquals(1L, topFilms.get(1).getId());
        assertEquals(4L, topFilms.get(2).getId());
        assertEquals(0L, topFilms.get(3).getId());

        topFilms = this.likesDbStorage.topFilms(1L);
        assertEquals(1, topFilms.size());
        assertEquals(3L, topFilms.get(0).getId());
    }
}