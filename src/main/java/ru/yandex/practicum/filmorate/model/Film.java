package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.IllegalAccessToModelException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Film.
 * Свойства model.Film:
 * целочисленный идентификатор — id;
 * название — name;
 * описание — description;
 * дата релиза — releaseDate;
 * продолжительность фильма — duration.
 */
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Film extends StorageData {
    /*
        @Null(groups = Create.class, message = "Id при создании должен быть пустым")
        @NotNull(groups = Update.class, message = "Id при обновлении не должен быть пустым")
        @Positive(message = "Id должен быть положительным целым числом")
        private Long id; todo
    */
    @NotBlank(groups = Create.class, message = "Название не должно быть null или состоять из пробелов")
    @Pattern(regexp = ".*\\S+.*", message = "Название не может состоять из пробелов")
    private String name;

    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;

    @ReleaseDateConstraint
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Long duration;

    /*Копируем в новый объект filmBuilder сначала поля oldFilm(тот, которого хотим обновить), затем добавляем только
    обновленную информацию из newFilm*/
    public static Film buildNewFilm(Film oldFilm, Film newFilm) {
        if (!oldFilm.getId().equals(newFilm.getId())) {
            throw new NotFoundException("Id фильмов не совпали");
        }
        FilmBuilder filmBuilder = oldFilm.toBuilder();
        // Получаем суперкласс билдера со всеми полями
        Class classWithDeclaredFields  = filmBuilder.getClass().getSuperclass();
        List<Field> fieldsOfBuilderFromFilm = new ArrayList<>(List.of(classWithDeclaredFields.getDeclaredFields()));
        List<Field> fieldsOfBuilderFromStorageData = List.of(classWithDeclaredFields.getSuperclass().getDeclaredFields());

        List<Field> fieldsOfFilm = List.of(newFilm.getClass().getDeclaredFields());
        fieldsOfBuilderFromFilm.addAll(fieldsOfBuilderFromStorageData);
        List<Field> fieldsOfBuilder = new ArrayList<>(fieldsOfBuilderFromFilm);

        for (Field field : fieldsOfFilm) {
            for (Field field1 : fieldsOfBuilder) {
                if (field1.getName().equals(field.getName())) {
                    try {
                        if (field.get(newFilm) != null) {
                            field1.set(filmBuilder, field.get(newFilm));
                        }
                    } catch (IllegalAccessException e) {
                        throw new IllegalAccessToModelException("Ошибка при обновлении данных фильма");
                    }
                    break;
                }
            }
        }
        return filmBuilder.build();
    }
}
