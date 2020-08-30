package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import io.vavr.control.Option;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.sss.holdem.card.CardRank.CARD_2;
import static com.sss.holdem.card.CardRank.CARD_3;
import static com.sss.holdem.card.CardRank.CARD_6;
import static com.sss.holdem.card.CardRank.CARD_7;
import static com.sss.holdem.card.CardRank.CARD_8;
import static com.sss.holdem.card.CardRank.CARD_9;
import static com.sss.holdem.card.CardRank.CARD_A;
import static com.sss.holdem.card.CardRank.CARD_J;
import static com.sss.holdem.card.CardRank.CARD_K;
import static com.sss.holdem.card.CardRank.CARD_Q;
import static com.sss.holdem.card.CardRank.CARD_T;
import static com.sss.holdem.card.CardSuit.SUIT_C;
import static com.sss.holdem.card.CardSuit.SUIT_D;
import static com.sss.holdem.card.CardSuit.SUIT_H;
import static com.sss.holdem.card.CardSuit.SUIT_S;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThreeOfAKindCombinationTest {

    private static Object[][] testDataPositiveCases() {
        return new Object[][]{
                {List.of(new Card(CARD_2, SUIT_C),
                        new Card(CARD_2, SUIT_D),
                        new Card(CARD_3, SUIT_H),
                        new Card(CARD_2, SUIT_S),
                        new Card(CARD_A, SUIT_C),
                        new Card(CARD_K, SUIT_D),
                        new Card(CARD_A, SUIT_H),
                        new Card(CARD_J, SUIT_H),
                        new Card(CARD_A, SUIT_S)
                ),
                        List.of(new Card(CARD_A, SUIT_C),
                                new Card(CARD_A, SUIT_H),
                                new Card(CARD_A, SUIT_S),
                                new Card(CARD_K, SUIT_D),
                                new Card(CARD_J, SUIT_H)
                        )},
        };
    }

    private static Object[][] testDataNegativeCases() {
        return new Object[][]{
                {List.of(new Card(CARD_2, SUIT_D),
                        new Card(CARD_T, SUIT_S),
                        new Card(CARD_A, SUIT_D),
                        new Card(CARD_9, SUIT_D),
                        new Card(CARD_J, SUIT_D),
                        new Card(CARD_9, SUIT_H),
                        new Card(CARD_T, SUIT_C)
                )},
                {List.of(new Card(CARD_2, SUIT_S),
                        new Card(CARD_3, SUIT_D),
                        new Card(CARD_A, SUIT_S),
                        new Card(CARD_9, SUIT_D),
                        new Card(CARD_J, SUIT_C),
                        new Card(CARD_8, SUIT_H),
                        new Card(CARD_9, SUIT_S),
                        new Card(CARD_T, SUIT_C)
                )},
                {List.of(new Card(CARD_2, SUIT_C),
                        new Card(CARD_T, SUIT_S),
                        new Card(CARD_A, SUIT_D),
                        new Card(CARD_9, SUIT_H),
                        new Card(CARD_3, SUIT_C),
                        new Card(CARD_K, SUIT_H),
                        new Card(CARD_Q, SUIT_C),
                        new Card(CARD_J, SUIT_D),
                        new Card(CARD_9, SUIT_H),
                        new Card(CARD_T, SUIT_C)
                )},
                {List.of(new Card(CARD_2, SUIT_D),
                        new Card(CARD_T, SUIT_H),
                        new Card(CARD_A, SUIT_C),
                        new Card(CARD_9, SUIT_D),
                        new Card(CARD_7, SUIT_H),
                        new Card(CARD_J, SUIT_D),
                        new Card(CARD_9, SUIT_S),
                        new Card(CARD_Q, SUIT_H),
                        new Card(CARD_6, SUIT_H),
                        new Card(CARD_T, SUIT_D)
                )},
        };
    }

    @ParameterizedTest
    @MethodSource("testDataPositiveCases")
    void testThreeOfAKindCombination_PositiveCases(final List<Card> inputCards, final List<Card> combinationCards) {
        final Option<List<Card>> result = new ThreeOfAKindCombination().isCombinationValid(inputCards);
        assertEquals(combinationCards, result.get(), "Not a Three of a kind result, input cards " + inputCards);
    }

    @ParameterizedTest
    @MethodSource("testDataNegativeCases")
    void testThreeOfAKindCombination_NegativeCases(final List<Card> inputCards) {
        final Option<List<Card>> result = new ThreeOfAKindCombination().isCombinationValid(inputCards);
        assertTrue(result.isEmpty(), "Unexpected a Three of a kind result " + inputCards);
    }
}