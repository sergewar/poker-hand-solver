package com.sss.holdem.card;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.sss.holdem.card.CardSuit.SUIT_C;
import static com.sss.holdem.card.CardSuit.SUIT_D;
import static com.sss.holdem.card.CardSuit.SUIT_H;
import static com.sss.holdem.card.CardSuit.SUIT_S;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardSuitTest {

    private static Object[][] testDataPositiveCases() {
        return new Object[][]{
                {'c', SUIT_C},
                {'d', SUIT_D},
                {'h', SUIT_H},
                {'s', SUIT_S},
        };
    }

    private static Object[][] testDataNegativeCases() {
        return new Object[][]{
                {'C'},
                {'D'},
                {'H'},
                {'S'},
                {'4'},
                {'A'},
                {'^'},
                {'-'},
                {'\u001a'}
        };
    }

    @ParameterizedTest
    @MethodSource("testDataPositiveCases")
    void getCardSuitBySuit_PositiveCases(final char suit, final CardSuit expectedSuit) {
        assertEquals(expectedSuit, CardSuit.getCardSuitBySuit(suit));
    }


    @ParameterizedTest
    @MethodSource("testDataNegativeCases")
    void getCardSuitBySuit_NegativeCases(final char suit) {
        assertThrows(IllegalArgumentException.class, () -> CardSuit.getCardSuitBySuit(suit));
    }
}