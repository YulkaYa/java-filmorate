package ru.yandex.practicum.filmorate.storage.relations.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikesStorage extends ModificationOfRelationStorage {
    List<Film> topFilms(Long count);
}
