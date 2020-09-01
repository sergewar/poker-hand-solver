package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardRank;
import com.sss.holdem.card.helpers.CardByRankHighToLowComparator;
import com.sss.holdem.card.helpers.CardBySuitComparator;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.sss.holdem.round.checkers.CombinationRank.STRAIGHT;
import static io.vavr.API.None;
import static io.vavr.API.Some;

public class StraightCombination implements Combination {

    //! Methods distinctByKey() not my code! It was copy pasted.
    // I don't understand clearly how it works but it works as I need:(
    private <T> Predicate<T> distinctByKey(final Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * checking it has Straight combination
     *
     * @param cards board's and player's cards
     * @return is valid and list of 5 cards creating this combination
     */
    @Override
    public Option<Tuple2<CombinationRank, List<Card>>> isCombinationValid(final List<Card> cards) {
        final List<Card> cardsSorted = cards.stream()
                .sorted(new CardBySuitComparator())
                .sorted(new CardByRankHighToLowComparator())
                .filter(distinctByKey(Card::getCardRank))
                .collect(Collectors.toUnmodifiableList());
        if (cardsSorted.size() < 5) {
            return None();
        }

        // it's not looking good but щито поделать Десу
        Option<List<Card>> strSorted = getBestSublistFiveCardsOrderedOnByOne(cardsSorted);
        if (strSorted.isDefined()) {
            final List<Card> straightCards = strSorted.get();
            return Some(
                    Tuple.of(
                            STRAIGHT,
                            List.of(
                                    straightCards.get(0),
                                    straightCards.get(1),
                                    straightCards.get(2),
                                    straightCards.get(3),
                                    straightCards.get(4)
                            )));
        } else if (cardsSorted.get(0).getCardRank() == CardRank.CARD_A) {
            if ((cardsSorted.size() > 5)) {
                Option<List<Card>> strSortedWithoutAInHead = getBestSublistFiveCardsOrderedOnByOne(cardsSorted.subList(1, cardsSorted.size()));
                if (strSortedWithoutAInHead.isDefined()) {
                    final List<Card> straightCards = strSortedWithoutAInHead.get();
                    return Some(
                            Tuple.of(
                                    STRAIGHT,
                                    List.of(
                                            straightCards.get(0),
                                            straightCards.get(1),
                                            straightCards.get(2),
                                            straightCards.get(3),
                                            straightCards.get(4)
                                    )));
                }
            }
            if (cardsSorted.get(cardsSorted.size() - 4).getCardRank() == CardRank.CARD_5
                    && cardsSorted.get(cardsSorted.size() - 3).getCardRank() == CardRank.CARD_4
                    && cardsSorted.get(cardsSorted.size() - 2).getCardRank() == CardRank.CARD_3
                    && cardsSorted.get(cardsSorted.size() - 1).getCardRank() == CardRank.CARD_2) {
                return Some(
                        Tuple.of(
                                STRAIGHT,
                                List.of(
                                        cardsSorted.get(cardsSorted.size() - 4),
                                        cardsSorted.get(cardsSorted.size() - 3),
                                        cardsSorted.get(cardsSorted.size() - 2),
                                        cardsSorted.get(cardsSorted.size() - 1),
                                        cardsSorted.get(0)
                                )));
            }
            return None();
        }
        return None();
    }

    private Option<List<Card>> getBestSublistFiveCardsOrderedOnByOne(final List<Card> cards) {
        // expect that cards were sorted in high to low order previously
        // don't sort and check size here again because of performance
//        if (cards.size() < 5) {
//            return None();
//        }

        for (int i = 0; i < cards.size(); i++) {
            final List<Card> cardsSubList = cards.subList(i, cards.size());
            if (cardsSubList.size() < 5) {
                return None();
            }
            final Iterator<Card> iter = cardsSubList.iterator();
            final List<Card> strCards = new ArrayList<>();
            Card current;
            Card previous = iter.next();
            strCards.add(previous);
            final CardByRankHighToLowComparator cardByRankHighToLowComparator = new CardByRankHighToLowComparator();
            while (iter.hasNext()) {
                current = iter.next();
                if (cardByRankHighToLowComparator.compare(current, previous) != 1) {
                    break;
                }
                previous = current;
                strCards.add(previous);
                if (strCards.size() == 5) {
                    return Some(strCards.subList(0, 5));
                }
            }
        }
        return None();
    }
}
