package com.sss.holdem.round;

import com.sss.holdem.board.Board;
import com.sss.holdem.card.Card;
import com.sss.holdem.card.helpers.CardsByRankComparator;
import com.sss.holdem.card.helpers.CardsUtils;
import com.sss.holdem.player.Player;
import com.sss.holdem.player.PlayerWithRank;
import com.sss.holdem.round.checkers.CombinationRank;
import com.sss.holdem.round.checkers.FlushCombination;
import com.sss.holdem.round.checkers.FourOfAKindCombination;
import com.sss.holdem.round.checkers.FullHouseCombination;
import com.sss.holdem.round.checkers.HighCardCombination;
import com.sss.holdem.round.checkers.PairCombination;
import com.sss.holdem.round.checkers.StraightCombination;
import com.sss.holdem.round.checkers.StraightFlushCombination;
import com.sss.holdem.round.checkers.ThreeOfAKindCombination;
import com.sss.holdem.round.checkers.TwoPairsCombination;
import com.sss.holdem.rules.Rule;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Option;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sss.holdem.round.checkers.CombinationRank.FLUSH;
import static com.sss.holdem.round.checkers.CombinationRank.FOUR_OF_A_KIND;
import static com.sss.holdem.round.checkers.CombinationRank.FULL_HOUSE;
import static com.sss.holdem.round.checkers.CombinationRank.HIGH_CARD;
import static com.sss.holdem.round.checkers.CombinationRank.PAIR;
import static com.sss.holdem.round.checkers.CombinationRank.STRAIGHT;
import static com.sss.holdem.round.checkers.CombinationRank.STRAIGHT_FLUSH;
import static com.sss.holdem.round.checkers.CombinationRank.THREE_OF_A_KIND;
import static com.sss.holdem.round.checkers.CombinationRank.TWO_PAIRS;
import static com.sss.holdem.utils.ListUtils.concatLists;

@Getter
@ToString
public class Round {
    private final Rule rule;
    private final Board board;
    private final List<Player> players;
    private final String roundInfo;
    private final boolean isValid;

    public Round(final Rule rule,
                 final Board board,
                 final List<Player> players,
                 final String roundInfo) {
        this.rule = rule;
        this.board = board;
        this.players = List.copyOf(players);
        this.roundInfo = roundInfo;
        this.isValid = isRoundValid();
    }

    private boolean isRoundValid() {
        final boolean checkRule = rule.equals(board.getRule())
                && board.isValid()
                && players.stream().allMatch(player -> player.getRule().equals(rule) && player.isValid());

        return checkRule && dupeCards().isEmpty();
    }

    private List<Card> dupeCards() {
        final List<Card> allCardsOnTable =
                concatLists(
                        board.getBoardCards(),
                        players.stream()
                                .map(Player::getCards)
                                .flatMap(Collection::stream)
                                .collect(Collectors.toUnmodifiableList())
                );

        return new CardsUtils().dupeCards(allCardsOnTable);
    }

