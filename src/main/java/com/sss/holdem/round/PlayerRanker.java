package com.sss.holdem.round;

import com.sss.holdem.card.helpers.CardsUtils;
import com.sss.holdem.player.PlayerWithRank;
import com.sss.holdem.player.PlayerWithRankComparator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class PlayerRanker {
    private PlayerRanker() {
        //hide default constructor
    }

    public static String playersWithRankCardsToString(final List<PlayerWithRank> playersWithRank) {
        final List<PlayerWithRank> playersWithRankSorted = playersWithRank.stream()
                .sorted(new PlayerWithRankComparator())
                .collect(Collectors.toUnmodifiableList());
        final CardsUtils cardsUtils = new CardsUtils();
        final StringBuilder sb = new StringBuilder();
        // todo need to update below because it not in good style
        IntStream.range(0, playersWithRankSorted.size()).forEach(i -> {
            sb.append(cardsUtils.cardsToString(playersWithRankSorted.get(i).getPlayer().getCards()));
            if (playersWithRankSorted.get(i).getRank() == playersWithRankSorted.get(i + 1).getRank()) {
                sb.append("=");
            } else {
                sb.append(" ");
            }
        });
        sb.append(cardsUtils.cardsToString(playersWithRankSorted.get(playersWithRankSorted.size() - 1).getPlayer().getCards()));
        return sb.toString();
    }
}
