package com.sss.holdem.card;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CardRank {
    CARD_2('2', 2),
    CARD_3('3', 3),
    CARD_4('4', 4),
    CARD_5('5', 5),
    CARD_6('6', 6),
    CARD_7('7', 7),
    CARD_8('8', 8),
    CARD_9('9', 8),
    CARD_T('T', 10),
    CARD_J('J', 11),
    CARD_Q('Q', 12),
    CARD_K('K', 13),
    CARD_A('A', 14);

    @Getter(AccessLevel.PACKAGE)
    private final char rank;
    private final int rankWeight;

    public static CardRank getCardRankByRank(final char rank) {
        return Stream.of(CardRank.class.getEnumConstants())
                .filter(s -> s.getRank() == rank)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not parse card rank from rank='" + rank + "'"));
    }
}
