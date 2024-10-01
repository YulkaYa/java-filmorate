package ru.yandex.practicum.filmorate.storage.relations.interfaces;

import ru.yandex.practicum.filmorate.model.StorageData;

public interface ModificationOfRelationStorage<T extends StorageData> {
    void addRelation(Long idFirst, Long idSecond);

    void deleteRelation(Long idFirst, Long idSecond);
}
