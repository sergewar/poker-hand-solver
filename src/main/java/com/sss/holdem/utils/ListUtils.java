package com.sss.holdem.utils;

import com.sss.holdem.card.Card;
import com.sss.holdem.card.helpers.CardByRankHighToLowComparator;
import com.sss.holdem.card.helpers.CardBySuitComparator;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ListUtils {
    private ListUtils() {
        //hide default constructor
    }

    @SafeVarargs
    public static <T> List<T> concatLists(@NonNull final List<T>... lists) {
        return Stream.of(lists).flatMap(List::stream).collect(Collectors.toUnmodifiableList());
    }

    public static List<Card> getEffectiveFiveCards(final List<Card> combination, final List<Card> cardsInRound) {
        return concatLists(
                combination,
                cardsInRound.stream()
                        .filter(e -> !combination.contains(e))
                        .sorted(new CardBySuitComparator())
                        .sorted(new CardByRankHighToLowComparator())
                        .collect(Collectors.toUnmodifiableList())
        )
                .subList(0, 5);
    }
}
