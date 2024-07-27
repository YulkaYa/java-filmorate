package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {FilmDbStorage.class, GenresFilmsDao.class, GenreDao.class, MpaDao.class})
@Sql(scripts = "/tests/films/some-films.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GenresFilmsDaoTest {
    private final FilmDbStorage filmStorage;
    private final GenreDao genreDao;
    private static List<Genre> genresTypesList;

    @BeforeEach
    protected void getAllGenres() {
        genresTypesList = genreDao.getAll();
    }

    @Test
    protected void getFilmGenre() {
        List<Genre> genreList = GenresFilmsDao.getFilmGenre(0L);
        assertEquals(0, genreList.size());
        final Film film = filmStorage.get(0L);
        film.setGenres(List.of(genresTypesList.get(3), genresTypesList.get(1)));
        GenresFilmsDao.addFilmGenre(film);
        genreList = GenresFilmsDao.getFilmGenre(0L);
        assertEquals(2, genreList.size());
        assertEquals(new Genre(4, "Триллер"), genreList.get(0));
        assertEquals(new Genre(2, "Драма"), genreList.get(1));
    }

    @Test
    protected void addFilmGenre() {
        final Film film = filmStorage.get(0L);
        film.setGenres(List.of(genresTypesList.get(3), genresTypesList.get(1)));
        GenresFilmsDao.addFilmGenre(film);
        List<Genre> genreList = GenresFilmsDao.getFilmGenre(0L);
        assertEquals(2, genreList.size());
        assertEquals(new Genre(4, "Триллер"), genreList.get(0));
        assertEquals(new Genre(2, "Драма"), genreList.get(1));

        genreList.add(genresTypesList.get(2));
        film.setGenres(genreList);
        GenresFilmsDao.addFilmGenre(film);
        genreList = GenresFilmsDao.getFilmGenre(0L);
        assertEquals(3, genreList.size());
        assertEquals(new Genre(4, "Триллер"), genreList.get(0));
        assertEquals(new Genre(2, "Драма"), genreList.get(1));
        assertEquals(new Genre(3, "Мультфильм"), genreList.get(2));
    }
}