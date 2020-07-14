package com.castro.mmf.data;

public class Painting {

    public static long startTime;
    public static String status;
    public static int picked;

    public static final String formatTime(final long ms) {
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60;
        m %= 60;
        h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}
