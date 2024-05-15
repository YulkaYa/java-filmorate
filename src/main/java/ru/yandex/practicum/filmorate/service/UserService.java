package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    Storage<User> userStorage;

    @Autowired
    public UserService(Storage<User> storage) {
        this.userStorage = storage;
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
        Set<Long> friendsId = userStorage.get(id).getFriends();
        return userStorage.getAll().stream().filter(user -> friendsId.contains(user.getId())).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        final Set<Long> friendIds = userStorage.get(id).getFriends();
        final Set<Long> otherFriendIds = userStorage.get(otherId).getFriends();
        List<Long> commonFriendsIds = friendIds.stream().filter(idToFilter -> otherFriendIds.contains(idToFilter)).collect(Collectors.toList());
        return userStorage.getAll().stream().filter(user -> commonFriendsIds.contains(user.getId())).collect(Collectors.toList());
    }

    public void deleteFromFriends(Long id, Long friendId) {
        User firstUser = userStorage.get(id);
        User secondUser = userStorage.get(friendId);
        firstUser.getFriends().remove(friendId);
        secondUser.getFriends().remove(id);
    }

    public void addFriend(Long id, Long friendId) {
        userStorage.get(id).getFriends().add(friendId);
        userStorage.get(friendId).getFriends().add(id);
    }
}
