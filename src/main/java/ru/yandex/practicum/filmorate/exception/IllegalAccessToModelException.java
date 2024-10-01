package ru.yandex.practicum.filmorate.exception;

public class IllegalAccessToModelException extends RuntimeException {
    public IllegalAccessToModelException(final String message) {
        super(message);
    }
}