package ru.melonhell.violenceuniverse.server.exceptions;

public class WrongPacketException extends ViolenceUniverseException {
    public WrongPacketException() {
        super("Отправлен неправильный пакет");
    }
}
