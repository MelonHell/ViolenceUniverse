package ru.melonhell.violenceuniverse.server.game.exceptions;

import ru.melonhell.violenceuniverse.server.exceptions.ViolenceUniverseException;

public class ItemNotFoundException extends ViolenceUniverseException {
    public ItemNotFoundException() {
        super("Предмет не найден");
    }
}
