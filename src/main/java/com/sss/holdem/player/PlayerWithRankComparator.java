package com.sss.holdem.player;

import com.sss.holdem.card.helpers.CardsUtils;

import java.util.Comparator;

public class PlayerWithRankComparator implements Comparator<PlayerWithRank> {
    private final PlayerWithRankByRankComparator playerWithRankByRankComparator = new PlayerWithRankByRankComparator();
    private final PlayerWithRankByCardsComparator playerWithRankByCardsComparator = new PlayerWithRankByCardsComparator();

    @Override
    public int compare(final PlayerWithRank plr1, final PlayerWithRank plr2) {
        final int rc = playerWithRankByRankComparator.compare(plr1, plr2);
        if (rc != 0) {
            return rc;
        }
        return playerWithRankByCardsComparator.compare(plr1, plr2);
    }

    private static class PlayerWithRankByRankComparator implements Comparator<PlayerWithRank> {
        @Override
        public int compare(final PlayerWithRank plr1, final PlayerWithRank plr2) {
            return plr1.getRank() - plr2.getRank();
        }
    }

    private static class PlayerWithRankByCardsComparator implements Comparator<PlayerWithRank> {
        @Override
        public int compare(final PlayerWithRank plr1, final PlayerWithRank plr2) {
            final CardsUtils cardsUtils = new CardsUtils();
            final String sortedCardsAlfPl1 = cardsUtils.cardsToString(plr1.getPlayer().getCards());
            final String sortedCardsAlfPl2 = cardsUtils.cardsToString(plr2.getPlayer().getCards());

            return sortedCardsAlfPl1.compareTo(sortedCardsAlfPl2);
        }
    }
}
