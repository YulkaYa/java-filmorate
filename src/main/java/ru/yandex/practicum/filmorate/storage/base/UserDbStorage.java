package ru.yandex.practicum.filmorate.storage.base;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.base.interfaces.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Primary
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User makeObject(final ResultSet rs, final int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .build();
    }

    @Override
    public User create(final User data) {
        final String sqlQuery = """
                insert into users (
                     name, birthday, login, email
                ) values (?, ?, ?, ?)
                """;

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            stmt.setString(1, data.getName());
            stmt.setDate(2, Date.valueOf(data.getBirthday()));
            stmt.setString(3, data.getLogin());
            stmt.setString(4, data.getEmail());
            return stmt;
        }, keyHolder);
        try {
            data.setId(keyHolder.getKey().longValue());
        } catch (final NullPointerException e) {
            throw new RuntimeException("Ошибка при добавлении id");
        }
        return data;
    }

    @Override
    public User update(final User data) {
        final User userFromBase = this.get(data.getId());
        final User updatedUser = User.buildNewUser(userFromBase, data);
        final String sqlQuery =
                "update users SET name = ?, birthday = ?, login = ?, email = ? WHERE user_id = ?";
        this.jdbcTemplate.update(
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
    public User get(final long id) {
        final String sqlQuery = "select * from users WHERE user_id = ?";
        List<User> users = this.jdbcTemplate.query(sqlQuery, this::makeObject, id);
        if (1 != users.size()) {
            throw new NotFoundException("user_id=" + id);
        }
        return users.get(0);
    }

    @Override
    public void delete(final long id) {
        final String sqlQuery = "delete from users where user_id = ?";
        this.jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users", this::makeObject);
    }
}
