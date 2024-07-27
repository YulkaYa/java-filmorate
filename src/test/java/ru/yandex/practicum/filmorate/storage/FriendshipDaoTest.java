package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {FriendshipDao.class, UserDbStorage.class})
class FriendshipDaoTest {
    private final FriendshipDao friendshipDao;
    private static final String SQL_SCRIPT_SOME_USERS_IN_DB = "/tests/friendship/some-users.sql";

    @Test
    @Sql(scripts = {SQL_SCRIPT_SOME_USERS_IN_DB})
    protected void addFriend() {
        friendshipDao.addFriend(0L, 1L);
        friendshipDao.addFriend(0L, 3L);
        List<User> friends = friendshipDao.getFriends(0L);
        assertEquals(2, friends.size());
        friends = friendshipDao.getFriends(1L);
        assertEquals(0, friends.size());
        friends = friendshipDao.getFriends(2L);
        assertEquals(0, friends.size());
        friends = friendshipDao.getFriends(3L);
        assertEquals(0, friends.size());
    }

    @Test
    @Sql(scripts = {SQL_SCRIPT_SOME_USERS_IN_DB})
    protected void getFriends() {
        friendshipDao.addFriend(0L, 1L);
        friendshipDao.addFriend(0L, 3L);
        List<User> friends = friendshipDao.getFriends(0L);
        assertEquals(2, friends.size());
        assertEquals(1L, friends.get(0).getId());
        assertEquals(3L, friends.get(1).getId());
    }

    @Test
    @Sql(scripts = {SQL_SCRIPT_SOME_USERS_IN_DB})
    protected void deleteFromFriends() {
        friendshipDao.addFriend(0L, 1L);
        friendshipDao.addFriend(0L, 3L);
        friendshipDao.deleteFromFriends(0L, 1L);
        List<User> friends = friendshipDao.getFriends(0L);
        assertEquals(1, friends.size());
        assertEquals(3L, friends.get(0).getId());
    }

    @Test
    @Sql(scripts = {SQL_SCRIPT_SOME_USERS_IN_DB})
    protected void getCommonFriends() {
        friendshipDao.addFriend(0L, 1L);
        friendshipDao.addFriend(0L, 3L);
        friendshipDao.addFriend(2L, 3L);

        List<User> friends = friendshipDao.getFriends(0L);
        assertEquals(2, friends.size());
        assertEquals(1L, friends.get(0).getId());
        assertEquals(3L, friends.get(1).getId());

        friends = friendshipDao.getFriends(1L);
        assertEquals(0, friends.size());

        friends = friendshipDao.getFriends(2L);
        assertEquals(1, friends.size());
        assertEquals(3L, friends.get(0).getId());

        friends = friendshipDao.getFriends(3L);
        assertEquals(0, friends.size());
    }
}