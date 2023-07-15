package ru.melonhell.violenceuniverse.server.user.exceptions;

import ru.melonhell.violenceuniverse.server.exceptions.ViolenceUniverseException;

public class UserAlreadyExistException extends ViolenceUniverseException {
    public UserAlreadyExistException() {
        super("Пользователь уже существует");
    }
}
