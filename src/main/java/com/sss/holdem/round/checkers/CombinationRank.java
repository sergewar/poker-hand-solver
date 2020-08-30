package com.sss.holdem.round.checkers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CombinationRank {
    HIGH_CARD(1),
    PAIR(2),
    TWO_PAIRS(4),
    THREE_OF_A_KIND(8),
    STRAIGHT(16),
    FLUSH(32),
    FULL_HOUSE(64),
    FOUR_OF_A_KIND(128),
    STRAIGHT_FLUSH(256);
//    ROYAL_FLUSH(512);

    private final int rank;
}
