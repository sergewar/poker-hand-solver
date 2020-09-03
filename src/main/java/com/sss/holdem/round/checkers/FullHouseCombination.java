package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.helpers.CardByRankHighToLowComparator;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.List;
import java.util.stream.Collectors;

import static com.sss.holdem.round.checkers.CombinationRank.FULL_HOUSE;
import static com.sss.holdem.utils.ListUtils.concatLists;
import static io.vavr.API.None;
import static io.vavr.API.Some;

public class FullHouseCombination implements Combination {

    /**
     * checking it has combination of three of a kind and a pair
     *
     * @param cards board's and player's cards
     * @return is valid and list of 5 cards creating this combination
     */
    @Override
    public Option<Tuple2<CombinationRank, List<Card>>> isCombinationValid(final List<Card> cards) {
        final SomeKindOfCards someKindOfCards = new SomeKindOfCards();
        final List<Card> list3 = someKindOfCards.getStrongestCardsWithTheSameKind(cards, 3);
        if (list3.isEmpty()) {
            return None();
        } else {
            final List<Card> cardsWithoutThreeOfAKind = cards.stream()
                    .filter(e -> !list3.contains(e))
                    .sorted(new CardByRankHighToLowComparator())
                    .collect(Collectors.toUnmodifiableList());
            final List<Card> list2 = someKindOfCards.getStrongestCardsWithTheSameKind(cardsWithoutThreeOfAKind, 2);
            if (list2.isEmpty()) {
                return None();
            } else {
                return Some(Tuple.of(FULL_HOUSE, concatLists(list3, list2)));
            }
        }
    }
}
