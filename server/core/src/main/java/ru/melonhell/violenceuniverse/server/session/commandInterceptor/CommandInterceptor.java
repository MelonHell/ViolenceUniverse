package ru.melonhell.violenceuniverse.server.session.commandInterceptor;

import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;
import ru.melonhell.violenceuniverse.server.session.Session;

public interface CommandInterceptor {
    default int priority() {
        return 0;
    }

    /**
     * @return Стоит ли продолжать выполнение
     */
    boolean onPacket(Session session, ServerboundPacket packet);
}
