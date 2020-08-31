package com.sss.holdem.utils;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FFileReader implements Reader {
    private final BufferedReader br;

    @SneakyThrows
    public FFileReader(final String filename) {
        this.br = new BufferedReader(new FileReader(new File(filename)));
    }

    @Override
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


