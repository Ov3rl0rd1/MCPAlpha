package Minecraft.World.Noise;

import Minecraft.World.FastNoiseLite;
import Minecraft.World.FastNoiseLite.NoiseType;

public class ContinentalnessNoise {
    
    private final static Curve continentalnessCurve = new Curve(new Keyframe[] 
    {
        new Keyframe(-1f, 42),
        new Keyframe(-0.24f, 50),
        new Keyframe(0, 55),
        new Keyframe(0.015f, 63),
        new Keyframe(0.1f, 68),
        new Keyframe(0.5f, 70),
        new Keyframe(0.65f, 150),
        new Keyframe(1f, 240),
    });
    private final FastNoiseLite continentalnessNoise;
    private final float scale;

    public ContinentalnessNoise(int seed)
    {
        continentalnessNoise = new FastNoiseLite();
        continentalnessNoise.SetNoiseType(NoiseType.Perlin);
        continentalnessNoise.SetSeed(seed);
        continentalnessNoise.SetFractalOctaves(4);
        scale = 0.1f;
    }

    public int Get(int x, int y)
    {
        return continentalnessCurve.Evaluate(continentalnessNoise.GetNoise(x * scale, y * scale));
    }
}
