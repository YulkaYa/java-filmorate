package ru.yandex.practicum.filmorate.storage.base.interfaces;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BaseStorage<T extends StorageData> {
    T get(long id);

    List<T> getAll();

    T makeObject(ResultSet rs, int rowNum) throws SQLException;
}
