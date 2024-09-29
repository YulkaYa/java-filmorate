package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.base.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.relations.FriendshipDbStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {FriendshipDbStorage.class, UserDbStorage.class})
class FriendshipDbStorageTest {
    private static final String SQL_SCRIPT_SOME_USERS_IN_DB = "/tests/friendship/some-users.sql";
    private final FriendshipDbStorage friendshipDbStorage;

    @Test
    @Sql(scripts = FriendshipDbStorageTest.SQL_SCRIPT_SOME_USERS_IN_DB)
    protected void addRelation() {
        this.friendshipDbStorage.addRelation(0L, 1L);
        this.friendshipDbStorage.addRelation(0L, 3L);
        List<User> friends = this.friendshipDbStorage.getRelations(0L);
        assertEquals(2, friends.size());
        friends = this.friendshipDbStorage.getRelations(1L);
        assertEquals(0, friends.size());
        friends = this.friendshipDbStorage.getRelations(2L);
        assertEquals(0, friends.size());
        friends = this.friendshipDbStorage.getRelations(3L);
        assertEquals(0, friends.size());
    }

    @Test
    @Sql(scripts = FriendshipDbStorageTest.SQL_SCRIPT_SOME_USERS_IN_DB)
    protected void getRelations() {
        this.friendshipDbStorage.addRelation(0L, 1L);
        this.friendshipDbStorage.addRelation(0L, 3L);
        final List<User> friends = this.friendshipDbStorage.getRelations(0L);
        assertEquals(2, friends.size());
        assertEquals(1L, friends.get(0).getId());
        assertEquals(3L, friends.get(1).getId());
    }

    @Test
    @Sql(scripts = FriendshipDbStorageTest.SQL_SCRIPT_SOME_USERS_IN_DB)
    protected void deleteFromFriends() {
        this.friendshipDbStorage.addRelation(0L, 1L);
        this.friendshipDbStorage.addRelation(0L, 3L);
        this.friendshipDbStorage.deleteRelation(0L, 1L);
        final List<User> friends = this.friendshipDbStorage.getRelations(0L);
        assertEquals(1, friends.size());
        assertEquals(3L, friends.get(0).getId());
    }

    @Test
    @Sql(scripts = FriendshipDbStorageTest.SQL_SCRIPT_SOME_USERS_IN_DB)
    protected void getCommonFriends() {
        this.friendshipDbStorage.addRelation(0L, 1L);
        this.friendshipDbStorage.addRelation(0L, 3L);
        this.friendshipDbStorage.addRelation(2L, 3L);

        List<User> friends = this.friendshipDbStorage.getRelations(0L);
        assertEquals(2, friends.size());
        assertEquals(1L, friends.get(0).getId());
        assertEquals(3L, friends.get(1).getId());

        friends = this.friendshipDbStorage.getRelations(1L);
        assertEquals(0, friends.size());

        friends = this.friendshipDbStorage.getRelations(2L);
        assertEquals(1, friends.size());
        assertEquals(3L, friends.get(0).getId());

        friends = this.friendshipDbStorage.getRelations(3L);
        assertEquals(0, friends.size());
    }
}