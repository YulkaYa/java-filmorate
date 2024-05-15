package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class InMemoryUserStorageTest {

    private final User userCreate = User.builder()
            .name("Name")
            .login("Login")
            .email("mail@gmail.com")
            .birthday(LocalDate.of(1995, 12, 28))
            .build();

    private final User userUpdate = User.builder()
            .name("NameUpdate")
            .login("LoginUpdate")
            .email("mailupdate@gmail.com")
            .birthday(LocalDate.of(1995, 12, 28))
            .build();

    @Test
    void checkUpdateOkEmailEqualAndIdEqualTest() {
        final InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        final User firstUser = userCreate.toBuilder().build();
        inMemoryUserStorage.create(firstUser);
        final User secondUser = userUpdate.toBuilder().build();
        inMemoryUserStorage.create(secondUser);
        final User updateUser = User.builder()
                .id(firstUser.getId())
                .email(firstUser.getEmail())
                .login("dfsdfds")
                .build();
        assertDoesNotThrow(() -> {
            inMemoryUserStorage.update(updateUser);
        }, "Неожиданное исключение в inMemoryUserStorage.update");
    }

    @Test
    void checkUpdateFailedLoginEqualAndIdEqualTest() {
        final InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        final User firstUser = userCreate.toBuilder().build();
        inMemoryUserStorage.create(firstUser);
        final User secondUser = userUpdate.toBuilder().build();
        inMemoryUserStorage.create(secondUser);
        final User updateUser = User.builder()
                .id(secondUser.getId())
                .email("update@mail.ru")
                .login(secondUser.getLogin())
                .build();
        assertDoesNotThrow(() -> {
            inMemoryUserStorage.update(updateUser);
        }, "Неожиданное исключение в inMemoryUserStorage.update");
    }

    @Test
    void checkUpdateFailedEmailEqualAndIdNotEqualTest() {
        final InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        final User firstUser = userCreate.toBuilder().build();
        inMemoryUserStorage.create(firstUser);
        final User secondUser = userUpdate.toBuilder().build();
        inMemoryUserStorage.create(secondUser);
        final User updateUser = User.builder()
                .id(secondUser.getId())
                .email(firstUser.getEmail())
                .login("dfsdfds")
                .build();
        assertThrows(DuplicatedDataException.class, () -> {
            inMemoryUserStorage.update(updateUser);
        }, "Должно быть исключение типа DuplicatedDataException");
    }

    @Test
    void checkUpdateFailedLoginEqualAndIdNotEqualTest() {
        final InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        final User firstUser = userCreate.toBuilder().build();
        inMemoryUserStorage.create(firstUser);
        final User secondUser = userUpdate.toBuilder().build();
        inMemoryUserStorage.create(secondUser);
        final User updateUser = User.builder()
                .id(secondUser.getId())
                .email("update@mail.ru")
                .login(firstUser.getLogin())
                .build();
        assertThrows(DuplicatedDataException.class, () -> {
            inMemoryUserStorage.update(updateUser);
        }, "Должно быть исключение типа DuplicatedDataException");
    }
}