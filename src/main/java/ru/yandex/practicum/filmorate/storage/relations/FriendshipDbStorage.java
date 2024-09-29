package ru.yandex.practicum.filmorate.storage.relations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.base.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.relations.interfaces.FriendshipStorage;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
@Primary
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userStorage;

    @Override
    public List<User> getCommonFriends(final Long id, final Long otherId) {
        final String sqlQuery = "select * from users where user_id in (select friend_id from friendship where user_id in (?, ?) GROUP BY friend_id having count (friend_id)>1)";
        return this.jdbcTemplate.query(sqlQuery, this.userStorage::makeObject, id, otherId);
    }

    @Override
    public void addRelation(final Long id, final Long friendId) {
        this.userStorage.get(id);
        this.userStorage.get(friendId);
        final String sqlQuery = "insert into friendship values (?, ?)";
        this.jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public void deleteRelation(final Long id, final Long friendId) {
        this.userStorage.get(id);
        this.userStorage.get(friendId);
        final String sqlQuery = "delete from friendship where user_id = ? and friend_id = ?";
        this.jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public List<User> getRelations(final Long id) {
        this.userStorage.get(id);
        final String sqlQuery = "select * from users as u join friendship as f on  u.user_id = f.friend_id where f.user_id = ?";
        return this.jdbcTemplate.query(sqlQuery, this.userStorage::makeObject, id);
    }
}
