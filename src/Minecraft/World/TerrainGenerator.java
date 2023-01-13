package Minecraft.World;

import java.util.Random;

import Minecraft.Block.BlockFormat;
import Minecraft.World.Noise.ContinentalnessNoise;
import Minecraft.World.Noise.ErosionNoise;

public class TerrainGenerator {
    public static final int OceanLevel = 63;
    public static final int MaxHeight = 240;

    private final Random randomGenerator;
    private final ContinentalnessNoise continentalnessNoise;
    private final ErosionNoise erosionNoise;
    private final FastNoiseLite humidityNoise;

    public TerrainGenerator()
    {
        int seed = new Random().nextInt(2147483647);
        randomGenerator = new Random(seed);
        continentalnessNoise = new ContinentalnessNoise(seed);
        erosionNoise = new ErosionNoise(seed);
        humidityNoise = new FastNoiseLite(seed);

    }

    public int GetHeight(int x, int y)
    {
        return (continentalnessNoise.Get(x, y) + erosionNoise.Get(x, y)) / 2;
    }

    public BlockFormat GetBlock(int x, int y, int z)
    {
        return null;
    }

    public int GetSpawnPoint()
    {
        return Math.max(OceanLevel, GetHeight(0, 0)) + 4;
    }
}
