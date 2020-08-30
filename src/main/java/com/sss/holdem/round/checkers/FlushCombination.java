package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardSuit;
import com.sss.holdem.card.helpers.CardByRankHighToLowComparator;
import io.vavr.control.Option;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.sss.holdem.card.CardSuit.SUIT_C;
import static com.sss.holdem.card.CardSuit.SUIT_D;
import static com.sss.holdem.card.CardSuit.SUIT_H;
import static com.sss.holdem.card.CardSuit.SUIT_S;
import static io.vavr.API.None;
import static io.vavr.API.Some;


public class FlushCombination implements Combination {
    /**
     * checking it has 5 cards of the same suit
     *
     * @param cards board's and player's cards
     * @return is valid and list of 5 cards creating this combination
     */
    @Override
    public Option<List<Card>> isCombinationValid(final List<Card> cards) {
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
                .collect(Collectors.toUnmodifiableList())
                .subList(0, 5);
    }
}
