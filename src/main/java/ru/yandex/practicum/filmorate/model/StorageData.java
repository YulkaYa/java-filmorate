package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class StorageData {
    @Null(groups = Create.class, message = "Id при создании должен быть пустым")
    @NotNull(groups = Update.class, message = "Id при обновлении не должен быть пустым")
    @Positive(message = "Id должен быть положительным целым числом")
    private Long id;
}
