package com.sss.holdem.round.checkers;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardRank;
import com.sss.holdem.card.helpers.CardByRankHighToLowComparator;
import com.sss.holdem.card.helpers.CardBySuitComparator;
import com.sss.holdem.card.helpers.CardsUtils;

import java.util.List;
import java.util.stream.Collectors;

class SomeKindOfCards {
    List<Card> getStrongestCardsWithTheSameKind(final List<Card> cards, final int quantity) {
        return getStrongestCardsWithTheSameKind(cards, quantity, true);
    }

    List<Card> getStrongestCardsWithTheSameKind(final List<Card> cards, final int quantity, final boolean strictQuantity) {
        final CardsUtils cardsUtils = new CardsUtils();
        // todo not the best algorithm, need to update getting card's rank with quantity equals {@code quantity}
        final List<CardRank> cardsRankWithSameRankWithQuantity = cards.stream()
                .sorted(new CardByRankHighToLowComparator())
                .map(Card::getCardRank)
                .distinct()
                .filter(cardRank -> {
                            final int countOfCardsWithRank = cardsUtils.countOfCardsWithRank(cards, cardRank);
                            return strictQuantity ? countOfCardsWithRank == quantity : countOfCardsWithRank >= quantity;
                        }
                )
                .collect(Collectors.toUnmodifiableList());
        if (cardsRankWithSameRankWithQuantity.isEmpty()) {
            return List.of();
        } else {
            final List<Card> output = cards.stream()
                    .filter(card -> card.getCardRank() == cardsRankWithSameRankWithQuantity.get(0))
                    .sorted(new CardBySuitComparator())
                    .collect(Collectors.toUnmodifiableList());
            if (strictQuantity) {
                assert output.size() == quantity;
            } else {
                assert output.size() >= quantity;
            }
            return output;
        }
    }
}
