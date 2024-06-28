package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaDao;

import java.util.List;

@Service
public class MpaService {
    private MpaDao mpaDao;

    @Autowired
    public MpaService(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }

    public Mpa get(int id) {
        return mpaDao.get(id);
    }

    public List<Mpa> getAll() {
        return mpaDao.getAll();
    }
}
