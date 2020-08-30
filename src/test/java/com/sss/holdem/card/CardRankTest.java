package com.sss.holdem.card;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.sss.holdem.card.CardRank.CARD_2;
import static com.sss.holdem.card.CardRank.CARD_3;
import static com.sss.holdem.card.CardRank.CARD_4;
import static com.sss.holdem.card.CardRank.CARD_5;
import static com.sss.holdem.card.CardRank.CARD_6;
import static com.sss.holdem.card.CardRank.CARD_7;
import static com.sss.holdem.card.CardRank.CARD_8;
import static com.sss.holdem.card.CardRank.CARD_9;
import static com.sss.holdem.card.CardRank.CARD_A;
import static com.sss.holdem.card.CardRank.CARD_J;
import static com.sss.holdem.card.CardRank.CARD_K;
import static com.sss.holdem.card.CardRank.CARD_Q;
import static com.sss.holdem.card.CardRank.CARD_T;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardRankTest {

    private static Object[][] testDataPositiveCases() {
        return new Object[][]{
                {'2', CARD_2},
                {'3', CARD_3},
                {'4', CARD_4},
                {'5', CARD_5},
                {'6', CARD_6},
                {'7', CARD_7},
                {'8', CARD_8},
                {'9', CARD_9},
                {'T', CARD_T},
                {'J', CARD_J},
                {'Q', CARD_Q},
                {'K', CARD_K},
                {'A', CARD_A}
        };
    }

    private static Object[][] testDataNegativeCases() {
        return new Object[][]{
                {'1'},
                {'0'},
                {'t'},
                {'j'},
                {'q'},
                {'k'},
                {'a'},
                {'^'},
                {'-'},
                {'c'},
                {'d'},
                {'h'},
                {'s'},
                {'C'},
                {'D'},
                {'H'},
                {'S'},
                {'\u001a'}
        };
    }

    @ParameterizedTest
    @MethodSource("testDataPositiveCases")
    void getCardRankByRank_PositiveCases(final char rank, final CardRank expectedRank) {
        assertEquals(expectedRank, CardRank.getCardRankByRank(rank));
    }

    @ParameterizedTest
    @MethodSource("testDataNegativeCases")
    void getCardRankByRank_NegativeCases(final char rank) {
        assertThrows(IllegalArgumentException.class, () -> CardRank.getCardRankByRank(rank));
    }
}
