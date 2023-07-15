package ru.melonhell.violenceuniverse.server.user.exceptions;

import ru.melonhell.violenceuniverse.server.exceptions.ViolenceUniverseException;

public class BlankPasswordException extends ViolenceUniverseException {
    public BlankPasswordException() {
        super("Пароль не может быть пустым");
    }
}
