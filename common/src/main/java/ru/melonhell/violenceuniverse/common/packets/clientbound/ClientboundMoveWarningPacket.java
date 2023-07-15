package ru.melonhell.violenceuniverse.common.packets.clientbound;

import ru.melonhell.violenceuniverse.common.packets.ClientboundPacket;

public record ClientboundMoveWarningPacket(int leftSeconds) implements ClientboundPacket {
}
