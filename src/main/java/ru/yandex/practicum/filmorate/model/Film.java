package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
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

    @NotBlank(groups = Create.class, message = "Название не должно быть null или состоять из пробелов")
    @Pattern(regexp = ".*\\S+.*", message = "Название не может состоять из пробелов")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;
    @ReleaseDateConstraint
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Long duration;
    private Mpa mpa;
    @Singular
    private List<Genre> genres = new ArrayList<>();

    /*Копируем в новый объект filmBuilder сначала поля oldFilm(тот, которого хотим обновить), затем добавляем только
    обновленную информацию из newFilm*/
    public static Film buildNewFilm(final Film oldFilm, final Film newFilm) {
        if (!oldFilm.getId().equals(newFilm.getId())) {
            throw new NotFoundException("Id фильмов не совпали");
        }
        final FilmBuilder filmBuilder = oldFilm.toBuilder();
        // Получаем суперкласс билдера со всеми полями
        final Class classWithDeclaredFields = filmBuilder.getClass().getSuperclass();
        final List<Field> fieldsOfBuilderFromFilm = new ArrayList<>(List.of(classWithDeclaredFields.getDeclaredFields()));
        final List<Field> fieldsOfBuilderFromStorageData = List.of(classWithDeclaredFields.getSuperclass().getDeclaredFields());

        final List<Field> fieldsOfFilm = List.of(newFilm.getClass().getDeclaredFields());
        fieldsOfBuilderFromFilm.addAll(fieldsOfBuilderFromStorageData);
        final List<Field> fieldsOfBuilder = new ArrayList<>(fieldsOfBuilderFromFilm);

        for (final Field field : fieldsOfFilm) {
            for (final Field field1 : fieldsOfBuilder) {
                if (field1.getName().equals(field.getName())) {
                    try {
                        if (null != field.get(newFilm)) {
                            if (!("genres".equals(field.getName()) && "[]".equals(field.get(newFilm).toString()))) {
                                field1.set(filmBuilder, field.get(newFilm));
                            }
                        }
                    } catch (final IllegalAccessException e) {
                        throw new IllegalAccessToModelException("Ошибка при обновлении данных фильма");
                    }
                    break;
                }
            }
        }
        return filmBuilder.build();
    }
}
