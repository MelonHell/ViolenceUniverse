package ru.melonhell.violenceuniverse.common.packets.serverbound;

import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;

public record ServerboundSignInPacket(String login, String password) implements ServerboundPacket {
}
