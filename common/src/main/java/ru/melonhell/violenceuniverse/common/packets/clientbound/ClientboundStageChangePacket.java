package ru.melonhell.violenceuniverse.common.packets.clientbound;

import ru.melonhell.violenceuniverse.common.enums.StageType;
import ru.melonhell.violenceuniverse.common.packets.ClientboundPacket;

public record ClientboundStageChangePacket(StageType stage) implements ClientboundPacket {
}
