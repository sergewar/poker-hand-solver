package com.sss.holdem.round;

import com.sss.holdem.board.Board;
import com.sss.holdem.card.Card;
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
import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sss.holdem.utils.ListUtils.concatLists;
import static com.sss.holdem.utils.PropertiesUtils.isLog;

@Getter
@ToString
public class Round {
    private final Rule rule;
    private final Board board;
    private final List<Player> players;
    private final String roundInfo;
    private final boolean isValid;

    private final Set<Set<Integer>> boardCombinations;
    private final Set<Set<Integer>> playerCombinations;

    public Round(final Rule rule,
                 final Board board,
                 final List<Player> players,
                 final String roundInfo) {
        this.rule = rule;
        this.board = board;
        this.players = List.copyOf(players);
        this.roundInfo = roundInfo;
        this.isValid = isRoundValid();

        this.boardCombinations = boardCardsCombinationIndexes2();
        this.playerCombinations = playerCardsCombinationIndexes2();
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
        final boolean isLog = isLog();
        if (!rule.isOmahaRule()) {
            players.forEach(player -> {
                final List<Card> allCards = concatLists(board.getBoardCards(), player.getCards());
                final Tuple2<CombinationRank, List<Card>> resultForCombination = resultForCombination(allCards);
                playersResult.add(Tuple.of(player, resultForCombination));
                if (isLog)
                    System.out.println("Player's cards: " + player.getCards() + " Combination: " + resultForCombination);
            });
        } else {
            final PlayerCombinationAndCardsComparator combinationAndCardsComparator = new PlayerCombinationAndCardsComparator();
            players.forEach(player -> {
                final AtomicReference<Tuple2<CombinationRank, List<Card>>> bestResultForPlayer = new AtomicReference<>(Tuple.of(CombinationRank.HIGH_CARD, List.of()));
                boardCombinations.forEach(bc -> {
                    final Iterator<Integer> iterBc = bc.iterator();
                    final int firstBoardCardIndex = iterBc.next();
                    final int secondBoardCardIndex = iterBc.next();
                    final int thirdBoardCardIndex = iterBc.next();
                    playerCombinations.forEach(pc -> {
                        final Iterator<Integer> iterPc = pc.iterator();
                        final int firstPlayerCardIndex = iterPc.next();
                        final int secondPlayerCardIndex = iterPc.next();
                        final List<Card> allCards = concatLists(
                                board.getBoardCards().subList(firstBoardCardIndex, firstBoardCardIndex + 1),
                                board.getBoardCards().subList(secondBoardCardIndex, secondBoardCardIndex + 1),
                                board.getBoardCards().subList(thirdBoardCardIndex, thirdBoardCardIndex + 1),
                                player.getCards().subList(firstPlayerCardIndex, firstPlayerCardIndex + 1),
                                player.getCards().subList(secondPlayerCardIndex, secondPlayerCardIndex + 1));

                        final Tuple2<CombinationRank, List<Card>> resultForCombination = resultForCombination(allCards);
                        if (combinationAndCardsComparator.compare(bestResultForPlayer.get(), resultForCombination) < 0) {
                            bestResultForPlayer.set(resultForCombination);
                        }
                    });
                });

                playersResult.add(Tuple.of(player, bestResultForPlayer.get()));
                if (isLog)
                    System.out.println("Player's cards: " + player.getCards() + " Best combination: " + bestResultForPlayer);
            });
        }

        return getSortedPlayersWithRankFromPlayersResult(playersResult);
    }

    private Tuple2<CombinationRank, List<Card>> resultForCombination(final List<Card> allCards) {

        final Option<Tuple2<CombinationRank, List<Card>>> sfResult = new StraightFlushCombination().isCombinationValid(allCards);
        if (sfResult.isDefined()) { // STRAIGHT_FLUSH
            return sfResult.get();
        }
        final Option<Tuple2<CombinationRank, List<Card>>> fkResult = new FourOfAKindCombination().isCombinationValid(allCards);
        if (fkResult.isDefined()) { // FOUR_OF_A_KIND
            return fkResult.get();
        }
        final Option<Tuple2<CombinationRank, List<Card>>> fhResult = new FullHouseCombination().isCombinationValid(allCards);
        if (fhResult.isDefined()) { // FULL_HOUSE
            return fhResult.get();
        }
        final Option<Tuple2<CombinationRank, List<Card>>> fResult = new FlushCombination().isCombinationValid(allCards);
        if (fResult.isDefined()) { // FLUSH
            return fResult.get();
        }
        final Option<Tuple2<CombinationRank, List<Card>>> sResult = new StraightCombination().isCombinationValid(allCards);
        if (sResult.isDefined()) { // STRAIGHT
            return sResult.get();
        }
        final Option<Tuple2<CombinationRank, List<Card>>> tkResult = new ThreeOfAKindCombination().isCombinationValid(allCards);
        if (tkResult.isDefined()) { // THREE_OF_A_KIND
            return tkResult.get();
        }
        final Option<Tuple2<CombinationRank, List<Card>>> tpResult = new TwoPairsCombination().isCombinationValid(allCards);
        if (tpResult.isDefined()) { // TWO_PAIRS
            return tpResult.get();
        }
        final Option<Tuple2<CombinationRank, List<Card>>> pResult = new PairCombination().isCombinationValid(allCards);
        if (pResult.isDefined()) { // PAIR
            return pResult.get();
        }
        final Option<Tuple2<CombinationRank, List<Card>>> hResult = new HighCardCombination().isCombinationValid(allCards);
        if (hResult.isDefined()) { // HIGH_CARD
            return hResult.get();
        }

        throw new RuntimeException("Player haven't result, it's strange");
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

    private Set<Set<Integer>> boardCardsCombinationIndexes2() {
        return Generator.combination(IntStream.range(0, rule.getCountOfBoardsCars()).boxed().collect(Collectors.toUnmodifiableList()))
                .simple(rule.isOmahaRule() ? 3 : rule.getCountOfBoardsCars())
                .stream()
                .map(Set::copyOf)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Set<Set<Integer>> playerCardsCombinationIndexes2() {
        return Generator.combination(IntStream.range(0, rule.getCountOfPlayersCards()).boxed().collect(Collectors.toUnmodifiableList()))
                .simple(2)
                .stream()
                .map(Set::copyOf)
                .collect(Collectors.toUnmodifiableSet());
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

    private static class CardsByRankWeightComparator implements Comparator<List<Card>> {
        @Override
        public int compare(final List<Card> cards1, final List<Card> cards2) {
            if (cards1.size() != cards2.size()) {
                return cards1.size() - cards2.size();
            } else {
                for (int i = 0; i < cards1.size(); i++) {
                    final int rc = cards1.get(i).getCardRank().getRankWeight() - cards2.get(i).getCardRank().getRankWeight();
                    if (rc != 0) {
                        return rc;
                    }
                }
            }
            return 0;
        }
    }
}
