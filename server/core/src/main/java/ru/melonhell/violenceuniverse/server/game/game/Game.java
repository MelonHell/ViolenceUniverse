package ru.melonhell.violenceuniverse.server.game.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.melonhell.violenceuniverse.server.game.entity.GameEntity;
import ru.melonhell.violenceuniverse.common.enums.GameResult;
import ru.melonhell.violenceuniverse.server.game.entity.GameRoundEntity;
import ru.melonhell.violenceuniverse.server.game.items.GameItem;
import ru.melonhell.violenceuniverse.server.game.repository.GameRepository;
import ru.melonhell.violenceuniverse.server.game.repository.GameRoundRepository;
import ru.melonhell.violenceuniverse.server.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class Game {
    private final Random random = new Random();
    private final List<GameItem> botItems;
    private final ScheduledExecutorService executor;
    @Getter
    private final User user;
    private final GameEntity game;
    private final List<GameRoundEntity> rounds;
    private final GameRepository gameRepository;
    private final GameRoundRepository gameRoundRepository;
    private boolean started = false;
    private final List<GameEventListener> listeners = new ArrayList<>();

    private final List<ScheduledFuture<?>> timers = new ArrayList<>();

    public void start() {
        if (started)
            return;
        started = true;
        startRound();
    }

    public void addGameListener(GameEventListener listener) {
        listeners.add(listener);
    }

    private void clearTimers() {
        for (ScheduledFuture<?> future : timers) {
            future.cancel(false);
        }
        timers.clear();
    }

    private void sendWarning(int left) {
        for (GameEventListener listener : listeners) {
            listener.moveWarning(left);
        }
    }

    private void createTimer(Runnable runnable, int delaySeconds) {
        ScheduledFuture<?> task = executor.schedule(runnable, delaySeconds, TimeUnit.SECONDS);
        timers.add(task);
    }

    private void createWarningTimer(int delay) {
        createTimer(() -> sendWarning(delay), 30 - delay);
    }

    public GameItem pickItem() {
        int index = random.nextInt(botItems.size());

        return botItems.get(index);
    }

    private void startRound() {
        clearTimers();
        for (GameEventListener listener : listeners) {
            listener.roundStart(rounds.size() + 1);
        }
        sendWarning(30);
        createWarningTimer(15);
        createWarningTimer(5);
        createWarningTimer(3);
        createWarningTimer(1);

        createTimer(this::skip, 30);
    }

    private GameRoundEntity createRoundEntity() {
        GameRoundEntity entity = new GameRoundEntity();
        entity.setGameId(game.getId());

        return entity;
    }

    private void skip() {
        clearTimers();
        GameRoundEntity entity = createRoundEntity();

        entity.setResult(GameResult.LOSE);

        for (GameEventListener listener : listeners) {
            listener.userSkipMove();
        }

        saveRoundAndGo(entity);
    }

    public void move(GameItem playerItem) {
        clearTimers();
        GameItem botItem = pickItem();

        GameRoundEntity entity = createRoundEntity();

        entity.setPlayerMove(playerItem.type());
        entity.setBotMove(botItem.type());

        if (botItem.beat(playerItem))
            entity.setResult(GameResult.LOSE);
        else if (playerItem.beat(botItem))
            entity.setResult(GameResult.WIN);
        else
            entity.setResult(GameResult.DRAW);

        for (GameEventListener listener : listeners) {
            listener.roundEnd(playerItem, botItem, entity.getResult());
        }

        saveRoundAndGo(entity);
    }

    private void saveRoundAndGo(GameRoundEntity entity) {
        entity = gameRoundRepository.create(entity);
        rounds.add(entity);
        if (rounds.size() >= 3)
            sendResults();
        else
            startRound();
    }

    private void sendResults() {
        int score = rounds
                .stream()
                .mapToInt(r -> {
                    if (r.getResult() == GameResult.WIN)
                        return 1;
                    else if (r.getResult() == GameResult.LOSE)
                        return -1;
                    else
                        return 0;
                })
                .sum();

        if (score > 0)
            game.setResult(GameResult.WIN);
        else if (score < 0)
            game.setResult(GameResult.LOSE);
        else
            game.setResult(GameResult.DRAW);

        gameRepository.setResult(game.getId(), game.getResult());

        for (GameEventListener listener : listeners) {
            listener.gameEnd(game, rounds, game.getResult());
        }
    }

    public void leave() {
        clearTimers();
    }
}
