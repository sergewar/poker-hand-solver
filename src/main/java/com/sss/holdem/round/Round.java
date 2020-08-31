package com.sss.holdem.round;

import com.sss.holdem.board.Board;
import com.sss.holdem.card.Card;
import com.sss.holdem.card.helpers.CardsByRankWeightComparator;
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
import io.vavr.Tuple2;
import io.vavr.control.Option;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
        final List<Tuple2<Player, Tuple2<CombinationRank, List<Card>>>> playersResult = new ArrayList<>();
        final boolean isLog = Boolean.parseBoolean(System.getProperty("logg", "false"));
        players.forEach(player -> {
            final List<Card> allCards = concatLists(board.getBoardCards(), player.getCards());

            final Option<Tuple2<CombinationRank, List<Card>>> sfResult = new StraightFlushCombination().isCombinationValid(allCards);
            if (sfResult.isDefined()) { // STRAIGHT_FLUSH
                if (isLog)
                    System.out.println("Straight flush. Player's cards: " + player.getCards() + " Player's combination: " + sfResult.get());
                playersResult.add(Tuple.of(player, sfResult.get()));
                return;
            }
            final Option<Tuple2<CombinationRank, List<Card>>> fkResult = new FourOfAKindCombination().isCombinationValid(allCards);
            if (fkResult.isDefined()) { // FOUR_OF_A_KIND
                if (isLog)
                    System.out.println("Four of a kind. Player's cards: " + player.getCards() + " Player's combination: " + fkResult.get());
                playersResult.add(Tuple.of(player, fkResult.get()));
                return;
            }
            final Option<Tuple2<CombinationRank, List<Card>>> fhResult = new FullHouseCombination().isCombinationValid(allCards);
            if (fhResult.isDefined()) { // FULL_HOUSE
                if (isLog)
                    System.out.println("Full house. Player's cards: " + player.getCards() + " Player's combination: " + fhResult.get());
                playersResult.add(Tuple.of(player, fhResult.get()));
                return;
            }
            final Option<Tuple2<CombinationRank, List<Card>>> fResult = new FlushCombination().isCombinationValid(allCards);
            if (fResult.isDefined()) { // FLUSH
                if (isLog)
                    System.out.println("Flush. Player's cards: " + player.getCards() + " Player's combination: " + fResult.get());
                playersResult.add(Tuple.of(player, fResult.get()));
                return;
            }
            final Option<Tuple2<CombinationRank, List<Card>>> sResult = new StraightCombination().isCombinationValid(allCards);
            if (sResult.isDefined()) { // STRAIGHT
                if (isLog)
                    System.out.println("Straight. Player's cards: " + player.getCards() + " Player's combination: " + sResult.get());
                playersResult.add(Tuple.of(player, sResult.get()));
                return;
            }
            final Option<Tuple2<CombinationRank, List<Card>>> tkResult = new ThreeOfAKindCombination().isCombinationValid(allCards);
            if (tkResult.isDefined()) { // THREE_OF_A_KIND
                if (isLog)
                    System.out.println("Three of a kind. Player's cards: " + player.getCards() + " Player's combination: " + tkResult.get());
                playersResult.add(Tuple.of(player, tkResult.get()));
                return;
            }
            final Option<Tuple2<CombinationRank, List<Card>>> tpResult = new TwoPairsCombination().isCombinationValid(allCards);
            if (tpResult.isDefined()) { // TWO_PAIRS
                if (isLog)
                    System.out.println("Two pairs. Player's cards: " + player.getCards() + " Player's combination: " + tpResult.get());
                playersResult.add(Tuple.of(player, tpResult.get()));
                return;
            }
            final Option<Tuple2<CombinationRank, List<Card>>> pResult = new PairCombination().isCombinationValid(allCards);
            if (pResult.isDefined()) { // PAIR
                if (isLog)
                    System.out.println("Pair. Player's cards: " + player.getCards() + " Player's combination: " + pResult.get());
                playersResult.add(Tuple.of(player, pResult.get()));
                return;
            }
            final Option<Tuple2<CombinationRank, List<Card>>> hResult = new HighCardCombination().isCombinationValid(allCards);
            if (hResult.isDefined()) { // HIGH_CARD
                if (isLog)
                    System.out.println("High card. Player's cards: " + player.getCards() + " Player's combination: " + hResult.get());
                playersResult.add(Tuple.of(player, hResult.get()));
                return;
            }

            throw new RuntimeException("Player haven't result, it's strange");
        });
        return getSortedPlayersWithRankFromPlayersResult(playersResult);
    }

    private List<PlayerWithRank> getSortedPlayersWithRankFromPlayersResult(final List<Tuple2<Player, Tuple2<CombinationRank, List<Card>>>> playersResult) {
        final List<Tuple2<Player, Tuple2<CombinationRank, List<Card>>>> playersResultSorted =
                playersResult.stream()
                        .sorted(new PlayerByResultComparator())
                        .collect(Collectors.toUnmodifiableList());

        int curR = 0;
        final PlayerCombinationAndCardsComparator playerCombinationAndCardsComparator = new PlayerCombinationAndCardsComparator();
        final Iterator<Tuple2<Player, Tuple2<CombinationRank, List<Card>>>> iter = playersResultSorted.iterator();
        final List<PlayerWithRank> out = new ArrayList<>();
        Tuple2<Player, Tuple2<CombinationRank, List<Card>>> current;
        Tuple2<Player, Tuple2<CombinationRank, List<Card>>> previous = iter.next();
        out.add(new PlayerWithRank(curR, previous._1, previous._2));
        while (iter.hasNext()) {
            current = iter.next();
            if (playerCombinationAndCardsComparator.compare(current._2, previous._2) != 0) {
                curR++;
            }
            previous = current;
            out.add(new PlayerWithRank(curR, previous._1, previous._2));
        }

        return out;
    }

    private static class PlayerByResultComparator implements Comparator<Tuple2<Player, Tuple2<CombinationRank, List<Card>>>> {
        @Override
        public int compare(final Tuple2<Player, Tuple2<CombinationRank, List<Card>>> o1, final Tuple2<Player, Tuple2<CombinationRank, List<Card>>> o2) {
            final int rc1 = o1._2._1.getRank() - o2._2._1.getRank();
            if (rc1 != 0) {
                return rc1;
            }
            return new CardsByRankWeightComparator().compare(o1._2._2, o2._2._2);
        }
    }

    private static class PlayerCombinationAndCardsComparator implements Comparator<Tuple2<CombinationRank, List<Card>>> {
        @Override
        public int compare(final Tuple2<CombinationRank, List<Card>> o1, final Tuple2<CombinationRank, List<Card>> o2) {
            final int rc = o1._1.getRank() - o2._1.getRank();
            if (rc != 0) {
                return rc;
            }
            return new CardsByRankWeightComparator().compare(o1._2, o2._2);
        }
    }
}
