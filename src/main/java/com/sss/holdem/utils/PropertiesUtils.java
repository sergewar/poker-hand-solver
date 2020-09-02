package com.sss.holdem.utils;

public final class PropertiesUtils {
    private PropertiesUtils() {
        // hide constructor
    }

    public static boolean isLog() {
        return Boolean.parseBoolean(System.getProperty("logg", "false"));
    }

    public static String getFileIn() {
        return System.getProperty("fileIn", "");
    }

    public static String getFileOut() {
        return System.getProperty("fileOut", "");
    }
}
