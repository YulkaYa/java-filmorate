package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {FilmDbStorage.class, GenresFilmsDao.class, GenreDao.class, MpaDao.class})
class FilmDBStorageTests {

    private final FilmDbStorage filmStorage;
    private static final String SQL_SCRIPT_ONE_FILM_IN_DB = "/tests/films/film.sql";
    private static final String SQL_SCRIPT_SOME_FILMS_IN_DB = "/tests/films/some-films.sql";

    @Test
    @Sql(scripts = {SQL_SCRIPT_ONE_FILM_IN_DB})
    protected void createFilm() {
        // Имеем 1 фильм в БД
        filmStorage.create(
                Film.builder()
                        .id(1L)
                        .name("FirstFilm")
                        .description("descriptionFirst")
                        .releaseDate(LocalDate.of(1990, 12, 29))
                        .mpa(new Mpa().toBuilder().id(3).build())
                        .duration(45L)
                        .build());
        List<Film> films = filmStorage.getAll();
        assertEquals(2, films.size());
        List<Genre> genres = films.get(1).getGenres();
        assertEquals(0, genres.size());
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("name", "FirstFilm");
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("description", "descriptionFirst");
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(1990, 12, 29));
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("mpa", (new Mpa().toBuilder().id(3).name("PG-13").build()));
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("duration", 45L);
    }


    @Test
    @Sql(scripts = {SQL_SCRIPT_SOME_FILMS_IN_DB})
    protected void updateFilm() {
        // Имеем 2 фильма в БД
        Film filmUpdated = Film.builder()
                .id(1L)
                .name("FilmUpdated")
                .description("descriptionUpdated")
                .releaseDate(LocalDate.of(1992, 11, 30))
                .mpa(new Mpa().toBuilder().id(4).build())
                .duration(60L)
                .build();
        filmStorage.update(filmUpdated);
        List<Film> films = filmStorage.getAll();
        assertEquals(2, films.size());
        List<Genre> genres = films.get(1).getGenres();
        assertEquals(0, genres.size());
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("name", "FilmUpdated");
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("description", "descriptionUpdated");
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(1992, 11, 30));
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("mpa", (new Mpa().toBuilder().id(4).name("R").build()));
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("duration", 60L);
    }

    @Test
    @Sql(scripts = {SQL_SCRIPT_SOME_FILMS_IN_DB})
    protected void delete() {
        // Имеем 2 фильма в БД
        assertEquals(2, filmStorage.getAll().size());

        // Удаляем 1 из них
        filmStorage.delete(1L);

        // Проверяем что остался 1 фильм
        assertEquals(1, filmStorage.getAll().size());
    }

    @Test
    @Sql(scripts = {SQL_SCRIPT_ONE_FILM_IN_DB})
    protected void get() {
        List<Film> films = filmStorage.getAll();
        List<Genre> genres = films.get(0).getGenres();
        assertEquals(0, genres.size());
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("id", 0L);
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("name", "nameOfZeroFilm");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("description", "description");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(1987, 12, 28));
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("mpa", (new Mpa().toBuilder().id(1).name("G").build()));
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("duration", 23L);
    }

    @Test
    @Sql(scripts = {SQL_SCRIPT_SOME_FILMS_IN_DB})
    protected void getAll() {
        List<Film> films = filmStorage.getAll();
        assertEquals(2, films.size());
        List<Genre> genres = films.get(0).getGenres();
        assertEquals(0, genres.size());

        assertThat(films.get(0)).hasFieldOrPropertyWithValue("id", 0L);
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("name", "nameOfZeroFilm");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("description", "descriptionZeroFilm");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(1988, 12, 28));
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("mpa", (new Mpa().toBuilder().id(1).name("G").build()));
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("duration", 34L);

        genres = films.get(1).getGenres();
        assertEquals(0, genres.size());
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("name", "nameOfFirstFilm");
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("description", "descriptionFirstFilm");
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(1989, 12, 28));
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("mpa", (new Mpa().toBuilder().id(2).name("PG").build()));
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("duration", 35L);
    }
}