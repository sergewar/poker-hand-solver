package com.sss.holdem.card;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.sss.holdem.card.CardRank.CARD_2;
import static com.sss.holdem.card.CardRank.CARD_9;
import static com.sss.holdem.card.CardRank.CARD_A;
import static com.sss.holdem.card.CardRank.CARD_T;
import static com.sss.holdem.card.CardSuit.SUIT_C;
import static com.sss.holdem.card.CardSuit.SUIT_D;
import static com.sss.holdem.card.CardSuit.SUIT_H;
import static com.sss.holdem.card.CardSuit.SUIT_S;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CardTest {

    private static Object[][] testDataPositiveCases() {
        return new Object[][]{
                {"2c", new Card(CARD_2, SUIT_C)},
                {"Td", new Card(CARD_T, SUIT_D)},
                {"9h", new Card(CARD_9, SUIT_H)},
                {"As", new Card(CARD_A, SUIT_S)},
        };
    }

    private static Object[][] testDataNegativeCases() {
        return new Object[][]{
                {"1c", new Card(CARD_2, SUIT_C)},
                {"TD", new Card(CARD_T, SUIT_D)},
                {"8h", new Card(CARD_9, SUIT_H)},
                {"-s", new Card(CARD_A, SUIT_S)},
                {"-^", new Card(CARD_A, SUIT_S)},
                {"\u001a-s", new Card(CARD_A, SUIT_S)},
        };
    }

    @ParameterizedTest
    @MethodSource("testDataPositiveCases")
    void testToString_PositiveCases(final String text, final Card card) {
        assertEquals(text, card.toString());
    }


    @ParameterizedTest
    @MethodSource("testDataNegativeCases")
    void getCardSuitBySuit_NegativeCases(final String text, final Card card) {
        assertNotEquals(text, card.toString());
    }
}