package ru.melonhell.violenceuniverse.common.packets.clientbound;

import ru.melonhell.violenceuniverse.common.packets.ClientboundPacket;

public record ClientboundServerErrorPacket(String message) implements ClientboundPacket {
}
