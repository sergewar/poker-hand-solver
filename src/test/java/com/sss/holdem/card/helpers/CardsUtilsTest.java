package com.sss.holdem.card.helpers;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardRank;
import com.sss.holdem.card.CardSuit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.sss.holdem.card.CardRank.CARD_2;
import static com.sss.holdem.card.CardRank.CARD_A;
import static com.sss.holdem.card.CardRank.CARD_K;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardsUtilsTest {

    @Test
    void cardsToString() {
        final List<Card> listOfCards = List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('Q'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('8'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('3'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('T'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('2'), CardSuit.getCardSuitBySuit('h')));
        assertEquals("AcQhKh8h3hJsTd2h", new CardsUtils().cardsToString(listOfCards));
    }

    @Test
    void dupeCards() {
        final List<Card> listWithDupeCards = List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('2'), CardSuit.getCardSuitBySuit('h')));
        assertEquals(
                List.of(new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                        new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('h'))),
                new CardsUtils().dupeCards(listWithDupeCards),
                "Not all dupe cards");
    }

    @Test
    void countOfCardsByRank() {
        final List<Card> cards = List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('2'), CardSuit.getCardSuitBySuit('h')));
        assertAll(
                () -> assertEquals(4, new CardsUtils().countOfCardsWithRank(cards, CARD_A)),
                () -> assertEquals(3, new CardsUtils().countOfCardsWithRank(cards, CARD_K)),
                () -> assertEquals(1, new CardsUtils().countOfCardsWithRank(cards, CARD_2))
        );
    }
}