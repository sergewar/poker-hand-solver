package com.sss.holdem.player;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PlayerWithRank {
    private final int rank;
    private final Player player;
}
