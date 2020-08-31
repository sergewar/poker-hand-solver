package com.sss.holdem.utils;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;

public class FFileWriter implements Writer {
    private final FileOutputStream fos;

    @SneakyThrows
    public FFileWriter(final String filename) {
        this.fos = new FileOutputStream(new File(filename));
    }

    @Override
    @SneakyThrows
    public void writeLine(final String string) {
        fos.write(string.getBytes());
        fos.write("\n".getBytes());
    }

    @Override
    @SneakyThrows
    public void close() {
        fos.close();
    }
}
