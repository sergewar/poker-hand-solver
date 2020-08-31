package com.sss.holdem.utils;

public class ConsoleWriter implements Writer {
    @Override
    public void writeLine(final String string) {
        System.out.println(string);
    }

    @Override
    public void close() {
    }
}
