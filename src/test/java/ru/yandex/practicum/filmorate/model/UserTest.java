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
class UserTest {

    private final User userCreate = User.builder()
            .name("Name")
            .login("Login")
            .email("mail@gmail.com")
            .birthday(LocalDate.of(1995, 12, 28))
            .build();
    private final User userUpdate = this.userCreate.toBuilder()
            .id(1L)
            .build();
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    protected void createOkWhenIdNullTest() {
        User userTest = this.userCreate.toBuilder().id(null).build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(userTest,
                Create.class));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createFailedWhenIdNotNullTest() {
        final User userTest = this.userCreate.toBuilder().id(1000L).build();

        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(userTest,
                Create.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
                "id", "Id при создании должен быть пустым");
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
        User userTest = this.userUpdate.toBuilder().id(null).build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(userTest,
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
        final User userTest = this.userUpdate.toBuilder().id(0L).build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(userTest));
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
        final User userTest = this.userUpdate.toBuilder().id(-1L).build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(userTest));
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
    protected void createFailedWhenLoginNullTest() {
        final User userTest = this.userCreate.toBuilder()
                .login(null)
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void createFailedWhenLoginAbsentTest() {
        final User userTest = User.builder()
                .name(this.userCreate.getName())
                .email(this.userCreate.getEmail())
                .birthday(this.userCreate.getBirthday())
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void createFailedWhenLoginEmptyTest() {
        final User userTest = this.userCreate.toBuilder()
                .login("")
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void createFailedWhenLoginBlankTest() {
        final User userTest = this.userCreate.toBuilder()
                .login(" ")
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void updateOkWhenLoginNullTest() {
        final User userTest = this.userUpdate.toBuilder()
                .login(null)
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void updateOkWhenLoginAbsentTest() {
        final User userTest = User.builder()
                .name(this.userCreate.getName())
                .email(this.userCreate.getEmail())
                .birthday(this.userCreate.getBirthday())
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createOrUpdateFailedWhenLoginEmptyTest() {
        final User userTest = this.userUpdate.toBuilder()
                .login("")
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void createOrUpdateFailedWhenLoginBlankTest() {
        final User userTest = this.userUpdate.toBuilder()
                .login(" ")
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void createOrUpdateFailedWhenLoginContainsSpacesTest() {
        final User userTest = this.userUpdate.toBuilder()
                .login("login login")
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void createFailedWhenEmailNullTest() {
        final User userTest = this.userCreate.toBuilder()
                .email(null)
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void createFailedWhenEmailAbsentTest() {
        final User userTest = User.builder()
                .name(this.userCreate.getName())
                .birthday(this.userCreate.getBirthday())
                .login(this.userCreate.getLogin())
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest, Create.class));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void createOrUpdateFailedWhenEmailNotFitPatternTest() {
        final User userTest = this.userCreate.toBuilder()
                .email("@mail.ru")
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void updateOkWhenEmailNullTest() {
        final User userTest = this.userUpdate.toBuilder()
                .email(null)
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void updateOkWhenEmailAbsentTest() {
        final User userTest = User.builder()
                .id(this.userUpdate.getId())
                .login(this.userUpdate.getLogin())
                .birthday(this.userUpdate.getBirthday())
                .name(this.userUpdate.getName())
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createOrUpdateOkWhenNameContainsSpacesTest() {
        final User userTest = this.userCreate.toBuilder()
                .name("name null")
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(0, violations.size());
        assertEquals("name null", userTest.getName());
        assertEquals(this.userCreate.getLogin(), userTest.getLogin());
    }

    @Test
    protected void createOrUpdateFailedWhenNameBlankTest() {
        final User userTest = this.userCreate.toBuilder()
                .name("")
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void createOrUpdateFailedWhenNameContainsOnlySpacesTest() {
        final User userTest = this.userCreate.toBuilder()
                .name(" ")
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void createOrUpdateOkWhenBirthdayAbsentTest() {
        final User userTest = User.builder()
                .name(this.userCreate.getName())
                .login(this.userCreate.getLogin())
                .email(this.userCreate.getEmail())
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createOrUpdateOkWhenBirthdayNullTest() {
        final User userTest = this.userCreate.toBuilder()
                .birthday(null)
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(0, violations.size());
    }

    @Test
    protected void createOrUpdateOkWhenBirthdayInPresentTest() {
        final User userTest = this.userCreate.toBuilder()
                .birthday(LocalDate.now())
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(1, violations.size());
    }

    @Test
    protected void createOrUpdateFailedWhenBirthdayInFutureTest() {
        final User userTest = this.userCreate.toBuilder()
                .birthday(LocalDate.now().plusDays(1))
                .build();
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                userTest));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void checkUpdateFailedFieldNameEmptyTest() {
        User userTest = this.userUpdate.toBuilder().build();
        User newUserTest = this.userUpdate.toBuilder()
                .name("")
                .build();
        final User updatedUser = User.buildNewUser(userTest, newUserTest);
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                updatedUser));
        assertEquals(1, violations.size());
        final ExpectedViolation expectedViolation = new ExpectedViolation(
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
    protected void checkUpdateOkFieldNameNotEmptyTest() {
        User userTest = this.userUpdate.toBuilder().build();
        User newUserTest = this.userUpdate.toBuilder()
                .name("fgtrytr")
                .build();
        final User updatedUser = User.buildNewUser(userTest, newUserTest);
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                updatedUser));
        assertEquals(0, violations.size());
        assertEquals(userTest.getId(), updatedUser.getId());
        assertEquals(userTest.getBirthday(), updatedUser.getBirthday());
        assertEquals(userTest.getEmail(), updatedUser.getEmail());
        assertEquals(userTest.getLogin(), updatedUser.getLogin());
        assertEquals("fgtrytr", updatedUser.getName());
    }

    @Test
    protected void checkUpdateOkFieldsNotEmptyTest() {
        User userTest = this.userUpdate.toBuilder().build();
        User newUserTest = User.builder()
                .id(userTest.getId())
                .name("sdfsdfsdf")
                .email("saddasd@mail.ru")
                .login("sdsds")
                .birthday(LocalDate.now().minusYears(10))
                .build();
        final User updatedUser = User.buildNewUser(userTest, newUserTest);
        final List<ConstraintViolation<User>> violations = new ArrayList<>(this.validator.validate(
                updatedUser));
        assertEquals(0, violations.size());
        assertEquals(newUserTest.getId(), updatedUser.getId());
        assertEquals(newUserTest.getBirthday(), updatedUser.getBirthday());
        assertEquals(newUserTest.getEmail(), updatedUser.getEmail());
        assertEquals(newUserTest.getLogin(), updatedUser.getLogin());
        assertEquals(newUserTest.getName(), updatedUser.getName());
    }
}
