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
    //todo private final JdbcTemplate jdbcTemplate;
    private Storage<User> userStorage;

    @Autowired
    public FriendshipDao(Storage<User> storage) {
        this.userStorage = storage;
    }

/*    public List<User> getFriends(Long id) {
*//* todo       String sqlQuery = """
                insert into users (
                     name, birthday, login, email
                ) 
                values (?, ?, ?, ?)
                """;
        userStorage.get(id);*//*
    }*/
}
