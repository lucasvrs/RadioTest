package com.example.radiotest;

/**
 * Holds utils for future use.
 */
public class RadioUtils {
    private static final float  DELTA       = 0.0001f;

    public static boolean estimatedEqual(float val1, float val2){
        return (Math.abs(val2 - val1) < DELTA);
    }
}

