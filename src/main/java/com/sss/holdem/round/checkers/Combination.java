package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.List;

public interface Combination {
    /**
     * @param cards board's and player's cards
     * @return is valid and list of 5 cards creating this combination
     */
    Option<Tuple2<CombinationRank, List<Card>>> isCombinationValid(final List<Card> cards);
}
