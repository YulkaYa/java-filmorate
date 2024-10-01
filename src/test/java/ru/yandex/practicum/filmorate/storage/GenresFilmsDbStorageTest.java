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
import ru.yandex.practicum.filmorate.storage.base.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.base.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.base.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.relations.GenresFilmsDbStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {FilmDbStorage.class, GenresFilmsDbStorage.class, GenreDbStorage.class, MpaDbStorage.class})
@Sql(scripts = "/tests/films/some-films.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GenresFilmsDbStorageTest {
    private static List<Genre> genresTypesList;
    private final FilmDbStorage filmStorage;
    private final GenreDbStorage genreDbStorage;
    private final GenresFilmsDbStorage genresFilmsDbStorage;

    @BeforeEach
    protected void getAllGenres() {
        GenresFilmsDbStorageTest.genresTypesList = this.genreDbStorage.getAll();
    }

    @Test
    protected void getRelations() {
        List<Genre> genreList = this.genresFilmsDbStorage.getRelations(0L);
        assertEquals(0, genreList.size());
        Film film = this.filmStorage.get(0L);
        film.setGenres(List.of(GenresFilmsDbStorageTest.genresTypesList.get(3), GenresFilmsDbStorageTest.genresTypesList.get(1)));
        this.genresFilmsDbStorage.addFilmGenre(film);
        genreList = this.genresFilmsDbStorage.getRelations(0L);
        assertEquals(2, genreList.size());
        assertEquals(new Genre(4L, "Триллер"), genreList.get(0));
        assertEquals(new Genre(2L, "Драма"), genreList.get(1));
    }

    @Test
    protected void addFilmGenre() {
        Film film = this.filmStorage.get(0L);
        film.setGenres(List.of(GenresFilmsDbStorageTest.genresTypesList.get(3), GenresFilmsDbStorageTest.genresTypesList.get(1)));
        this.genresFilmsDbStorage.addFilmGenre(film);
        List<Genre> genreList = this.genresFilmsDbStorage.getRelations(0L);
        assertEquals(2, genreList.size());
        assertEquals(new Genre(4L, "Триллер"), genreList.get(0));
        assertEquals(new Genre(2L, "Драма"), genreList.get(1));

        genreList.add(GenresFilmsDbStorageTest.genresTypesList.get(2));
        film.setGenres(genreList);
        this.genresFilmsDbStorage.addFilmGenre(film);
        genreList = this.genresFilmsDbStorage.getRelations(0L);
        assertEquals(3, genreList.size());
        assertEquals(new Genre(4L, "Триллер"), genreList.get(0));
        assertEquals(new Genre(2L, "Драма"), genreList.get(1));
        assertEquals(new Genre(3L, "Мультфильм"), genreList.get(2));
    }
}