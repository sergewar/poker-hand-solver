package com.sss.holdem.card;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Collectors;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testCardsRatingRelations() {
        final boolean isCorrectOrder =
                CARD_2.getRankWeight() < CARD_3.getRankWeight()
                        && CARD_3.getRankWeight() < CARD_4.getRankWeight()
                        && CARD_4.getRankWeight() < CARD_5.getRankWeight()
                        && CARD_5.getRankWeight() < CARD_6.getRankWeight()
                        && CARD_6.getRankWeight() < CARD_7.getRankWeight()
                        && CARD_7.getRankWeight() < CARD_8.getRankWeight()
                        && CARD_8.getRankWeight() < CARD_9.getRankWeight()
                        && CARD_9.getRankWeight() < CARD_T.getRankWeight()
                        && CARD_T.getRankWeight() < CARD_J.getRankWeight()
                        && CARD_J.getRankWeight() < CARD_Q.getRankWeight()
                        && CARD_Q.getRankWeight() < CARD_K.getRankWeight()
                        && CARD_K.getRankWeight() < CARD_A.getRankWeight();
        assertTrue(isCorrectOrder, "Cards rank weight in incorrect order:\n"
                + Arrays.stream(CardRank.values())
                .map(cr -> "Card rank: " + cr + " card has rank weight: " + cr.getRankWeight())
        .collect(Collectors.joining("\n")));
    }
}
