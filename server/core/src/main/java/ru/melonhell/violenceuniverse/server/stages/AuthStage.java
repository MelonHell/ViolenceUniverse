package ru.melonhell.violenceuniverse.server.stages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;
import ru.melonhell.violenceuniverse.common.packets.clientbound.ClientboundSignInSuccessPacket;
import ru.melonhell.violenceuniverse.common.packets.clientbound.ClientboundSignUpSuccessPacket;
import ru.melonhell.violenceuniverse.common.packets.serverbound.ServerboundSignInPacket;
import ru.melonhell.violenceuniverse.common.packets.serverbound.ServerboundSignUpPacket;
import ru.melonhell.violenceuniverse.server.exceptions.WrongPacketException;
import ru.melonhell.violenceuniverse.server.game.service.GameService;
import ru.melonhell.violenceuniverse.server.session.Session;
import ru.melonhell.violenceuniverse.common.enums.StageType;
import ru.melonhell.violenceuniverse.server.session.stage.Stage;
import ru.melonhell.violenceuniverse.server.user.entity.User;
import ru.melonhell.violenceuniverse.server.user.service.UserService;

@Component
@RequiredArgsConstructor
public class AuthStage implements Stage {
    private final UserService userService;
    private final GameService gameService;


    @Override
    public void processPacket(ServerboundPacket packet, Session session) {
        if (packet instanceof ServerboundSignUpPacket signUpPacket) {
            session.setDestroyMarker();
            userService.signUp(signUpPacket.login(), signUpPacket.password());
            session.sendPacket(new ClientboundSignUpSuccessPacket());
        } else if (packet instanceof ServerboundSignInPacket signInPacket) {
            User user = userService.signIn(signInPacket.login(), signInPacket.password());
            session.setUser(user);
            session.sendPacket(new ClientboundSignInSuccessPacket());
            if (gameService.hasUnfinishedGame(user))
                session.setStage(StageType.GAME);
            else
                session.setStage(StageType.MENU);
        } else {
            throw new WrongPacketException();
        }
    }

    @Override
    public StageType type() {
        return StageType.AUTH;
    }
}
