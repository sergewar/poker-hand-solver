package com.sss.holdem.card;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Card {
    private final CardRank cardRank;
    private final CardSuit cardSuit;

    @Override
    public String toString() {
        return "" + cardRank.getRank() + cardSuit.getSuit();
    }
}
