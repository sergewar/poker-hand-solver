package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.sss.holdem.card.CardRank.CARD_2;
import static com.sss.holdem.card.CardRank.CARD_3;
import static com.sss.holdem.card.CardRank.CARD_4;
import static com.sss.holdem.card.CardRank.CARD_5;
import static com.sss.holdem.card.CardRank.CARD_6;
import static com.sss.holdem.card.CardRank.CARD_7;
import static com.sss.holdem.card.CardRank.CARD_8;
import static com.sss.holdem.card.CardRank.CARD_9;
import static com.sss.holdem.card.CardRank.CARD_J;
import static com.sss.holdem.card.CardRank.CARD_T;
import static com.sss.holdem.card.CardSuit.SUIT_C;
import static com.sss.holdem.card.CardSuit.SUIT_D;
import static com.sss.holdem.card.CardSuit.SUIT_H;
import static com.sss.holdem.card.CardSuit.SUIT_S;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SomeKindOfCardsTest {

    private static Object[][] testData() {
        return new Object[][]{
                {List.of(new Card(CARD_T, SUIT_D),
                        new Card(CARD_T, SUIT_S),
                        new Card(CARD_9, SUIT_S),
                        new Card(CARD_9, SUIT_D),
                        new Card(CARD_J, SUIT_D),
                        new Card(CARD_9, SUIT_H),
                        new Card(CARD_9, SUIT_C),
                        new Card(CARD_3, SUIT_S),
                        new Card(CARD_3, SUIT_D),
                        new Card(CARD_J, SUIT_H),
                        new Card(CARD_3, SUIT_H),
                        new Card(CARD_3, SUIT_C),
                        new Card(CARD_4, SUIT_S),
                        new Card(CARD_4, SUIT_D),
                        new Card(CARD_J, SUIT_C)
                ),
                        List.of(new Card(CARD_9, SUIT_C),
                                new Card(CARD_9, SUIT_D),
                                new Card(CARD_9, SUIT_H),
                                new Card(CARD_9, SUIT_S)
                        ),
                        4
                },
                {List.of(new Card(CARD_2, SUIT_D),
                        new Card(CARD_3, SUIT_S),
                        new Card(CARD_4, SUIT_S),
                        new Card(CARD_5, SUIT_D),
                        new Card(CARD_6, SUIT_D),
                        new Card(CARD_7, SUIT_H),
                        new Card(CARD_8, SUIT_C),
                        new Card(CARD_9, SUIT_S),
                        new Card(CARD_T, SUIT_D)
                ),
                        List.of(),
                        3
                },
        };
    }

    @ParameterizedTest
    @MethodSource("testData")
    void getStrongestCardsWithTheSameKind(final List<Card> inputCards, final List<Card> outputExpectedCards, final int quantity) {
        final List<Card> result = new SomeKindOfCards().getStrongestCardsWithTheSameKind(inputCards, quantity);
        assertEquals(outputExpectedCards, result);
    }
}