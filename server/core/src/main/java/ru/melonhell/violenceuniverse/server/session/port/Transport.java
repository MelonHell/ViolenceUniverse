package ru.melonhell.violenceuniverse.server.session.port;

import ru.melonhell.violenceuniverse.common.packets.ClientboundPacket;
import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;

import java.util.function.Consumer;

public interface Transport {
    void sendPacket(ClientboundPacket command);

    void addCommandCallback(Consumer<ServerboundPacket> onCommand);
    void addDisconnectCallback(Runnable runnable);

    void disconnect();

    boolean isAlive();
}
