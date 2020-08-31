package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardSuit;
import com.sss.holdem.card.helpers.CardByRankHighToLowComparator;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.sss.holdem.card.CardSuit.SUIT_C;
import static com.sss.holdem.card.CardSuit.SUIT_D;
import static com.sss.holdem.card.CardSuit.SUIT_H;
import static com.sss.holdem.card.CardSuit.SUIT_S;
import static com.sss.holdem.round.checkers.CombinationRank.STRAIGHT_FLUSH;
import static io.vavr.API.None;
import static io.vavr.API.Some;

public class StraightFlushCombination implements Combination {
    /**
     * checking it has straight of the same suit
     *
     * @param cards board's and player's cards
     * @return is valid and list of 5 cards creating this combination
     */
    @Override
    public Option<Tuple2<CombinationRank, List<Card>>> isCombinationValid(final List<Card> cards) {
        final Option<List<Card>> flushCombinationCards = getAllCardsForFlush(cards);
        if (flushCombinationCards.isEmpty()) {
            return None();
        }

        final Option<Tuple2<CombinationRank, List<Card>>> straightFlushCombination =
                new StraightCombination().isCombinationValid(flushCombinationCards.get());

        if (straightFlushCombination.isDefined()) {
            return Some(Tuple.of(STRAIGHT_FLUSH, straightFlushCombination.get()._2));
        }
        return None();
    }

    private Option<List<Card>> getAllCardsForFlush(final List<Card> cards) {
        final List<CardSuit> cardsSuits = cards.stream().map(Card::getCardSuit).collect(Collectors.toUnmodifiableList());
        if (cardsSuits.contains(SUIT_C) && Collections.frequency(cardsSuits, SUIT_C) >= 5) {
            return Some(getCardsBySuit(cards, SUIT_C));
        } else if (cardsSuits.contains(SUIT_D) && Collections.frequency(cardsSuits, SUIT_D) >= 5) {
            return Some(getCardsBySuit(cards, SUIT_D));
        } else if (cardsSuits.contains(SUIT_H) && Collections.frequency(cardsSuits, SUIT_H) >= 5) {
            return Some(getCardsBySuit(cards, SUIT_H));
        } else if (cardsSuits.contains(SUIT_S) && Collections.frequency(cardsSuits, SUIT_S) >= 5) {
            return Some(getCardsBySuit(cards, SUIT_S));
        }
        return None();
    }

    private List<Card> getCardsBySuit(final List<Card> cards, final CardSuit suit) {
        return cards.stream()
                .filter(card -> card.getCardSuit() == suit)
                .sorted(new CardByRankHighToLowComparator())
                .collect(Collectors.toUnmodifiableList());
    }
}
