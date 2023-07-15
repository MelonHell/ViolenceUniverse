package ru.melonhell.violenceuniverse.server.user.exceptions;

import ru.melonhell.violenceuniverse.server.exceptions.ViolenceUniverseException;

public class InvalidLoginException extends ViolenceUniverseException {
    public InvalidLoginException(){
        super("Некорректный логин или пароль");
    }
}
