package dev.phonis.factions_tweaks.util;

public class MathUtil
{

    public static float clamp(float value, float min, float max)
    {
        if (value < min)
        {
            return min;
        }
        else
        {
            return Math.min(value, max);
        }
    }

    public static boolean epsilonEquals(double a, double b)
    {
        return Math.abs(a - b) < 1.0E-5;
    }

}
