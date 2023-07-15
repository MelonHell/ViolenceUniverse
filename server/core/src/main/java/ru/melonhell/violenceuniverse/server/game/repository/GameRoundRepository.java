package ru.melonhell.violenceuniverse.server.game.repository;

import ru.melonhell.violenceuniverse.server.game.entity.GameRoundEntity;

import java.util.List;

public interface GameRoundRepository {
    GameRoundEntity create(GameRoundEntity entity);

    List<GameRoundEntity> findRoundsByGame(String gameId);
}
