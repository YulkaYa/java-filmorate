package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class ExpectedViolation {
    protected String propertyPath;
    protected String message;
}