package ru.melonhell.violenceuniverse.server.session.stage;

import ru.melonhell.violenceuniverse.common.enums.StageType;
import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;
import ru.melonhell.violenceuniverse.common.packets.clientbound.ClientboundStageChangePacket;
import ru.melonhell.violenceuniverse.server.session.Session;

public interface Stage {
    /**
     * Сессия переключилась на этот контроллер
     */
    default void onEnter(Session session) {
        session.sendPacket(new ClientboundStageChangePacket(type()));
    }

    /**
     * Пользователь покинул контроллер
     */
    default void onLeave(Session session, LeaveReason reason) {
    }

    /**
     * Пришла команда от клиента
     */
    void processPacket(ServerboundPacket packet, Session session);
    StageType type();

}
