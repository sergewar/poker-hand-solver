package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.helpers.CardByRankHighToLowComparator;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.List;
import java.util.stream.Collectors;

import static com.sss.holdem.round.checkers.CombinationRank.TWO_PAIRS;
import static com.sss.holdem.utils.ListUtils.concatLists;
import static com.sss.holdem.utils.ListUtils.getEffectiveFiveCards;
import static io.vavr.API.None;
import static io.vavr.API.Some;

public class TwoPairsCombination implements Combination {
    /**
     * checking it has two times two cards with the same value
     *
     * @param cards board's and player's cards
     * @return is valid and list of 5 cards creating this combination
     */
    @Override
    public Option<Tuple2<CombinationRank, List<Card>>> isCombinationValid(final List<Card> cards) {
        final SomeKindOfCards someKindOfCards = new SomeKindOfCards();
        final List<Card> list2 = someKindOfCards.getStrongestCardsWithTheSameKind(cards, 2);
        if (list2.isEmpty()) {
            return None();
        } else {
            final List<Card> cardsWithoutTwoOfAKind = cards.stream()
                    .filter(e -> !list2.contains(e))
                    .sorted(new CardByRankHighToLowComparator())
                    .collect(Collectors.toUnmodifiableList());
            final List<Card> list2_2 = someKindOfCards.getStrongestCardsWithTheSameKind(cardsWithoutTwoOfAKind, 2);
            if (list2_2.isEmpty()) {
                return None();
            } else {
                return Some(Tuple.of(TWO_PAIRS, getEffectiveFiveCards(concatLists(list2, list2_2), cards)));
            }
        }
    }
}
