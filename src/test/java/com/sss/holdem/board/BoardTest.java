package com.sss.holdem.board;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardRank;
import com.sss.holdem.card.CardSuit;
import com.sss.holdem.rules.Rule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {

    @Test
    void boardWithoutCardsIsntValid() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        assertFalse(new Board(rule, List.of()).isValid()
        );
    }

    @Test
    void boardWithSixCardsIsntValid() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        assertFalse(
                new Board(rule, List.of(
                        new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                        new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                        new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                        new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                        new Card(CardRank.getCardRankByRank('Q'), CardSuit.getCardSuitBySuit('h')),
                        new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
                )).isValid()
        );
    }

    @Test
    void notPossibleUpdateBoardCards() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        final Card firstCard = new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c'));
        final Board board = new Board(rule, List.of(
                firstCard,
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        assertAll(
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> board.getBoardCards().add(new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c')))),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> board.getBoardCards().remove(firstCard)),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> board.getBoardCards().set(0, new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('d'))))
        );
    }

}