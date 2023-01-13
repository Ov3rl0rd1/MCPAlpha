package Minecraft.World.Noise;

import Minecraft.World.FastNoiseLite;
import Minecraft.World.FastNoiseLite.NoiseType;

public class ErosionNoise {
    private final static Curve erosionCurve = new Curve(new Keyframe[] 
    {
        new Keyframe(-1f, 256),
        new Keyframe(-0.8f, 200),
        new Keyframe(-0.4f, 130),
        new Keyframe(-0.3f, 150),
        new Keyframe(0.1f, 64),
        new Keyframe(0.5f, 50),
        new Keyframe(0.55f, 100),
        new Keyframe(0.7f, 100),
        new Keyframe(0.75f, 50),
        new Keyframe(1f, 40),
    });
    private final FastNoiseLite erosionNoise;
    private final float scale;

    public ErosionNoise(int seed)
    {
        erosionNoise = new FastNoiseLite();
        erosionNoise.SetNoiseType(NoiseType.Perlin);
        erosionNoise.SetSeed(seed);
        erosionNoise.SetFractalOctaves(4);
        scale = 0.1f;
    }

    public int Get(int x, int y)
    {
        return erosionCurve.Evaluate(erosionNoise.GetNoise(x * scale, y * scale));
    }
}
