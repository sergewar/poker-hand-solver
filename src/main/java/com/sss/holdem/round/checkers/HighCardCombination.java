package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.helpers.CardByRankHighToLowComparator;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.List;
import java.util.stream.Collectors;

import static com.sss.holdem.round.checkers.CombinationRank.HIGH_CARD;
import static io.vavr.API.Some;

public class HighCardCombination implements Combination {

    /**
     * the "fallback" in case no other hand value rule applies
     *
     * @param cards board's and player's cards
     * @return is valid and list of 5 cards creating this combination
     */
    @Override
    public Option<Tuple2<CombinationRank, List<Card>>> isCombinationValid(final List<Card> cards) {
        return Some(
                Tuple.of(
                        HIGH_CARD,
                        cards.stream()
                                .sorted(new CardByRankHighToLowComparator())
                                .collect(Collectors.toUnmodifiableList())
                                .subList(0, 5))
        );
    }
}
