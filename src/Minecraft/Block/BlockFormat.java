package Minecraft.Block;

import Engine.Utils.Math.Vector2;

public class BlockFormat {
    public final Vector2[] uvAtlas;
    public final boolean isTransparent, isSolid, isLightSource, isBlendable;
    public int lightLevel = 0, id = 0;

    public BlockFormat(Block block)
    {
        uvAtlas = block.uv;
        id = block.id;
        isTransparent = block.isTransparent;
        isLightSource = block.isLightSource;
        isSolid = block.isSolid;
        isBlendable = block.isBlendable;
    }
}
