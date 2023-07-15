package ru.melonhell.violenceuniverse.server.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.server.game.entity.GameEntity;
import ru.melonhell.violenceuniverse.server.game.entity.GameRoundEntity;
import ru.melonhell.violenceuniverse.server.game.game.Game;
import ru.melonhell.violenceuniverse.server.game.repository.GameRepository;
import ru.melonhell.violenceuniverse.server.game.repository.GameRoundRepository;
import ru.melonhell.violenceuniverse.server.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

@Component
@RequiredArgsConstructor
public class GameService {
    private final ScheduledExecutorService executorService;


    private final ItemService itemService;
    private final GameRepository gameRepository;
    private final GameRoundRepository gameRoundRepository;

    public boolean hasUnfinishedGame(User user) {
        GameEntity entity = gameRepository.findNotFinishedGame(user.getId());

        return entity != null;
    }

    public Game createGame(User user) {
        GameEntity notFinishedGame = gameRepository.findNotFinishedGame(user.getId());
        if (notFinishedGame == null)
            return createNewGame(user);

        List<GameRoundEntity> rounds = gameRoundRepository.findRoundsByGame(notFinishedGame.getId());

        return createGame(user, notFinishedGame, rounds);
    }

    private Game createNewGame(User user) {
        GameEntity gameEntity = gameRepository.create(user.getId());

        return createGame(user, gameEntity, new ArrayList<>(3));
    }

    private Game createGame(User user, GameEntity entity, List<GameRoundEntity> rounds) {
        return new Game(itemService.all(), executorService, user, entity, rounds, gameRepository, gameRoundRepository);
    }

}
