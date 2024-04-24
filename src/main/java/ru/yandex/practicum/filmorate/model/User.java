package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.lang.reflect.Field;
import java.time.LocalDate;
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
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class User {
    @Null(groups = Create.class, message = "Id при создании пользователя должен быть пустым")
    @NotNull(groups = Update.class, message = "Id при обновлении не должен быть пустым")
    @Positive(message = "Id должен быть положительным целым числом")
    private Long id;

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

    public static User buildNewUser(User oldUser, User newUser) throws IllegalAccessException {
        if (!oldUser.getId().equals(newUser.getId())) {
            throw new NotFoundException("Id пользователей не совпали");
        }
        UserBuilder userBuilder = oldUser.toBuilder();
        List<Field> fieldsOfUser = List.of(newUser.getClass().getDeclaredFields());
        List<Field> fieldsOfBuilder = List.of(userBuilder.getClass().getDeclaredFields());
        for (Field field : fieldsOfUser) {
            for (Field field1 : fieldsOfBuilder) {
                if (field1.getName().equals(field.getName())) {
                    if (!(field.get(newUser) == null)) {
                        field1.set(userBuilder, field.get(newUser));
                    }
                    break;
                }
            }
        }
        return userBuilder.build();
    }
}