package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.List;

import static com.sss.holdem.round.checkers.CombinationRank.FOUR_OF_A_KIND;
import static com.sss.holdem.utils.ListUtils.getEffectiveFiveCards;
import static io.vavr.API.None;
import static io.vavr.API.Some;

public class FourOfAKindCombination implements Combination {

    /**
     * checking it has four cards of the same value
     *
     * @param cards board's and player's cards
     * @return is valid and list of 5 cards creating this combination
     */
    @Override
    public Option<Tuple2<CombinationRank, List<Card>>> isCombinationValid(final List<Card> cards) {
        final List<Card> list4 = new SomeKindOfCards().getStrongestCardsWithTheSameKind(cards, 4);
        if (list4.isEmpty()) {
            return None();
        } else {
            return Some(Tuple.of(FOUR_OF_A_KIND, getEffectiveFiveCards(list4, cards)));
        }
    }
}
