package ru.melonhell.violenceuniverse.common.packets.clientbound;

import ru.melonhell.violenceuniverse.common.packets.ClientboundPacket;

public record ClientboundRoundStartPacket(int roundNumber) implements ClientboundPacket {
}
