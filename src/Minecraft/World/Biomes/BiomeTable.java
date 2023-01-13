package Minecraft.World.Biomes;

public class BiomeTable {
    private final Biome[][] table;

    public BiomeTable(Biome[][] table)
    {
        this.table = table;
    }

    public Biome GetBiome(float x, float y)
    {
        int width = table.length;
        int height = table[0].length;

        int targetX = (int)((x / 2 + 0.5f) * width);
        int targetY = (int)((y / 2 + 0.5f) * height);

        return table[targetX][targetY];
    }
}
