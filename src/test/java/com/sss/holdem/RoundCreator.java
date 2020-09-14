package com.sss.holdem;

import com.sss.holdem.board.Board;
import com.sss.holdem.card.Card;
import com.sss.holdem.player.Player;
import com.sss.holdem.round.Round;
import com.sss.holdem.rules.Rule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@RequiredArgsConstructor
public class RoundCreator {
    final Rule rule;
    final int playersCount;
    final Deck deck;

    public Round getRound() {
        final List<Card> boardCards = deck.getShuffledCards().subList(0, rule.getCountOfBoardsCars());
        final List<Player> players = IntStream.range(0, playersCount).boxed()
                .map(i -> new Player(rule, deck.getShuffledCards()
                        .subList(rule.getCountOfBoardsCars() + i * rule.getCountOfPlayersCards(),
                                rule.getCountOfBoardsCars() + (i + 1) * rule.getCountOfPlayersCards())))
                .collect(Collectors.toUnmodifiableList());
        return new Round(rule, new Board(rule, boardCards), players, "");
    }

}
