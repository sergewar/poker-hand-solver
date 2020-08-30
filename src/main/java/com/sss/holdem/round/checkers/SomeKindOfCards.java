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
        final CardsUtils cardsUtils = new CardsUtils();
        // todo not the best algorithm, need to update getting card's rank with quantity equals {@code quantity}
        final List<CardRank> cardsRankWithSameRankWithQuantity = cards.stream()
                .sorted(new CardByRankHighToLowComparator())
                .map(Card::getCardRank)
                .distinct()
                .filter(entry -> cardsUtils.countOfCardsByRank(cards, entry) == quantity)
                .collect(Collectors.toUnmodifiableList());
        if (cardsRankWithSameRankWithQuantity.isEmpty()) {
            return List.of();
        } else {
            final List<Card> output = cards.stream()
                    .filter(card -> card.getCardRank() == cardsRankWithSameRankWithQuantity.get(0))
                    .sorted(new CardBySuitComparator())
                    .collect(Collectors.toUnmodifiableList());
            assert output.size() == quantity;
            return output;
        }
    }
}
