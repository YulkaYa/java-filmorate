package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FilmTest {
    private final Film filmCreate = Film.builder()
            .name("Name")
            .description("filmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilm" +
                    "filmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmfilmf" +
                    "ilmfilmfilmfilmfilmfilmfilmfilm")
            .releaseDate(LocalDate.of(1985, 12, 28))
            .duration(1L)
            .build();
    private final Film filmUpdate = filmCreate.toBuilder()
            .id(1L)
            .build();

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    protected void createOkWhenIdNullTest() {
        final Film filmTest = filmCreate.toBuilder().id(null).build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(filmTest,
                Create.class));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createFailedWhenIdNotNullTest() {
        Film filmTest = filmCreate.toBuilder().id(1000L).build();

        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(filmTest,
                Create.class));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation("id", "Id при создании должен быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void updateFailWhenIdNullTest() {
        final Film filmTest = filmUpdate.toBuilder().id(null).build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(filmTest,
                Update.class));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "id", "Id при обновлении не должен быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void updateFailedWhenIdZeroTest() {
        Film filmTest = filmUpdate.toBuilder().id(0L).build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(filmTest));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "id", "Id должен быть положительным целым числом");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void updateFailedWhenIdNegativeTest() {
        Film filmTest = filmUpdate.toBuilder().id(-1L).build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(filmTest));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "id", "Id должен быть положительным целым числом");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void createFailedWhenNameNullTest() {
        Film filmTest = filmCreate.toBuilder()
                .name(null)
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest, Create.class));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "name", "Название не должно быть null или состоять из пробелов");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void createFailedWhenNameIsAbsentTest() {
        Film filmTest = Film.builder()
                .description(filmCreate.getDescription())
                .duration(filmCreate.getDuration())
                .releaseDate(filmCreate.getReleaseDate())
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest, Create.class));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "name", "Название не должно быть null или состоять из пробелов");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void createFailedWhenNameIsEmptyTest() {
        Film filmTest = filmCreate.toBuilder().name("").build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "name", "Название не может состоять из пробелов");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void createFailedWhenNameIsBlankTest() {
        Film filmTest = filmCreate.toBuilder()
                .name("  ")
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "name", "Название не может состоять из пробелов");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void updateOkWhenNameNullTest() {
        final Film filmTest = filmUpdate.toBuilder()
                .name(null)
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(filmTest, Update.class));
        assertEquals(0, violations.size());
    }

    @Test
    protected void updateOkWhenNameAbsentTest() {
        final Film filmTest = Film.builder()
                .id(filmUpdate.getId())
                .description(filmUpdate.getDescription())
                .duration(filmUpdate.getDuration())
                .releaseDate(filmUpdate.getReleaseDate())
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(filmTest,
                Update.class));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createOkWhenDescriptionNullTest() {
        Film filmTest = filmCreate.toBuilder().description(null).build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createOkWhenDescriptionIsAbsentTest() {
        Film filmTest = Film.builder()
                .name(filmCreate.getName())
                .duration(filmCreate.getDuration())
                .releaseDate(filmCreate.getReleaseDate())
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createFailedWhenDescriptionMoreThanExpectedTest() {
        Film filmTest = filmCreate.toBuilder()
                .description(filmCreate.getDescription() + "d")
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "description", "Максимальная длина описания — 200 символов");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void createOkWhenReleaseDateAbsentTest() {
        Film filmTest = Film.builder()
                .name(filmCreate.getName())
                .description(filmCreate.getDescription())
                .duration(filmCreate.getDuration())
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createOkWhenReleaseDateInFutureTest() {
        Film filmTest = filmCreate.toBuilder()
                .releaseDate(LocalDate.now().plusDays(1))
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createFailWhenReleaseDateBeforeDateOfCreationOfCinematographyTest() {
        Film filmTest = filmCreate.toBuilder()
                .releaseDate(LocalDate.of(1895, 12, 27))
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "releaseDate", "Неправильная дата релиза. Дата не может быть раньше 28.12.1895");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void createOkWhenDurationAbsentTest() {
        final Film filmTest = Film.builder()
                .name(filmCreate.getName())
                .description(filmCreate.getDescription())
                .releaseDate(filmCreate.getReleaseDate())
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createFailedWhenDurationNegativeTest() {
        final Film filmTest = filmCreate.toBuilder()
                .duration(-1L)
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "duration", "Продолжительность фильма должна быть положительным числом");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void createFailedWhenDurationZeroTest() {
        final Film filmTest = filmCreate.toBuilder()
                .duration(0L)
                .build();
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new ExpectedViolation(
                "duration", "Продолжительность фильма должна быть положительным числом");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    protected void checkUpdateOkFieldsNotChangedTest() {
        final Film filmTest = filmUpdate.toBuilder().build();
        final Film newFilmTest = Film.builder()
                .id(filmTest.getId())
                .build();
        Film updatedFilm = Film.buildNewFilm(filmTest, newFilmTest);
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                updatedFilm));
        assertEquals(0, violations.size());
        assertEquals(filmTest.getName(), updatedFilm.getName());
        assertEquals(filmTest.getId(), updatedFilm.getId());
        assertEquals(filmTest.getReleaseDate(), updatedFilm.getReleaseDate());
        assertEquals(filmTest.getDescription(), updatedFilm.getDescription());
        assertEquals(filmTest.getDuration(), updatedFilm.getDuration());
    }

    @Test
    protected void checkUpdateOkFieldNameNotEmptyTest() {
        final Film filmTest = filmUpdate.toBuilder().build();
        final Film newFilmTest = Film.builder()
                .id(filmTest.getId())
                .name("dadasdads")
                .build();
        Film updatedFilm = Film.buildNewFilm(filmTest, newFilmTest);
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                updatedFilm));
        assertEquals(0, violations.size());
        assertEquals(newFilmTest.getName(), updatedFilm.getName());
        assertEquals(filmTest.getId(), updatedFilm.getId());
        assertEquals(filmTest.getReleaseDate(), updatedFilm.getReleaseDate());
        assertEquals(filmTest.getDescription(), updatedFilm.getDescription());
        assertEquals(filmTest.getDuration(), updatedFilm.getDuration());
    }

    @Test
    protected void checkUpdateOkFieldsNotEmptyTest() {
        final Film filmTest = filmUpdate.toBuilder().build();
        final Film newFilmTest = Film.builder()
                .id(filmTest.getId())
                .name("dadasdads")
                .description(("dsfsdfsdsdf"))
                .duration(6L)
                .releaseDate(LocalDate.of(2010, 10, 1))
                .build();
        Film updatedFilm = Film.buildNewFilm(filmTest, newFilmTest);
        List<ConstraintViolation<Film>> violations = new ArrayList<>(validator.validate(
                updatedFilm));
        assertEquals(0, violations.size());
        assertEquals(newFilmTest.getName(), updatedFilm.getName());
        assertEquals(newFilmTest.getId(), updatedFilm.getId());
        assertEquals(newFilmTest.getReleaseDate(), updatedFilm.getReleaseDate());
        assertEquals(newFilmTest.getDescription(), updatedFilm.getDescription());
        assertEquals(newFilmTest.getDuration(), updatedFilm.getDuration());
    }
}
