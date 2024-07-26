package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {LikesDao.class, UserDbStorage.class, FilmDbStorage.class, GenresFilmsDao.class, GenreDao.class, MpaDao.class})
class LikesDaoTest {
    private final LikesDao likesDao;
    private final String SQL_SCRIPT_SOME_FILMS_USERS_IN_DB = "/tests/likes/some-films-users.sql";

    @Test
    @Sql(scripts = {SQL_SCRIPT_SOME_FILMS_USERS_IN_DB})
    protected void addLike() {
        likesDao.addLike(2L, 1L);
        likesDao.addLike(3L, 0L);
        List<Film> topFilms = likesDao.topFilms(5);
        assertEquals(2, topFilms.size());
        assertEquals(0L, topFilms.get(0).getId());
        assertEquals(1L, topFilms.get(1).getId());
    }

    @Test
    @Sql(scripts = {SQL_SCRIPT_SOME_FILMS_USERS_IN_DB})
    protected void deleteLike() {
        likesDao.addLike(3L, 0L);

        likesDao.addLike(0L, 1L);
        likesDao.addLike(2L, 1L);
        likesDao.addLike(4L, 1L);

        likesDao.addLike(0L, 3L);
        likesDao.addLike(1L, 3L);
        likesDao.addLike(2L, 3L);
        likesDao.addLike(4L, 3L);

        likesDao.addLike(2L, 4L);
        likesDao.addLike(1L, 4L);
        likesDao.addLike(3L, 4L);
        likesDao.deleteLike(2L, 1L);
        likesDao.deleteLike(3L, 0L);
        List<Film> topFilms = likesDao.topFilms(5);
        assertEquals(3, topFilms.size());
        assertEquals(3L, topFilms.get(0).getId());
        assertEquals(4L, topFilms.get(1).getId());
        assertEquals(1L, topFilms.get(2).getId());
    }

    @Test
    @Sql(scripts = {SQL_SCRIPT_SOME_FILMS_USERS_IN_DB})
    protected void topFilms() {
        likesDao.addLike(3L, 0L);

        likesDao.addLike(0L, 1L);
        likesDao.addLike(2L, 1L);
        likesDao.addLike(4L, 1L);

        likesDao.addLike(0L, 3L);
        likesDao.addLike(1L, 3L);
        likesDao.addLike(2L, 3L);
        likesDao.addLike(4L, 3L);

        likesDao.addLike(2L, 4L);
        likesDao.addLike(1L, 4L);
        likesDao.addLike(3L, 4L);
        List<Film> topFilms = likesDao.topFilms(5);
        assertEquals(4, topFilms.size());
        assertEquals(3L, topFilms.get(0).getId());
        assertEquals(1L, topFilms.get(1).getId());
        assertEquals(4L, topFilms.get(2).getId());
        assertEquals(0L, topFilms.get(3).getId());

        topFilms = likesDao.topFilms(1);
        assertEquals(1, topFilms.size());
        assertEquals(3L, topFilms.get(0).getId());
    }
}