    public List<PlayerWithRank> getPlayersRank() {
        final List<Tuple3<Player, CombinationRank, List<Card>>> playersResult = new ArrayList<>();
        boolean isLog = Boolean.parseBoolean(System.getProperty("logg", "false"));
        players.forEach(player -> {
            final List<Card> allCards = concatLists(board.getBoardCards(), player.getCards());

            final Option<List<Card>> sfResult = new StraightFlushCombination().isCombinationValid(allCards);
            if (sfResult.isDefined()) {
                if (isLog)
                    System.out.println("Straight flush. Player's cards: " + player.getCards() + " Player's combination: " + sfResult.get());
                playersResult.add(Tuple.of(player, STRAIGHT_FLUSH, sfResult.get()));
                return;
            }
            final Option<List<Card>> fkResult = new FourOfAKindCombination().isCombinationValid(allCards);
            if (fkResult.isDefined()) {
                if (isLog)
                    System.out.println("Four of a kind. Player's cards: " + player.getCards() + " Player's combination: " + fkResult.get());
                playersResult.add(Tuple.of(player, FOUR_OF_A_KIND, fkResult.get()));
                return;
            }
            final Option<List<Card>> fhResult = new FullHouseCombination().isCombinationValid(allCards);
            if (fhResult.isDefined()) {
                if (isLog)
                    System.out.println("Full house. Player's cards: " + player.getCards() + " Player's combination: " + fhResult.get());
                playersResult.add(Tuple.of(player, FULL_HOUSE, fhResult.get()));
                return;
            }
            final Option<List<Card>> fResult = new FlushCombination().isCombinationValid(allCards);
            if (fResult.isDefined()) {
                if (isLog)
                    System.out.println("Flush. Player's cards: " + player.getCards() + " Player's combination: " + fResult.get());
                playersResult.add(Tuple.of(player, FLUSH, fResult.get()));
                return;
            }
            final Option<List<Card>> sResult = new StraightCombination().isCombinationValid(allCards);
            if (sResult.isDefined()) {
                if (isLog)
                    System.out.println("Straight. Player's cards: " + player.getCards() + " Player's combination: " + sResult.get());
                playersResult.add(Tuple.of(player, STRAIGHT, sResult.get()));
                return;
            }
            final Option<List<Card>> tkResult = new ThreeOfAKindCombination().isCombinationValid(allCards);
            if (tkResult.isDefined()) {
                if (isLog)
                    System.out.println("Three of a kind. Player's cards: " + player.getCards() + " Player's combination: " + tkResult.get());
                playersResult.add(Tuple.of(player, THREE_OF_A_KIND, tkResult.get()));
                return;
            }
            final Option<List<Card>> tpResult = new TwoPairsCombination().isCombinationValid(allCards);
            if (tpResult.isDefined()) {
                if (isLog)
                    System.out.println("Two pairs. Player's cards: " + player.getCards() + " Player's combination: " + tpResult.get());
                playersResult.add(Tuple.of(player, TWO_PAIRS, tpResult.get()));
                return;
            }
            final Option<List<Card>> pResult = new PairCombination().isCombinationValid(allCards);
            if (pResult.isDefined()) {
                if (isLog)
                    System.out.println("Pair. Player's cards: " + player.getCards() + " Player's combination: " + pResult.get());
                playersResult.add(Tuple.of(player, PAIR, pResult.get()));
                return;
            }
            final Option<List<Card>> hResult = new HighCardCombination().isCombinationValid(allCards);
            if (hResult.isDefined()) {
                if (isLog)
                    System.out.println("High card. Player's cards: " + player.getCards() + " Player's combination: " + hResult.get());
                playersResult.add(Tuple.of(player, HIGH_CARD, hResult.get()));
                return;
            }

            throw new RuntimeException("Player haven't result, it's strange");
        });
        return getSortedPlayersWithRankFromPlayersResult(playersResult);
    }

    private List<PlayerWithRank> getSortedPlayersWithRankFromPlayersResult(final List<Tuple3<Player, CombinationRank, List<Card>>> playersResult) {
        final List<Tuple3<Player, CombinationRank, List<Card>>> playersResultSorted =
                playersResult.stream()
                        .sorted(new PlayerByResultComparator())
                        .collect(Collectors.toUnmodifiableList());

        return playersResultSorted.stream()
                .map(playerResult -> new PlayerWithRank(playerResult._2.getRank(), playerResult._1))
                .collect(Collectors.toUnmodifiableList());
    }

    private static class PlayerByResultComparator implements Comparator<Tuple3<Player, CombinationRank, List<Card>>> {
        @Override
        public int compare(final Tuple3<Player, CombinationRank, List<Card>> o1, final Tuple3<Player, CombinationRank, List<Card>> o2) {
            final int rc = o1._2.getRank() - o2._2.getRank();
            if (rc != 0) {
                return rc;
            }
            return new CardsByRankComparator().compare(o1._3, o2._3);
        }
    }
}
