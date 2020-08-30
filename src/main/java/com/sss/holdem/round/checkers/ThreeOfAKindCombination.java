package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import io.vavr.control.Option;

import java.util.List;

import static com.sss.holdem.utils.ListUtils.getEffectiveFiveCards;
import static io.vavr.API.None;
import static io.vavr.API.Some;

public class ThreeOfAKindCombination implements Combination {
    /**
     * checking it has three cards with the same value
     *
     * @param cards board's and player's cards
     * @return is valid and list of 5 cards creating this combination
     */
    @Override
    public Option<List<Card>> isCombinationValid(final List<Card> cards) {
        final List<Card> list = new SomeKindOfCards().getStrongestCardsWithTheSameKind(cards, 3);
        if (list.isEmpty()) {
            return None();
        } else {
            return Some(getEffectiveFiveCards(list, cards));
        }
    }
}
