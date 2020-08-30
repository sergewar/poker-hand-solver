package com.sss.holdem.player;

import com.sss.holdem.card.Card;
import com.sss.holdem.rules.Rule;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class Player {
    private final Rule rule;
    private final List<Card> cards;

    public Player(final Rule rule, final List<Card> cards) {
        this.rule = rule;
        this.cards = List.copyOf(cards);
    }

    public boolean isValid() {
        return cards.size() == rule.getCountOfPlayersCards();
    }
}
