package com.flickfinder.util;

public class Utils {
    /**
     * Returns the first non-null value.
     *
     * @param one the first value
     * @param two the second value
     * @return the first non-null value
     */
    public static <T> T coalesce(T one, T two) {
        return one != null ? one : two;
    }

    /**
     * Removes - sign from the beginning of input string if present.
     *
     * @param input the input string
     * @return modified input string without - sign
     */
    public static String abs(String input) {
        return input.startsWith("-") ? input.substring(1) : input;
    }
}
