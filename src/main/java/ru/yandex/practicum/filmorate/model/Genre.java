package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;


@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Genre extends StorageData {
    private String name;

    public Genre(final Long id, final String name) {
        super(id);
        this.name = name;
    }
}
