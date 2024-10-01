package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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
 * User.
 * Свойства model.User:
 * целочисленный идентификатор — id;
 * электронная почта — email;
 * логин пользователя — login;
 * имя для отображения — name;
 * дата рождения — birthday.
 */
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class User extends StorageData {

    @NotBlank(groups = Create.class, message = "Логин не может быть пустым")
    @Pattern(regexp = "^\\S+$", message = "Логин не может содержать пробелы или быть пустым")
    private String login;
    @Pattern(regexp = ".*\\S+.*", message = "Имя не может состоять из пробелов или быть пустым")
    private String name;
    @NotBlank(groups = Create.class, message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна содержать символ @ и соответствовать правилам названия email")
    private String email;
    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    /*Копируем в новый объект userBuilder сначала поля oldUser(тот, которого хотим обновить), затем добавляем только
    обновленную информацию из newUser*/
    public static User buildNewUser(final User oldUser, final User newUser) {
        if (!oldUser.getId().equals(newUser.getId())) {
            throw new NotFoundException("Id пользователей не совпали");
        }
        final UserBuilder userBuilder = oldUser.toBuilder();
        // Получаем суперкласс билдера со всеми полями
        final Class classWithDeclaredFields = userBuilder.getClass().getSuperclass();
        final List<Field> fieldsOfBuilderFromUser = new ArrayList<>(List.of(classWithDeclaredFields.getDeclaredFields()));
        final List<Field> fieldsOfBuilderFromStorageData = List.of(classWithDeclaredFields.getSuperclass().getDeclaredFields());


        final List<Field> fieldsOfUser = List.of(newUser.getClass().getDeclaredFields());
        fieldsOfBuilderFromUser.addAll(fieldsOfBuilderFromStorageData);
        final List<Field> fieldsOfBuilder = new ArrayList<>(fieldsOfBuilderFromUser);

        for (final Field field : fieldsOfUser) {
            for (final Field field1 : fieldsOfBuilder) {
                if (field1.getName().equals(field.getName())) {
                    try {
                        if (null != field.get(newUser)) {
                            field1.set(userBuilder, field.get(newUser));
                        }
                    } catch (final IllegalAccessException e) {
                        throw new IllegalAccessToModelException("Ошибка при обновлении данных пользователя");
                    }
                    break;
                }
            }
        }
        return userBuilder.build();
    }

    private void setLogin(final String login) {
        this.login = login;
        this.replaceBlankNameWithLogin();
    }

    private void setName(final String name) {
        this.name = name;
        this.replaceBlankNameWithLogin();
    }

    private void replaceBlankNameWithLogin() {
        final String nameOfUser = this.name;
        if (null == nameOfUser || nameOfUser.isEmpty() || nameOfUser.isBlank()) {
            this.setName(this.login);
        }
    }
}