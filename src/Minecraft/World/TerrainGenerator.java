package Minecraft.World;

import java.util.Random;

import Minecraft.World.Noise.ContinentalnessNoise;

public class TerrainGenerator {
    public static final int OceanLevel = 63;

    private final ContinentalnessNoise noise;

    public TerrainGenerator()
    {
        int seed = new Random().nextInt(2147483647);
        noise = new ContinentalnessNoise(seed);
    }

    public int GetHeight(int x, int y)
    {
        return noise.Get(x, y);
    }

    public int GetSpawnPoint()
    {
        return Math.max(OceanLevel, noise.Get(0, 0)) + 4;
    }
}
