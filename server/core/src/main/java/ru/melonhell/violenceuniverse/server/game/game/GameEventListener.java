package ru.melonhell.violenceuniverse.server.game.game;

import ru.melonhell.violenceuniverse.common.enums.GameResult;
import ru.melonhell.violenceuniverse.server.game.entity.GameEntity;
import ru.melonhell.violenceuniverse.server.game.entity.GameRoundEntity;
import ru.melonhell.violenceuniverse.server.game.items.GameItem;

import java.util.List;

public interface GameEventListener {
    void roundStart(int roundNumber);

    /**
     * До конца хода осталось N секунд
     */
    void moveWarning(int leftSeconds);

    /**
     * Игрок пропустил ход и проиграл
     */
    void userSkipMove();

    /**
     * Раунд закончился
     */
    void roundEnd(GameItem userItem, GameItem botItem, GameResult result);

    /**
     * Игра закончилась
     */
    void gameEnd(GameEntity game, List<GameRoundEntity> rounds, GameResult result);
}
