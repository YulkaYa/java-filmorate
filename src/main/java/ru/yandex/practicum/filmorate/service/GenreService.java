package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.base.interfaces.GenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenreService {
    private final GenreStorage genreStorage;

    public Genre get(final Long id) {
        return this.genreStorage.get(id);
    }

    public List<Genre> getAll() {
        return this.genreStorage.getAll();
    }
}
