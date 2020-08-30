package com.sss.holdem.card.helpers;

import com.sss.holdem.card.Card;

import java.util.Comparator;

public class CardByRankHighToLowComparator implements Comparator<Card> {
    @Override
    public int compare(final Card card1, final Card card2) {
        return card2.getCardRank().getRankWeight() - card1.getCardRank().getRankWeight();
    }
}
