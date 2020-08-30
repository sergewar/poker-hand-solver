package com.sss.holdem.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PlayerWithRank {
    private final int rank;
    private final Player player;
}
