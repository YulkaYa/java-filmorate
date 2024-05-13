package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.IllegalAccessToModelException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

/*
    @Null(groups = Create.class, message = "Id при создании должен быть пустым")
    @NotNull(groups = Update.class, message = "Id при обновлении не должен быть пустым")
    @Positive(message = "Id должен быть положительным целым числом")
    private Long id; todo
*/

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

    private void setLogin(String login) {
        this.login = login;
        replaceBlankNameWithLogin();
    }

    private void setName(String name) {
        this.name = name;
        replaceBlankNameWithLogin();
    }

    private void replaceBlankNameWithLogin() {
        String nameOfUser = this.getName();
        if (nameOfUser == null || nameOfUser.isEmpty() || nameOfUser.isBlank()) {
            this.setName(this.getLogin());
        }
    }

    /*Копируем в новый объект userBuilder сначала поля oldUser(тот, которого хотим обновить), затем добавляем только
    обновленную информацию из newUser*/
    public static User buildNewUser(User oldUser, User newUser) {
        if (!oldUser.getId().equals(newUser.getId())) {
            throw new NotFoundException("Id пользователей не совпали");
        }
        UserBuilder userBuilder = oldUser.toBuilder();
        // Получаем суперкласс билдера со всеми полями
       Class classWithDeclaredFields  = userBuilder.getClass().getSuperclass();
       List<Field> fieldsOfBuilderFromUser = new ArrayList<>(List.of(classWithDeclaredFields.getDeclaredFields()));
       List<Field> fieldsOfBuilderFromStorageData = List.of(classWithDeclaredFields.getSuperclass().getDeclaredFields());


        List<Field> fieldsOfUser = List.of(newUser.getClass().getDeclaredFields());
        fieldsOfBuilderFromUser.addAll(fieldsOfBuilderFromStorageData);
        List<Field> fieldsOfBuilder = new ArrayList<>(fieldsOfBuilderFromUser);

        for (Field field : fieldsOfUser) {
            for (Field field1 : fieldsOfBuilder) {
                if (field1.getName().equals(field.getName())) {
                    try {
                        if (field.get(newUser) != null) {
                            field1.set(userBuilder, field.get(newUser));
                        }
                    } catch (IllegalAccessException e) {
                        throw new IllegalAccessToModelException("Ошибка при обновлении данных пользователя");
                    }
                    break;
                }
            }
        }
        return userBuilder.build();
    }
}