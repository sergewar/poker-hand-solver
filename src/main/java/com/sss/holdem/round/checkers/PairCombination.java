package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.List;

import static com.sss.holdem.round.checkers.CombinationRank.PAIR;
import static com.sss.holdem.utils.ListUtils.getEffectiveFiveCards;
import static io.vavr.API.None;
import static io.vavr.API.Some;

public class PairCombination implements Combination {

    /**
     * checking it has pair combination
     *
     * @param cards board's and player's cards
     * @return is valid and list of 5 cards creating this combination
     */
    @Override
    public Option<Tuple2<CombinationRank, List<Card>>> isCombinationValid(final List<Card> cards) {
        final List<Card> list = new SomeKindOfCards().getStrongestCardsWithTheSameKind(cards, 2);
        if (list.isEmpty()) {
            return None();
        } else {
            return Some(Tuple.of(PAIR, getEffectiveFiveCards(list, cards)));
        }
    }
}
