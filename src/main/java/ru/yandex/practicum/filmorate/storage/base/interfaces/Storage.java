package ru.yandex.practicum.filmorate.storage.base.interfaces;

import ru.yandex.practicum.filmorate.model.StorageData;

public interface Storage<T extends StorageData> extends BaseStorage<T> {
    T create(T data);

    T update(T data);

    void delete(long id);
}