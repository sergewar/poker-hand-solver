package com.sss.holdem.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CardSuit {
    SUIT_C('c'),
    SUIT_D('d'),
    SUIT_H('h'),
    SUIT_S('s');

    private final char suit;

    public static CardSuit getCardSuitBySuit(final char suit) {
        return Stream.of(CardSuit.class.getEnumConstants())
                .filter(s -> s.getSuit() == suit)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not parse card suit from suit='" + suit + "'"));
    }
}
