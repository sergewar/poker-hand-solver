package com.sss.holdem.card.helpers;

import com.sss.holdem.card.Card;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CardsByRankWeightComparator implements Comparator<List<Card>> {
    @Override
    public int compare(final List<Card> cards1, final List<Card> cards2) {
        if (cards1.size() != cards2.size()) {
            return cards1.size() - cards2.size();
        } else {
            final var cardByRankHighToLowComparator = new CardByRankHighToLowComparator();
            final List<Card> sortedCards1 = cards1.stream().sorted(cardByRankHighToLowComparator).collect(Collectors.toUnmodifiableList());
            final List<Card> sortedCards2 = cards2.stream().sorted(cardByRankHighToLowComparator).collect(Collectors.toUnmodifiableList());
            for (int i = 0; i < sortedCards1.size(); i++) {
                final int rc = sortedCards1.get(i).getCardRank().getRankWeight() - sortedCards2.get(i).getCardRank().getRankWeight();
                if (rc != 0) {
                    return rc;
                }
            }
        }
        return 0;
    }
}
