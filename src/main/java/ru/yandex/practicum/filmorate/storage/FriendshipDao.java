package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FriendshipDao {
    private final JdbcTemplate jdbcTemplate;
    private Storage<User> userStorage;

    @Autowired
    public FriendshipDao(Storage<User> storage, JdbcTemplate jdbcTemplate) {
        this.userStorage = storage;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(Long id, Long friendId) {
        userStorage.get(id);
        userStorage.get(friendId);
        final String sqlQuery = "insert into friendship values (?, ?)";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    public List<User> getFriends(Long id) {
        userStorage.get(id);
        final String sqlQuery = "select * from users as u join friendship as f on  u.user_id = f.friend_id where f.user_id = ?";
        final List<User> users = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, id);
        return users;
    }

    public void deleteFromFriends(Long id, Long friendId) {
        userStorage.get(id);
        userStorage.get(friendId);
        final String sqlQuery = "delete from friendship where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        final String sqlQuery = "select * from users where user_id in (select friend_id from friendship where user_id in (?, ?) GROUP BY friend_id having count (friend_id)>1)";
        final List<User> users = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, id, otherId);
        return users;
    }
}
