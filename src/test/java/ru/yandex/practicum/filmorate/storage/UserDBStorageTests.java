package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = UserDbStorage.class)
class UserDBStorageTests {
    private final UserDbStorage userStorage;
    private final String SQL_SCRIPT_ONE_USER_IN_DB = "/tests/users/user.sql";
    private final String SQL_SCRIPT_SOME_USERS_IN_DB = "/tests/users/some-users.sql";

    @Test
    @Sql(scripts = SQL_SCRIPT_ONE_USER_IN_DB)
    protected void createUser() {
        // Имеем 1 юзер в БД
        userStorage.create(
                User.builder()
                        .id(1L)
                        .name("user1")
                        .login("userLogin")
                        .email("email@mail.ru")
                        .birthday(LocalDate.of(1985, 12, 28))
                        .build());
        final List<User> users = userStorage.getAll();
        assertEquals(2, users.size());
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("login", "userLogin");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("name", "user1");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("birthday", LocalDate.of(1985, 12, 28));
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("email", "email@mail.ru");
    }


    @Test
    @Sql(scripts = SQL_SCRIPT_SOME_USERS_IN_DB)
    protected void updateUser() {
        // Имеем 2 юзера в БД
        final User userUpdated = User.builder()
                .id(1L)
                .name("userUpdated")
                .login("userUpdatedLogin")
                .email("emailUpdated@mail.ru")
                .birthday(LocalDate.of(1986, 12, 28))
                .build();
        userStorage.update(userUpdated);
        final List<User> users = userStorage.getAll();
        assertEquals(2, users.size());
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("login", "userUpdatedLogin");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("name", "userUpdated");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("birthday", LocalDate.of(1986, 12, 28));
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("email", "emailUpdated@mail.ru");
    }

    @Test
    @Sql(scripts = SQL_SCRIPT_ONE_USER_IN_DB)
    protected void get() {
        final User user = userStorage.get(0L);
        assertThat(user).hasFieldOrPropertyWithValue("login", "testLogin");
        assertThat(user).hasFieldOrPropertyWithValue("name", "nameOfZeroUser");
        assertThat(user).hasFieldOrPropertyWithValue("birthday", LocalDate.of(1988, 12, 28));
        assertThat(user).hasFieldOrPropertyWithValue("email", "yulkaTEST@ya.ru");
    }

    @Test
    @Sql(scripts = SQL_SCRIPT_SOME_USERS_IN_DB)
    protected void delete() {
        // Имеем 2 юзера в БД
        assertEquals(2, userStorage.getAll().size());

        // Удаляем 1 из них
        userStorage.delete(1L);

        // Проверяем что остался 1 юзер 
        assertEquals(1, userStorage.getAll().size());
    }

    @Test
    @Sql(scripts = SQL_SCRIPT_SOME_USERS_IN_DB)
    protected void getAll() {
        final List<User> users = userStorage.getAll();
        assertEquals(2, users.size());
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("id", 0L);
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("login", "testLogin");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("name", "nameOfZeroUser");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("birthday", LocalDate.of(1988, 12, 28));
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("email", "yulkaTEST@ya.ru");

        assertThat(users.get(1)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("login", "testLoginOne");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("name", "nameOfFirstUser");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("birthday", LocalDate.of(1989, 12, 28));
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("email", "yulkaTESTOne@ya.ru");


    }
}