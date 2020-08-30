package com.sss.holdem.player;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardRank;
import com.sss.holdem.card.CardSuit;
import com.sss.holdem.rules.Rule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {

    @Test
    void playerWithoutCardsIsntValid() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        assertFalse(new Player(rule, List.of()).isValid());
    }

    @Test
    void playerWithOneCardIsntValid() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        assertFalse(new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c'))))
                .isValid());
    }

    @Test
    void playerWithFourCardsWithoutOmahaRuleIsntValid() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        assertFalse(new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
                )).isValid()
        );
    }

    @Test
    void playerWithTwoCardsWithOmahaRuleIsntValid() {
        final boolean isOmahaRule = true;
        final Rule rule = new Rule(isOmahaRule);
        assertFalse(
                new Player(rule, List.of(
                        new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                        new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
                )).isValid()
        );
    }

    @Test
    void notPossibleUpdatePlayerCards() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        final Card firstCard = new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c'));
        final Player player = new Player(rule, List.of(
                firstCard,
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s'))
        ));

        assertAll(
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> player.getCards().add(new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c')))),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> player.getCards().remove(firstCard)),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> player.getCards().set(0, new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c'))))
        );
    }

}