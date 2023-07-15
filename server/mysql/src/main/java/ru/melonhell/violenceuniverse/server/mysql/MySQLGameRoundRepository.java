package ru.melonhell.violenceuniverse.server.mysql;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.common.enums.GameResult;
import ru.melonhell.violenceuniverse.common.enums.ItemType;
import ru.melonhell.violenceuniverse.server.game.entity.GameRoundEntity;
import ru.melonhell.violenceuniverse.server.game.repository.GameRoundRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MySQLGameRoundRepository implements GameRoundRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public GameRoundEntity create(GameRoundEntity entity) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        ItemType botMove = entity.getBotMove();
        ItemType playerMove = entity.getPlayerMove();
        jdbcTemplate.update("INSERT INTO rounds (game_id, bot_move, player_move, result)\n" +
                        "VALUES (:game_id, :bot_move, :player_move, :result)",
                new MapSqlParameterSource()
                        .addValue("game_id", entity.getGameId())
                        .addValue("bot_move", botMove != null ? botMove.name() : null)
                        .addValue("player_move", playerMove != null ? playerMove.name() : null)
                        .addValue("result", entity.getResult().name()),
                holder
        );

        entity.setId(Objects.toString(holder.getKey().intValue()));

        return entity;
    }

    @Override
    public List<GameRoundEntity> findRoundsByGame(String gameId) {
        return jdbcTemplate.query("SELECT * FROM rounds where game_id = :id", Map.of("id", gameId), (row, num) -> GameRoundEntity.builder()
                .id(row.getString("id"))
                .gameId(gameId)
                .botMove(ItemType.valueOf(row.getString("bot_move")))
                .playerMove(ItemType.valueOf(row.getString("player_move")))
                .result(GameResult.valueOf(row.getString("result")))
                .build());
    }
}
