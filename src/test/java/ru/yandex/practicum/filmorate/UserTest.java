package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Create;
import ru.yandex.practicum.filmorate.model.Update;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserTest {

    private final User userCreate = User.builder()
            .name("Name")
            .login("Login")
            .email("mail@gmail.com")
            .birthday(LocalDate.of(1995, 12, 28))
            .build();
    private final User userUpdate = userCreate.toBuilder()
            .id(1L)
            .build();
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @AllArgsConstructor
    private static class ExpectedViolation {
        private String propertyPath;
        private String message;
    }

    @Test
    void createOkWhenIdNullTest() {
        final User userTest = userCreate.toBuilder().id(null).build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(userTest,
                Create.class));
        assertEquals(0, violations.size());
    }

    @Test
    void createFailedWhenIdNotNullTest() {
        User userTest = userCreate.toBuilder().id(1000L).build();

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(userTest,
                Create.class));
        assertEquals(1, violations.size());
        ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "id", "Id при создании пользователя должен быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void updateFailWhenIdNullTest() {
        final User userTest = userUpdate.toBuilder().id(null).build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(userTest,
                Update.class));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
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
    void updateFailedWhenIdZeroTest() {
        User userTest = userUpdate.toBuilder().id(0L).build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(userTest));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
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
    void updateFailedWhenIdNegativeTest() {
        User userTest = userUpdate.toBuilder().id(-1L).build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(userTest));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
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
    void createFailedWhenLoginNullTest() {
        User userTest = userCreate.toBuilder()
                .login(null)
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "login", "Логин не может быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void createFailedWhenLoginAbsentTest() {
        User userTest = User.builder()
                .name(userCreate.getName())
                .email(userCreate.getEmail())
                .birthday(userCreate.getBirthday())
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "login", "Логин не может быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void createFailedWhenLoginEmptyTest() {
        User userTest = userCreate.toBuilder()
                .login("")
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "login", "Логин не может быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void createFailedWhenLoginBlankTest() {
        User userTest = userCreate.toBuilder()
                .login(" ")
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "login", "Логин не может быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void updateOkWhenLoginNullTest() {
        User userTest = userUpdate.toBuilder()
                .login(null)
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    void updateOkWhenLoginAbsentTest() {
        User userTest = User.builder()
                .name(userCreate.getName())
                .email(userCreate.getEmail())
                .birthday(userCreate.getBirthday())
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    void createOrUpdateFailedWhenLoginEmptyTest() {
        User userTest = userUpdate.toBuilder()
                .login("")
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "login", "Логин не может содержать пробелы или быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void createOrUpdateFailedWhenLoginBlankTest() {
        User userTest = userUpdate.toBuilder()
                .login(" ")
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "login", "Логин не может содержать пробелы или быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void createOrUpdateFailedWhenLoginContainsSpacesTest() {
        User userTest = userUpdate.toBuilder()
                .login("login login")
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "login", "Логин не может содержать пробелы или быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }


    @Test
    void createFailedWhenEmailNullTest() {
        User userTest = userCreate.toBuilder()
                .email(null)
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "email", "Электронная почта не может быть пустой");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void createFailedWhenEmailAbsentTest() {
        User userTest = User.builder()
                .name(userCreate.getName())
                .birthday(userCreate.getBirthday())
                .login(userCreate.getLogin())
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "email", "Электронная почта не может быть пустой");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());

    }

    @Test
    void createOrUpdateFailedWhenEmailNotFitPatternTest() {
        User userTest = userCreate.toBuilder()
                .email("@mail.ru")
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "email", "Электронная почта должна содержать символ @ и соответствовать правилам " +
                "названия email");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }


    @Test
    void updateOkWhenEmailNullTest() {
        User userTest = userUpdate.toBuilder()
                .email(null)
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    void updateOkWhenEmailAbsentTest() {
        User userTest = User.builder()
                .id(userUpdate.getId())
                .login(userUpdate.getLogin())
                .birthday(userUpdate.getBirthday())
                .name(userUpdate.getName())
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    void createOrUpdateOkWhenNameContainsSpacesTest() {
        User userTest = userCreate.toBuilder()
                .name("name null")
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(0, violations.size());
        assertEquals("name null", userTest.getName());
        assertEquals(userCreate.getLogin(), userTest.getLogin());
    }

    @Test
    void createOrUpdateFailedWhenNameBlankTest() {
        User userTest = userCreate.toBuilder()
                .name("")
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "name", "Имя не может состоять из пробелов или быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void createOrUpdateFailedWhenNameContainsOnlySpacesTest() {
        User userTest = userCreate.toBuilder()
                .name(" ")
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "name", "Имя не может состоять из пробелов или быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void createOrUpdateOkWhenBirthdayAbsentTest() {
        User userTest = User.builder()
                .name(userCreate.getName())
                .login(userCreate.getLogin())
                .email(userCreate.getEmail())
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    void createOrUpdateOkWhenBirthdayNullTest() {
        User userTest = userCreate.toBuilder()
                .birthday(null)
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    void createOrUpdateOkWhenBirthdayInPresentTest() {
        User userTest = userCreate.toBuilder()
                .birthday(LocalDate.now())
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(1, violations.size());
    }

    @Test
    void createOrUpdateFailedWhenBirthdayInFutureTest() {
        User userTest = userCreate.toBuilder()
                .birthday(LocalDate.now().plusDays(1))
                .build();
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                userTest));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "birthday", "Дата рождения не может быть в будущем");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void checkUpdateFailedFieldNameEmptyTest() throws IllegalAccessException {
        final User userTest = userUpdate.toBuilder().build();
        final User newUserTest = userUpdate.toBuilder()
                .name("")
                .build();
        User updatedUser = User.buildNewUser(userTest, newUserTest);
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                updatedUser));
        assertEquals(1, violations.size());
        UserTest.ExpectedViolation expectedViolation = new UserTest.ExpectedViolation(
                "name", "Имя не может состоять из пробелов или быть пустым");
        assertEquals(
                expectedViolation.propertyPath,
                violations.get(0).getPropertyPath().toString()
        );
        assertEquals(
                expectedViolation.message,
                violations.get(0).getMessage());
    }

    @Test
    void checkUpdateOkFieldNameNotEmptyTest() throws IllegalAccessException {
        final User userTest = userUpdate.toBuilder().build();
        final User newUserTest = userUpdate.toBuilder()
                .name("fgtrytr")
                .build();
        User updatedUser = User.buildNewUser(userTest, newUserTest);
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                updatedUser));
        assertEquals(0, violations.size());
        assertEquals(userTest.getId(), updatedUser.getId());
        assertEquals(userTest.getBirthday(), updatedUser.getBirthday());
        assertEquals(userTest.getEmail(), updatedUser.getEmail());
        assertEquals(userTest.getLogin(), updatedUser.getLogin());
        assertEquals("fgtrytr", updatedUser.getName());
    }

    @Test
    void checkUpdateOkFieldsNotEmptyTest() throws IllegalAccessException {
        final User userTest = userUpdate.toBuilder().build();
        final User newUserTest = User.builder()
                .id(userTest.getId())
                .name("sdfsdfsdf")
                .email("saddasd@mail.ru")
                .login("sdsds")
                .birthday(LocalDate.now().minusYears(10))
                .build();
        User updatedUser = User.buildNewUser(userTest, newUserTest);
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(
                updatedUser));
        assertEquals(0, violations.size());
        assertEquals(newUserTest.getId(), updatedUser.getId());
        assertEquals(newUserTest.getBirthday(), updatedUser.getBirthday());
        assertEquals(newUserTest.getEmail(), updatedUser.getEmail());
        assertEquals(newUserTest.getLogin(), updatedUser.getLogin());
        assertEquals(newUserTest.getName(), updatedUser.getName());
    }
}

