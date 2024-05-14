package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Create;
import ru.yandex.practicum.filmorate.model.StorageData;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage extends StorageData implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long idGenerator = 0L;

    @Override
    public User create(User user) {
        // проверяем выполнение необходимых условий
        if (users.values().stream().anyMatch(user1 -> user1.getEmail().equals(user.getEmail()))) {

            throw new DuplicatedDataException("Имейл " + user.getEmail() + " уже используется");
        }
        if (users.values().stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin()))) {
            throw new DuplicatedDataException("Логин " + user.getLogin() + " уже используется");
        }
        // формируем дополнительные данные
        user.setId(++idGenerator);
        // сохраняем нового пользователя в памяти приложения
        users.put(user.getId(), user);
        log.info("Новый пользователь с id = " + user.getId() + " создан");
        return user;
    }

    @Override
    public User update(User newUser) {
        // проверяем необходимые условия
        Long newUserId = newUser.getId();
        if (users.containsKey(newUserId)) {
            User oldUser = users.get(newUserId);
/*            if (users.values().stream().anyMatch(user1 -> user1.getEmail().equals(newUser.getEmail()) && !user1.getId().equals(newUserId))) {
                throw new DuplicatedDataException("Имейл " + newUser.getEmail() + " уже используется");
            } todo дописать тесты на проверку*/
/*            for ( User user : users.values()) {
                if (user.getEmail().equals(newUser.getEmail()) && !user.getId().equals(newUserId)) {
                    throw new DuplicatedDataException("Имейл " + newUser.getEmail() + " уже используется");
                }
                if (user.getLogin().equals(newUser.getLogin()) && !user.getId().equals(newUserId)) {
                    throw new DuplicatedDataException("Логин " + newUser.getLogin() + " уже используется");
                }
            }todo*/
/*            if (users.values().stream().anyMatch(user1 -> user1.getLogin().equals(newUser.getLogin()) && !user1.getId().equals(newUserId))) {
                throw new DuplicatedDataException("Логин " + newUser.getLogin() + " уже используется");
            }todo*/
            User user = User.buildNewUser(oldUser, newUser);
            users.put(newUserId, user);
            log.info("Пользователь с id = " + newUserId + " обновлен");
            return user;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    @Override
    public User get(long id) {
        final User user = users.get(id);
        if (user == null) {
            throw new NotFoundException("user with id=" + id + " does not exist");
        }
        return user;
    }

    @Override
    public void delete(long id) {
        users.remove(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }
}
