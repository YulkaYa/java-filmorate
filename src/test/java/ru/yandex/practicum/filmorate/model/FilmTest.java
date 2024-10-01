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
    private final Film filmUpdate = this.filmCreate.toBuilder()
            .id(1L)
            .build();

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    protected void createOkWhenIdNullTest() {
        Film filmTest = this.filmCreate.toBuilder().id(null).build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(filmTest,
                Create.class));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createFailedWhenIdNotNullTest() {
        final Film filmTest = this.filmCreate.toBuilder().id(1000L).build();

        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(filmTest,
                Create.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation("id", "Id при создании должен быть пустым");
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
        Film filmTest = this.filmUpdate.toBuilder().id(null).build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(filmTest,
                Update.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        final Film filmTest = this.filmUpdate.toBuilder().id(0L).build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(filmTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        final Film filmTest = this.filmUpdate.toBuilder().id(-1L).build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(filmTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        final Film filmTest = this.filmCreate.toBuilder()
                .name(null)
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest, Create.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        final Film filmTest = Film.builder()
                .description(this.filmCreate.getDescription())
                .duration(this.filmCreate.getDuration())
                .releaseDate(this.filmCreate.getReleaseDate())
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest, Create.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        final Film filmTest = this.filmCreate.toBuilder().name("").build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        final Film filmTest = this.filmCreate.toBuilder()
                .name("  ")
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        Film filmTest = this.filmUpdate.toBuilder()
                .name(null)
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(filmTest, Update.class));
        assertEquals(0, violations.size());
    }

    @Test
    protected void updateOkWhenNameAbsentTest() {
        Film filmTest = Film.builder()
                .id(this.filmUpdate.getId())
                .description(this.filmUpdate.getDescription())
                .duration(this.filmUpdate.getDuration())
                .releaseDate(this.filmUpdate.getReleaseDate())
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(filmTest,
                Update.class));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createOkWhenDescriptionNullTest() {
        final Film filmTest = this.filmCreate.toBuilder().description(null).build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createOkWhenDescriptionIsAbsentTest() {
        final Film filmTest = Film.builder()
                .name(this.filmCreate.getName())
                .duration(this.filmCreate.getDuration())
                .releaseDate(this.filmCreate.getReleaseDate())
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createFailedWhenDescriptionMoreThanExpectedTest() {
        final Film filmTest = this.filmCreate.toBuilder()
                .description(this.filmCreate.getDescription() + "d")
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        final Film filmTest = Film.builder()
                .name(this.filmCreate.getName())
                .description(this.filmCreate.getDescription())
                .duration(this.filmCreate.getDuration())
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createOkWhenReleaseDateInFutureTest() {
        final Film filmTest = this.filmCreate.toBuilder()
                .releaseDate(LocalDate.now().plusDays(1))
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createFailWhenReleaseDateBeforeDateOfCreationOfCinematographyTest() {
        final Film filmTest = this.filmCreate.toBuilder()
                .releaseDate(LocalDate.of(1895, 12, 27))
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        Film filmTest = Film.builder()
                .name(this.filmCreate.getName())
                .description(this.filmCreate.getDescription())
                .releaseDate(this.filmCreate.getReleaseDate())
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createFailedWhenDurationNegativeTest() {
        Film filmTest = this.filmCreate.toBuilder()
                .duration(-1L)
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        Film filmTest = this.filmCreate.toBuilder()
                .duration(0L)
                .build();
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                filmTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
        Film filmTest = this.filmUpdate.toBuilder().build();
        Film newFilmTest = Film.builder()
                .id(filmTest.getId())
                .build();
        final Film updatedFilm = Film.buildNewFilm(filmTest, newFilmTest);
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
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
        Film filmTest = this.filmUpdate.toBuilder().build();
        Film newFilmTest = Film.builder()
                .id(filmTest.getId())
                .name("dadasdads")
                .build();
        final Film updatedFilm = Film.buildNewFilm(filmTest, newFilmTest);
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
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
        Film filmTest = this.filmUpdate.toBuilder().build();
        Film newFilmTest = Film.builder()
                .id(filmTest.getId())
                .name("dadasdads")
                .description(("dsfsdfsdsdf"))
                .duration(6L)
                .releaseDate(LocalDate.of(2010, 10, 1))
                .build();
        final Film updatedFilm = Film.buildNewFilm(filmTest, newFilmTest);
        final List<ConstraintViolation<Film>> violations = new ArrayList<>(this.validator.validate(
                updatedFilm));
        assertEquals(0, violations.size());
        assertEquals(newFilmTest.getName(), updatedFilm.getName());
        assertEquals(newFilmTest.getId(), updatedFilm.getId());
        assertEquals(newFilmTest.getReleaseDate(), updatedFilm.getReleaseDate());
        assertEquals(newFilmTest.getDescription(), updatedFilm.getDescription());
        assertEquals(newFilmTest.getDuration(), updatedFilm.getDuration());
    }
}
