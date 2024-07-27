package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Primary
@RequiredArgsConstructor
@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .build();
    }

    @Override
    public User create(User data) {
        final String sqlQuery = """
                insert into users (
                     name, birthday, login, email
                ) values (?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            stmt.setString(1, data.getName());
            stmt.setDate(2, Date.valueOf(data.getBirthday()));
            stmt.setString(3, data.getLogin());
            stmt.setString(4, data.getEmail());
            return stmt;
        }, keyHolder);
        try {
            data.setId(keyHolder.getKey().longValue());
        } catch (NullPointerException e) {
            throw new RuntimeException("Ошибка при добавлении id");
        }
        return data;
    }

    @Override
    public User update(User data) {
        User userFromBase = get(data.getId());
        User updatedUser = User.buildNewUser(userFromBase, data);
        final String sqlQuery =
                "update users SET name = ?, birthday = ?, login = ?, email = ? WHERE user_id = ?";
        jdbcTemplate.update(
                sqlQuery,
                updatedUser.getName(),
                updatedUser.getBirthday(),
                updatedUser.getLogin(),
                updatedUser.getEmail(),
                updatedUser.getId()
        );
        return updatedUser;
    }

    @Override
    public User get(long id) {
        final String sqlQuery = "select * from users WHERE user_id = ?";
        final List<User> users = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, id);
        if (1 != users.size()) {
            throw new NotFoundException("user_id=" + id);
        }
        return users.get(0);
    }

    @Override
    public void delete(long id) {
        final String sqlQuery = "delete from users where user_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users", UserDbStorage::makeUser);
    }
}
