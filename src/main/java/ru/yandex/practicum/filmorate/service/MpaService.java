package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.base.interfaces.MpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MpaService {
    private final MpaStorage mpaStorage;

    public Mpa get(final Long id) {
        return this.mpaStorage.get(id);
    }

    public List<Mpa> getAll() {
        return this.mpaStorage.getAll();
    }
}
