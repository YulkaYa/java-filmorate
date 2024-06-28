package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDao;

import java.util.List;

@Service
public class GenreService {
    private GenreDao genreDao;

    @Autowired
    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Genre get(int id) {
        return genreDao.get(id);
    }

    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
