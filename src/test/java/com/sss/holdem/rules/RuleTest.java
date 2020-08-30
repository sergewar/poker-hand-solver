package com.sss.holdem.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleTest {

    @Test
    void testOmahaOff() {
        final boolean isOmahaRule = false;

        final Rule rule = new Rule(isOmahaRule);
        assertAll(
                () -> assertEquals(2, rule.getCountOfPlayersCards()),
                () -> assertEquals(5, rule.getCountOfBoardsCars())
        );
    }

    @Test
    void testOmahaOn() {
        final boolean isOmahaRule = true;
        final Rule rule = new Rule(isOmahaRule);
        assertAll(
                () -> assertEquals(4, rule.getCountOfPlayersCards()),
                () -> assertEquals(5, rule.getCountOfBoardsCars())
        );
    }
}