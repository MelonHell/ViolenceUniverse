package ru.melonhell.violenceuniverse.server.game.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.melonhell.violenceuniverse.common.enums.ItemType;
import ru.melonhell.violenceuniverse.server.game.exceptions.ItemNotFoundException;
import ru.melonhell.violenceuniverse.server.game.items.GameItem;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemService {
    private final List<GameItem> items;


    @NotNull
    public GameItem findItem(ItemType type) throws ItemNotFoundException {
        return items
                .stream()
                .filter(item -> item.type().equals(type))
                .findFirst()
                .orElseThrow(ItemNotFoundException::new);
    }

    public List<GameItem> all() {
        return items;
    }
}
