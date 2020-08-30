package com.sss.holdem.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerWithRank {
    private final int rank;
    private final Player player;
}
