package ru.melonhell.violenceuniverse.common.packets.clientbound;

import ru.melonhell.violenceuniverse.common.enums.GameResult;
import ru.melonhell.violenceuniverse.common.packets.ClientboundPacket;

public record ClientboundGameEndPacket(GameResult result, int humanWins, int botWins) implements ClientboundPacket {
}
