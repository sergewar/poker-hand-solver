package com.sss.holdem.card.helpers;

import com.sss.holdem.card.Card;

import java.util.Comparator;

public class CardBySuitComparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, final Card card2) {
        return card1.getCardSuit().getSuit() - card2.getCardSuit().getSuit();
    }
}
