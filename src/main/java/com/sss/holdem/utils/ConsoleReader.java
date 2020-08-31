package com.sss.holdem.utils;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleReader implements Reader {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @SneakyThrows
    public String readLine() {
        return br.readLine();
    }

    @Override
    @SneakyThrows
    public void close() {
        br.close();
    }
}
