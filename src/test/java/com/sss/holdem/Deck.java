package com.sss.holdem;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.CardRank;
import com.sss.holdem.card.CardSuit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class Deck {
    private final List<Card> cards;
    private final List<Card> shuffledCards;

    public Deck() {
        this.cards = Arrays.stream(CardRank.values())
                .map(cardRank ->
                        Arrays.stream(CardSuit.values())
                                .map(cardSuit -> new Card(cardRank, cardSuit))
                                .collect(Collectors.toUnmodifiableList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());
        this.shuffledCards = shuffleCards(cards);
    }

    private List<Card> shuffleCards(final List<Card> cardsForShuffle) {
        final List<Card> copy = new ArrayList<>(cardsForShuffle);
        final long seed = System.nanoTime();
        Collections.shuffle(copy, new Random(seed));
        return List.copyOf(copy); // make list unmodifiable
    }
}
