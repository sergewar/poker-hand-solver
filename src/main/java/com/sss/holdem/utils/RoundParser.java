package com.sss.holdem.utils;

import com.sss.holdem.board.Board;
import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardRank;
import com.sss.holdem.card.CardSuit;
import com.sss.holdem.player.Player;
import com.sss.holdem.round.Round;
import com.sss.holdem.rules.Rule;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.Left;
import static io.vavr.API.Right;

@AllArgsConstructor
public class RoundParser implements Parser {
    private final Rule rule;

    @Override
    public Either<String, Round> parse(final String line) {
        if (line == null || line.isEmpty()) {
            return Left("Empty line, should contains at least cards for board and for player");
        }

        final List<String> splitLine = List.of(line.trim().split("\\s+"));
        if (splitLine.size() < 2) {
            return Left("Not enough arguments, " +
                    "should contains at least cards for board and for one player. Line for parse: " + line);
        }

        if (splitLine.size() > ((52 - rule.getCountOfPlayersCards()) / 2)) {
            return Left("More cards than in one deck have (more than 52 cards): " + line);
        }

        final String boardCards = splitLine.get(0);
        if (boardCards.length() != rule.getCountOfBoardsCars() * 2
                || splitLine.stream().skip(1).anyMatch(st -> st.length() != rule.getCountOfPlayersCards() * 2)) {
            return Left("Wrong size of board cards (should be 5) " +
                    "or for player cards (should be " + rule.getCountOfPlayersCards() + "): Line for parse: " + line);
        }

        try {
            final Board board = new Board(rule, List.of(
                    new Card(CardRank.getCardRankByRank(boardCards.charAt(0)), CardSuit.getCardSuitBySuit(boardCards.charAt(1))),
                    new Card(CardRank.getCardRankByRank(boardCards.charAt(2)), CardSuit.getCardSuitBySuit(boardCards.charAt(3))),
                    new Card(CardRank.getCardRankByRank(boardCards.charAt(4)), CardSuit.getCardSuitBySuit(boardCards.charAt(5))),
                    new Card(CardRank.getCardRankByRank(boardCards.charAt(6)), CardSuit.getCardSuitBySuit(boardCards.charAt(7))),
                    new Card(CardRank.getCardRankByRank(boardCards.charAt(8)), CardSuit.getCardSuitBySuit(boardCards.charAt(9))))
            );

            final List<Player> playerCards = splitLine.stream().skip(1)
                    .map(st -> new Player(
                                    rule,
                                    !rule.isOmahaRule()
                                            ? List.of(
                                            new Card(CardRank.getCardRankByRank(st.charAt(0)), CardSuit.getCardSuitBySuit(st.charAt(1))),
                                            new Card(CardRank.getCardRankByRank(st.charAt(2)), CardSuit.getCardSuitBySuit(st.charAt(3)))
                                    )
                                            : List.of(
                                            new Card(CardRank.getCardRankByRank(st.charAt(0)), CardSuit.getCardSuitBySuit(st.charAt(1))),
                                            new Card(CardRank.getCardRankByRank(st.charAt(2)), CardSuit.getCardSuitBySuit(st.charAt(3))),
                                            new Card(CardRank.getCardRankByRank(st.charAt(4)), CardSuit.getCardSuitBySuit(st.charAt(5))),
                                            new Card(CardRank.getCardRankByRank(st.charAt(6)), CardSuit.getCardSuitBySuit(st.charAt(7))))
                            )
                    )
                    .collect(Collectors.toUnmodifiableList());

            final Round round = new Round(rule, board, playerCards, "Board and player cards are valid");

            if (!round.isValid()) {
                return Left("Look like round has duplicated cards on table: " + line);
            }

            return Right(round);
        } catch (IllegalArgumentException e) {
            return Left("Wrong data, can't parse. Line:  " + line);
        }
    }
}
