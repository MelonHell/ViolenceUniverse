package ru.melonhell.violenceuniverse.server.game.repository;

import org.jetbrains.annotations.Nullable;
import ru.melonhell.violenceuniverse.common.enums.GameResult;
import ru.melonhell.violenceuniverse.server.game.entity.GameEntity;

public interface GameRepository {
    @Nullable
    GameEntity findNotFinishedGame(String userId);

    GameEntity create(String userId);

    void setResult(String id, GameResult result);
}
