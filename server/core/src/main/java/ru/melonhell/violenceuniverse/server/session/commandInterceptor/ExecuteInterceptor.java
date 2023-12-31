package ru.melonhell.violenceuniverse.server.session.commandInterceptor;

import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;
import ru.melonhell.violenceuniverse.server.session.Session;

@Component
public class ExecuteInterceptor implements CommandInterceptor {

    @Override
    public int priority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean onPacket(Session session, ServerboundPacket packet) {
        session.getStage().processPacket(packet, session);
        return false;
    }
}
