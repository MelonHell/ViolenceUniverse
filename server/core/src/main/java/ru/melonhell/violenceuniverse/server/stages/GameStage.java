package ru.melonhell.violenceuniverse.server.stages;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.common.enums.GameResult;
import ru.melonhell.violenceuniverse.common.enums.StageType;
import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;
import ru.melonhell.violenceuniverse.common.packets.clientbound.*;
import ru.melonhell.violenceuniverse.common.packets.serverbound.ServerboundAskTimerPacket;
import ru.melonhell.violenceuniverse.common.packets.serverbound.ServerboundMovePacket;
import ru.melonhell.violenceuniverse.server.exceptions.WrongPacketException;
import ru.melonhell.violenceuniverse.server.game.entity.GameEntity;
import ru.melonhell.violenceuniverse.server.game.entity.GameRoundEntity;
import ru.melonhell.violenceuniverse.server.game.game.Game;
import ru.melonhell.violenceuniverse.server.game.game.GameEventListener;
import ru.melonhell.violenceuniverse.server.game.items.GameItem;
import ru.melonhell.violenceuniverse.server.game.service.GameService;
import ru.melonhell.violenceuniverse.server.game.service.ItemService;
import ru.melonhell.violenceuniverse.server.session.Session;
import ru.melonhell.violenceuniverse.server.session.stage.LeaveReason;
import ru.melonhell.violenceuniverse.server.session.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class GameStage implements Stage<@Nullable GameEntity> {
    private final ScheduledExecutorService scheduledExecutorService;
    private final GameService gameService;
    private final ItemService itemService;
    private final Map<Session, Game> activeGames = new HashMap<>();

    @Override
    public void onEnter(Session session, GameEntity gameEntity) {
        Stage.super.onEnter(session, gameEntity);
        Game game;
        if (gameEntity != null)
            game = gameService.createGame(session.getUser(), gameEntity);
        else
            game = gameService.createNewGame(session.getUser());

        activeGames.put(session, game);
        game.addGameListener(new StageListener(session));

        game.start();
    }

    @Override
    public void onLeave(Session session, LeaveReason reason) {
        Game game = activeGames.remove(session);
        if (game == null)
            return;

        game.leave();
    }

    @Override
    public void processPacket(ServerboundPacket packet, Session session) {
        Game game = activeGames.get(session);
        if (game == null) throw new RuntimeException("Игра не найдена");
        if (packet instanceof ServerboundMovePacket movePacket) {
            GameItem item = itemService.findItem(movePacket.move());
            game.move(item);
        } else if (packet instanceof ServerboundAskTimerPacket) {
            game.sendWarning();
        } else
            throw new WrongPacketException();
    }

    @Override
    public StageType type() {
        return StageType.GAME;
    }

    @RequiredArgsConstructor
    private class StageListener implements GameEventListener {
        private final Session session;

        @Override
        public void roundStart(int roundNumber) {
            session.sendPacket(new ClientboundRoundStartPacket(roundNumber));
        }

        @Override
        public void moveWarning(int leftSeconds) {
            session.sendPacket(new ClientboundMoveWarningPacket(leftSeconds));
        }

        @Override
        public void userSkipMove() {
            session.sendPacket(new ClientboundUserSkipMovePacket());
        }

        @Override
        public void roundEnd(GameItem userItem, GameItem botItem, GameResult result) {
            session.sendPacket(new ClientboundRoundEndPacket(userItem.type(), botItem.type(), result));
        }

        @Override
        public void gameEnd(GameEntity game, List<GameRoundEntity> rounds, GameResult result) {
            int humanWins = 0;
            int botWins = 0;
            for (GameRoundEntity round : rounds) {
                if (round.getResult() == GameResult.WIN)
                    humanWins++;
                else if (round.getResult() == GameResult.LOSE)
                    botWins++;
            }
            session.sendPacket(new ClientboundGameEndPacket(result, humanWins, botWins));
            scheduledExecutorService.schedule(() -> session.setStage(MenuStage.class), 5, TimeUnit.SECONDS);
        }
    }
}
