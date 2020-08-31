package com.sss.holdem.round;

import com.sss.holdem.card.Card;
import com.sss.holdem.player.Player;
import com.sss.holdem.player.PlayerWithRank;
import com.sss.holdem.rules.Rule;
import io.vavr.Tuple;
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
import static com.sss.holdem.round.checkers.CombinationRank.PAIR;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundPlayersWithRankTest {

    private static Object[][] testDataPositiveCases() {
        return new Object[][]{
                {"2c3d=3c4d 5c6d 7h8s=9cTd=JcQd KcAd",
                        List.of(
                                new PlayerWithRank(
                                        2,
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_2, SUIT_C),
                                                        new Card(CARD_3, SUIT_D)
                                                )
                                        ),
                                        Tuple.of(PAIR,
                                                List.of(new Card(CARD_2, SUIT_C),
                                                        new Card(CARD_3, SUIT_D)
                                                ))
                                ),
                                new PlayerWithRank(
                                        2,
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_3, SUIT_C),
                                                        new Card(CARD_4, SUIT_D)
                                                )
                                        ),
                                        Tuple.of(PAIR,
                                                List.of(new Card(CARD_2, SUIT_C),
                                                        new Card(CARD_3, SUIT_D)
                                                ))
                                ),
                                new PlayerWithRank(
                                        3,
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_5, SUIT_C),
                                                        new Card(CARD_6, SUIT_D)
                                                )
                                        ),
                                        Tuple.of(PAIR,
                                                List.of(new Card(CARD_2, SUIT_C),
                                                        new Card(CARD_3, SUIT_D)
                                                ))
                                ),
                                new PlayerWithRank(
                                        4,
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_7, SUIT_H),
                                                        new Card(CARD_8, SUIT_S)
                                                )
                                        ),
                                        Tuple.of(PAIR,
                                                List.of(new Card(CARD_2, SUIT_C),
                                                        new Card(CARD_3, SUIT_D)
                                                ))
                                ),
                                new PlayerWithRank(
                                        4,
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_9, SUIT_C),
                                                        new Card(CARD_T, SUIT_D)
                                                )
                                        ),
                                        Tuple.of(PAIR,
                                                List.of(new Card(CARD_2, SUIT_C),
                                                        new Card(CARD_3, SUIT_D)
                                                ))
                                ),
                                new PlayerWithRank(
                                        4,
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_J, SUIT_C),
                                                        new Card(CARD_Q, SUIT_D)
                                                )
                                        ),
                                        Tuple.of(PAIR,
                                                List.of(new Card(CARD_2, SUIT_C),
                                                        new Card(CARD_3, SUIT_D)
                                                ))
                                ),
                                new PlayerWithRank(
                                        5,
                                        new Player(new Rule(false),
                                                List.of(new Card(CARD_K, SUIT_C),
                                                        new Card(CARD_A, SUIT_D)
                                                )
                                        ),
                                        Tuple.of(PAIR,
                                                List.of(new Card(CARD_2, SUIT_C),
                                                        new Card(CARD_3, SUIT_D)
                                                ))
                                )

                        ),
                },
        };
    }

    @ParameterizedTest
    @MethodSource("testDataPositiveCases")
    void roundPlayersWithRankToString(final String expectedResult, final List<PlayerWithRank> playerWithRanks) {
        assertEquals(expectedResult, new RoundPlayersWithRank(playerWithRanks).toString());
    }

}