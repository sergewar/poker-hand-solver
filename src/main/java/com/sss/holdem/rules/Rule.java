package com.sss.holdem.rules;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Rule {
    private final int countOfBoardsCars = 5;
    private final int countOfPlayersCards;
    private final boolean isOmahaRule;

    public Rule(final boolean isOmahaRule) {
        this.isOmahaRule = isOmahaRule;
        if (isOmahaRule) {
            this.countOfPlayersCards = 4;
        } else {
            this.countOfPlayersCards = 2;
        }
    }
}
