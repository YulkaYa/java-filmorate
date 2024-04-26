package ru.yandex.practicum.filmorate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class ExpectedViolation {
    protected String propertyPath;
    protected String message;
}