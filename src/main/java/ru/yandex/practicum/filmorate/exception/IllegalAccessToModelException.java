package ru.yandex.practicum.filmorate.exception;

public class IllegalAccessToModelException extends RuntimeException {
    public IllegalAccessToModelException(String message) {
        super(message);
    }
}