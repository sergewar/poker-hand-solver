package com.sss.holdem.utils;

public class ConsoleWriter implements Writer {
    @Override
    public void writeLine(String string) {
        System.out.println(string);
    }
}
