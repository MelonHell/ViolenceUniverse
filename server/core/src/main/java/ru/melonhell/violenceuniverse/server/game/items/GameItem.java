package ru.melonhell.violenceuniverse.server.game.items;

import ru.melonhell.violenceuniverse.common.enums.ItemType;

public interface GameItem {
    boolean beat(GameItem anotherItem);

    ItemType type();
}
