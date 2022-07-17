package com.example.radiotest;

public class RadioUtils {
    private static final float  DELTA       = 0.0001f;

    public static final float   MIN_FREQ    = 87.0f;    //fm in MHz
    public static final float   MAX_FREQ    = 109.0f;   //fm in MHz

    public static boolean estimatedEqual(float val1, float val2){
        return (Math.abs(val2 - val1) < DELTA);
    }
}

