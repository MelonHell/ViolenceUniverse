package ru.melonhell.violenceuniverse.server.stages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.server.session.stage.Stage;
import ru.melonhell.violenceuniverse.common.enums.StageType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StageService {
    private final List<Stage> stages;

    public Stage getByType(StageType type) {
        return stages
                .stream()
                .filter(s -> s.type() == type)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Не удалось найти этап с именем " + type));
    }
}
