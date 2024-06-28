package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipDao;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Service
public class UserService {
    private UserStorage userStorage;
    private FriendshipDao friendshipDao;

    @Autowired
    public UserService(UserStorage userStorage, FriendshipDao friendshipDao) {
        this.userStorage = userStorage;
        this.friendshipDao = friendshipDao;
    }

    public User create(User user) {
        userStorage.create(user);
        return user;
    }

    public User get(Long id) {
        return userStorage.get(id);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public List<User> getFriends(Long id) {
        return friendshipDao.getFriends(id);
    }

    public void addFriend(Long id, Long friendId) {
        friendshipDao.addFriend(id, friendId);
    }

    public void deleteFromFriends(Long id, Long friendId) {
        friendshipDao.deleteFromFriends(id, friendId);
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        return friendshipDao.getCommonFriends(id, otherId);
    }

}
