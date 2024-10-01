package ru.yandex.practicum.filmorate.storage.relations.interfaces;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.List;

public interface RelationStorage<T extends StorageData> {
    List<T> getRelations(Long number);
}
