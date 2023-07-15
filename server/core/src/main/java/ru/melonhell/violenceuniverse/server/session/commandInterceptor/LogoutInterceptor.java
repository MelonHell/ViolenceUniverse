package ru.melonhell.violenceuniverse.server.session.commandInterceptor;

import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;
import ru.melonhell.violenceuniverse.common.packets.serverbound.ServerboundLogOutPacket;
import ru.melonhell.violenceuniverse.server.session.Session;
import ru.melonhell.violenceuniverse.common.enums.StageType;
import ru.melonhell.violenceuniverse.server.stages.AuthStage;

@Component
public class LogoutInterceptor implements CommandInterceptor {
    @Override
    public boolean onPacket(Session session, ServerboundPacket packet) {
        if (!(packet instanceof ServerboundLogOutPacket) || session.getStage() instanceof AuthStage)
            return true;

        session.setUser(null);
        session.setStage(StageType.AUTH);
        return false;
    }
}
