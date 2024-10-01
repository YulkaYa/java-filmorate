package ru.yandex.practicum.filmorate.storage.relations.interfaces;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

public interface GenresFilmsStorage extends RelationStorage<Genre> {
    void addFilmGenre(Film film);
}
