package com.sss.holdem.card.helpers;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardRank;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CardsUtils {

    public String cardsToString(final List<Card> cards) {
        return cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining());
    }

    public List<Card> dupeCards(final List<Card> cards) {
        return cards.stream()
                .distinct()
                .filter(entry -> Collections.frequency(cards, entry) > 1)
                .sorted(new CardByRankHighToLowComparator())
                .collect(Collectors.toUnmodifiableList());
    }

    public int countOfCardsWithRank(final List<Card> cards, final CardRank cardRank) {
        return (int) cards.stream().filter(card -> card.getCardRank() == cardRank).count();
    }
}
