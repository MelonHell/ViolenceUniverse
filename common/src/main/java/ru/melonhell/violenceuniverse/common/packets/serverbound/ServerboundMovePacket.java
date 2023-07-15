package ru.melonhell.violenceuniverse.common.packets.serverbound;

import ru.melonhell.violenceuniverse.common.enums.ItemType;
import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;

public record ServerboundMovePacket(ItemType move) implements ServerboundPacket {
}
