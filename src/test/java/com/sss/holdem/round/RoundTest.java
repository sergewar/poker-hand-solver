package com.sss.holdem.round;

import com.sss.holdem.board.Board;
import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardRank;
import com.sss.holdem.card.CardSuit;
import com.sss.holdem.player.Player;
import com.sss.holdem.player.PlayerWithRank;
import com.sss.holdem.rules.Rule;
import org.junit.jupiter.api.Test;
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
import static com.sss.holdem.card.CardRank.CARD_A;
import static com.sss.holdem.card.CardRank.CARD_J;
import static com.sss.holdem.card.CardRank.CARD_K;
import static com.sss.holdem.card.CardRank.CARD_Q;
import static com.sss.holdem.card.CardRank.CARD_T;
import static com.sss.holdem.card.CardSuit.SUIT_C;
import static com.sss.holdem.card.CardSuit.SUIT_D;
import static com.sss.holdem.card.CardSuit.SUIT_H;
import static com.sss.holdem.card.CardSuit.SUIT_S;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoundTest {

    private static Object[][] testDataPositiveCases() {
        return new Object[][]{
                {List.of(
                        new PlayerWithRank(
                                0,
                                new Player(new Rule(false),
                                        List.of(new Card(CARD_7, SUIT_H),
                                                new Card(CARD_8, SUIT_S)
                                        )
                                )
                        ),
                        new PlayerWithRank(
                                1,
                                new Player(new Rule(false),
                                        List.of(new Card(CARD_2, SUIT_S),
                                                new Card(CARD_3, SUIT_D)
                                        )
                                )
                        ),
                        new PlayerWithRank(
                                2,
                                new Player(new Rule(false),
                                        List.of(new Card(CARD_4, SUIT_D),
                                                new Card(CARD_3, SUIT_C)
                                        )
                                )
                        ),
                        new PlayerWithRank(
                                3,
                                new Player(new Rule(false),
                                        List.of(new Card(CARD_5, SUIT_C),
                                                new Card(CARD_6, SUIT_D)
                                        )
                                )
                        ),
                        new PlayerWithRank(
                                4,
                                new Player(new Rule(false),
                                        List.of(new Card(CARD_T, SUIT_D),
                                                new Card(CARD_9, SUIT_C)
                                        )
                                )
                        ),
                        new PlayerWithRank(
                                5,
                                new Player(new Rule(false),
                                        List.of(new Card(CARD_A, SUIT_C),
                                                new Card(CARD_K, SUIT_D)
                                        )
                                )
                        ),
                        new PlayerWithRank(
                                6,
                                new Player(new Rule(false),
                                        List.of(new Card(CARD_J, SUIT_C),
                                                new Card(CARD_Q, SUIT_D)
                                        )
                                )
                        )
                ),
                        new Round(
                                new Rule(false),
                                new Board(new Rule(false),
                                        List.of(
                                                new Card(CARD_2, SUIT_C),
                                                new Card(CARD_3, SUIT_C),
                                                new Card(CARD_6, SUIT_C),
                                                new Card(CARD_7, SUIT_C),
                                                new Card(CARD_9, SUIT_D)
                                        )),
                                List.of(
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_2, SUIT_S),
                                                        new Card(CARD_3, SUIT_D)
                                                )),
                                        new Player(new Rule(false),
                                                List.of(
                                                        new Card(CARD_4, SUIT_D),
                                                        new Card(CARD_3, SUIT_C)
                                                )
                                        ),
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_5, SUIT_C),
                                                        new Card(CARD_6, SUIT_D)
                                                )
                                        ),
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_7, SUIT_H),
                                                        new Card(CARD_8, SUIT_S)
                                                )
                                        ),
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_T, SUIT_D),
                                                        new Card(CARD_9, SUIT_C)
                                                )
                                        ),
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_J, SUIT_C),
                                                        new Card(CARD_Q, SUIT_D)
                                                )
                                        ),
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_A, SUIT_C),
                                                        new Card(CARD_K, SUIT_D)
                                                )
                                        )),
                                ""
                        )},
        };
    }

    @Test
    public void invalidRoundIfIncorrectCountOfCardsForBoardWithoutOmahaRule() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        final Board board = new Board(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Player player1 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('Q'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c'))
        ));
        final Player player2 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('3'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('4'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Round round = new Round(rule, board, List.of(player1, player2), "");

        assertFalse(round.isValid(), "This round shouldn't be correct: " + round);
    }

    @Test
    public void invalidRoundIfIncorrectCountOfCardsForBoardWithOmahaRule() {
        final boolean isOmahaRule = true;
        final Rule rule = new Rule(isOmahaRule);
        final Board board = new Board(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Player player1 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('2'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('3'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('4'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('5'), CardSuit.getCardSuitBySuit('c'))
        ));
        final Player player2 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('6'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('7'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('7'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('8'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Round round = new Round(rule, board, List.of(player1, player2), "");

        assertFalse(round.isValid(), "This round shouldn't be correct: " + round);
    }

    @Test
    public void invalidRoundIfIncorrectCountOfCardsForPlayerWithoutOmahaRule() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        final Board board = new Board(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Player player1 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c'))
        ));
        final Player player2 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('3'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('4'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Round round = new Round(rule, board, List.of(player1, player2), "");

        assertFalse(round.isValid(), "This round shouldn't be correct: " + round);
    }

    @Test
    public void invalidRoundIfIncorrectCountOfCardsForPlayerWithOmahaRule() {
        final boolean isOmahaRule = true;
        final Rule rule = new Rule(isOmahaRule);
        final Board board = new Board(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Player player1 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('2'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('3'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('4'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('5'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('6'), CardSuit.getCardSuitBySuit('c'))
        ));
        final Player player2 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('T'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('T'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('Q'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Round round = new Round(rule, board, List.of(player1, player2), "");

        assertFalse(round.isValid(), "This round shouldn't be correct: " + round);
    }

    @Test
    public void invalidRoundIfDupeCardsInRoundWithoutOmahaRule() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        final Board board = new Board(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Player player1 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('6'), CardSuit.getCardSuitBySuit('c'))
        ));
        final Player player2 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('Q'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Round round = new Round(rule, board, List.of(player1, player2), "");

        assertFalse(round.isValid(), "This round shouldn't be correct: " + round);
    }

    @Test
    public void invalidRoundIfDupeCardsInRoundWithOmahaRule() {
        final boolean isOmahaRule = true;
        final Rule rule = new Rule(isOmahaRule);
        final Board board = new Board(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Player player1 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('2'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('3'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('6'), CardSuit.getCardSuitBySuit('c'))
        ));
        final Player player2 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('T'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('T'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('Q'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Round round = new Round(rule, board, List.of(player1, player2), "");

        assertFalse(round.isValid(), "This round shouldn't be correct: " + round);
    }

    @Test
    public void cantChangeCardsInRoundWithoutOmahaRule() {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        final var firstBoardCard = new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c'));
        final Board board = new Board(rule, List.of(
                firstBoardCard,
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        final var firstPlayer1Card = new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c'));
        final Player player1 = new Player(rule, List.of(
                firstPlayer1Card,
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));
        final Player player2 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Round round = new Round(rule, board, List.of(player1, player2), "");

        assertAll(
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getBoard().getBoardCards().add(new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c')))),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getBoard().getBoardCards().remove(firstBoardCard)),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getBoard().getBoardCards().set(0, new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('d')))),

                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getPlayers().get(0).getCards().add(new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c')))),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getPlayers().get(0).getCards().remove(firstPlayer1Card)),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getPlayers().get(0).getCards().set(0, new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c'))))

        );
    }

    @Test
    public void cantChangeCardsInRoundWithOmahaRule() {
        final boolean isOmahaRule = true;
        final Rule rule = new Rule(isOmahaRule);
        final var firstBoardCard = new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c'));
        final Board board = new Board(rule, List.of(
                firstBoardCard,
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('h')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        final var firstPlayer1Card = new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c'));
        final Player player1 = new Player(rule, List.of(
                firstPlayer1Card,
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));
        final Player player2 = new Player(rule, List.of(
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('c')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('s')),
                new Card(CardRank.getCardRankByRank('A'), CardSuit.getCardSuitBySuit('d')),
                new Card(CardRank.getCardRankByRank('K'), CardSuit.getCardSuitBySuit('c'))
        ));

        final Round round = new Round(rule, board, List.of(player1, player2), "");

        assertAll(
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getBoard().getBoardCards().add(new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c')))),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getBoard().getBoardCards().remove(firstBoardCard)),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getBoard().getBoardCards().set(0, new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('d')))),

                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getPlayers().get(0).getCards().add(new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c')))),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getPlayers().get(0).getCards().remove(firstPlayer1Card)),
                () -> assertThrows(
                        UnsupportedOperationException.class,
                        () -> round.getPlayers().get(0).getCards().set(0, new Card(CardRank.getCardRankByRank('J'), CardSuit.getCardSuitBySuit('c'))))

        );
    }

    @ParameterizedTest
    @MethodSource("testDataPositiveCases")
    void getPlayersRank(final List<PlayerWithRank> playerWithRanks, final Round round) {
        assertEquals(playerWithRanks, round.getPlayersRank());
    }
}