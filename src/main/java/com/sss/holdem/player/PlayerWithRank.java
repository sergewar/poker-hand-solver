package com.sss.holdem.player;

import com.sss.holdem.card.Card;
import com.sss.holdem.round.checkers.CombinationRank;
import io.vavr.Tuple2;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PlayerWithRank {
    private final int rank;
    private final Player player;
    private final Tuple2<CombinationRank, List<Card>> combinationAndCards;
}
