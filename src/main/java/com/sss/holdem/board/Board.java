package com.sss.holdem.board;

import com.sss.holdem.card.Card;
import com.sss.holdem.rules.Rule;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Board {
    private final Rule rule;
    private final List<Card> boardCards;

    public Board(final Rule rule, final List<Card> boardCards) {
        this.rule = rule;
        this.boardCards = List.copyOf(boardCards);
    }

    public boolean isValid() {
        return boardCards.size() == rule.getCountOfBoardsCars();
    }
}
