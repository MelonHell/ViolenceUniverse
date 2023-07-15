package ru.melonhell.violenceuniverse.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum ItemType {
    ROCK("rock"),
    PAPER("paper"),
    SCISSORS("scissors");
    private final String code;
}
