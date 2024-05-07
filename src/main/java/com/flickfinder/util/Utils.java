package com.flickfinder.util;

public class Utils {
    /**
     * Returns the first non-null value.
     * Equivalent to the JS ?? operator.
     *
     * @param one the first value
     * @param two the second value
     * @return the first non-null value
     */
    public static <T> T coalesce(T one, T two) {
        return one != null ? one : two;
    }
}
