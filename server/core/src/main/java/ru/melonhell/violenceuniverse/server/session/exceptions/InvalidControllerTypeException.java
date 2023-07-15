package ru.melonhell.violenceuniverse.server.session.exceptions;

import ru.melonhell.violenceuniverse.server.exceptions.ViolenceUniverseException;

public class InvalidControllerTypeException extends ViolenceUniverseException {
    public InvalidControllerTypeException() {
        super("Неизвестный тип контроллера");
    }
}
