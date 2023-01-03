package Engine.Utils.Math;

import java.lang.Math;

public final class Mathf {
    public final static double Deg2Rad = Math.PI / 180;
    public final static float Epsilon = 0.00000001f;

    public static float Clamp(float value, float min, float max)
    {
        return Math.max(min, Math.min(max, value));
    }

    public static float Repeat(float value, float min, float max)
    {
        if(value > max)
            return max - value;
        if(value < min)
            return max + value;

        return value;
    }

    public static float CastToFloat(double value)
    {
        float fValue = (float)value;
        if(Math.abs(fValue) < Epsilon)
            fValue = 0;
        return fValue;
    }
}
