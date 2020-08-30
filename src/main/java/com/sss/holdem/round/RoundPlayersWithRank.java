package com.sss.holdem.round;

import com.sss.holdem.card.helpers.CardsUtils;
import com.sss.holdem.player.PlayerWithRank;
import com.sss.holdem.player.PlayerWithRankComparator;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class RoundPlayersWithRank {
    private final List<PlayerWithRank> playerWithRanks;

    @Override
    public String toString() {
        final List<PlayerWithRank> playersWithRankSorted = playerWithRanks.stream()
                .sorted(new PlayerWithRankComparator())
                .collect(Collectors.toUnmodifiableList());
        final CardsUtils cardsUtils = new CardsUtils();
        final StringBuilder sb = new StringBuilder();
        // todo need to update below because it not in good style
        IntStream.range(0, playersWithRankSorted.size() - 1).forEach(i -> {
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
