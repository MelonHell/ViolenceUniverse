package ru.melonhell.violenceuniverse.server.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.server.session.commandInterceptor.CommandInterceptor;
import ru.melonhell.violenceuniverse.server.session.port.Transport;
import ru.melonhell.violenceuniverse.common.enums.StageType;
import ru.melonhell.violenceuniverse.server.stages.StageService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionCreator {
    private final List<CommandInterceptor> interceptors;
    private final StageService stageService;

    public Session createSession(Transport transport) {
        Session session = new Session(
                stageService,
                transport,
                stageService.getByType(StageType.AUTH)
        );

        for (CommandInterceptor interceptor : interceptors) {
            session.addGlobalInterceptor(interceptor);
        }

        return session;
    }
}
