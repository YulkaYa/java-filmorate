package ru.yandex.practicum.filmorate.storage.relations.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipStorage extends ModificationOfRelationStorage<User>, RelationStorage<User> {
    List<User> getCommonFriends(Long id, Long otherId);
}
