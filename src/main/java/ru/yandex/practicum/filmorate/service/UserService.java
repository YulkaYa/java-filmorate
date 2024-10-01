package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.base.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.storage.relations.interfaces.FriendshipStorage;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    public User create(final User user) {
        this.userStorage.create(user);
        return user;
    }

    public User get(final Long id) {
        return this.userStorage.get(id);
    }

    public User update(final User user) {
        return this.userStorage.update(user);
    }

    public List<User> getAll() {
        return this.userStorage.getAll();
    }

    public List<User> getFriends(final Long id) {
        return this.friendshipStorage.getRelations(id);
    }

    public void addFriend(final Long id, final Long friendId) {
        this.friendshipStorage.addRelation(id, friendId);
    }

    public void deleteFromFriends(final Long id, final Long friendId) {
        this.friendshipStorage.deleteRelation(id, friendId);
    }

    public List<User> getCommonFriends(final Long id, final Long otherId) {
        return this.friendshipStorage.getCommonFriends(id, otherId);
    }
}
