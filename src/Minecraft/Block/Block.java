package Minecraft.Block;

import Engine.Utils.Math.Vector2;

public class Block {
    public static final int ATLAS_SIZE = 16;
    public static final float TEXTURE_SIZE = 0.0625f;

    public final int id;
    public final Vector2[] uv = new Vector2[6];
    public final boolean isTransparent, isSolid, isLightSource, isBlendable;
    public final int lightLevel;

    public Block(int id, boolean isTransparent, boolean isSolid, boolean isLightSource, boolean isBlendable, int lightLevel)
    {
        int x = id % ATLAS_SIZE;
        int y = id / ATLAS_SIZE;
        this.id = id;
        for(int i = 0; i < uv.length; i++)
            uv[i] = new Vector2(x * TEXTURE_SIZE, y * TEXTURE_SIZE);
        this.isTransparent = isTransparent;
        this.isSolid = isSolid;
        this.isLightSource = isLightSource;
        this.lightLevel = lightLevel;
        this.isBlendable = isBlendable;
    }

    public Block(int id, int sideId, int topId, int bottomId, boolean isTransparent, boolean isSolid, boolean isLightSource, boolean isBlendable, int lightLevel)
    {
        this.id = id;
        this.isTransparent = isTransparent;
        this.isSolid = isSolid;
        this.isLightSource = isLightSource;
        this.lightLevel = lightLevel;

        uv[0] = new Vector2((sideId % ATLAS_SIZE) * TEXTURE_SIZE, (sideId / ATLAS_SIZE) * TEXTURE_SIZE);
        uv[1] = new Vector2((sideId % ATLAS_SIZE)  * TEXTURE_SIZE, (sideId / ATLAS_SIZE) * TEXTURE_SIZE);
        uv[2] = new Vector2((sideId % ATLAS_SIZE)  * TEXTURE_SIZE, (sideId / ATLAS_SIZE) * TEXTURE_SIZE);
        uv[3] = new Vector2((sideId % ATLAS_SIZE)  * TEXTURE_SIZE, (sideId / ATLAS_SIZE) * TEXTURE_SIZE);
        uv[4] = new Vector2((topId % ATLAS_SIZE)  * TEXTURE_SIZE, (topId / ATLAS_SIZE) * TEXTURE_SIZE);
        uv[5] = new Vector2((bottomId % ATLAS_SIZE)  * TEXTURE_SIZE, (bottomId / ATLAS_SIZE) * TEXTURE_SIZE);
        this.isBlendable = isBlendable;
    }
    
    public static final Block GRASS = new Block(0, 3, 0, 2, false, true, false, false, 0);
    public static final Block DIRT = new Block(2, false, true, false, false, 0);
    public static final Block STONE = new Block(1, false, true, false, false, 0);
    public static final Block BEDROCK = new Block(17, false, true, false, false, 0);
    public static final Block SAND = new Block(18, false, true, false, false, 0);
    public static final Block WATER = new Block(205, true, false, false, true, 0);
}