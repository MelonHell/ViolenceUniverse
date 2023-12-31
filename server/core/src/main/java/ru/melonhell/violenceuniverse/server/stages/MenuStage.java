package ru.melonhell.violenceuniverse.server.stages;

import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.common.enums.StageType;
import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;
import ru.melonhell.violenceuniverse.common.packets.serverbound.ServerboundStartPacket;
import ru.melonhell.violenceuniverse.server.exceptions.WrongPacketException;
import ru.melonhell.violenceuniverse.server.session.Session;
import ru.melonhell.violenceuniverse.server.session.stage.NoDataStage;

@Component
public class MenuStage extends NoDataStage {
    @Override
    public void processPacket(ServerboundPacket packet, Session session) {
        if (!(packet instanceof ServerboundStartPacket)) throw new WrongPacketException();

        session.setStage(StageType.GAME, null);
    }

    @Override
    public StageType type() {
        return StageType.MENU;
    }
}
