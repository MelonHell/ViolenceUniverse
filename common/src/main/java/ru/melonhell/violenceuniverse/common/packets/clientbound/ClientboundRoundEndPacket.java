package ru.melonhell.violenceuniverse.common.packets.clientbound;

import ru.melonhell.violenceuniverse.common.enums.GameResult;
import ru.melonhell.violenceuniverse.common.enums.ItemType;
import ru.melonhell.violenceuniverse.common.packets.ClientboundPacket;

public record ClientboundRoundEndPacket(ItemType userItem, ItemType botItem, GameResult result) implements ClientboundPacket {
}
