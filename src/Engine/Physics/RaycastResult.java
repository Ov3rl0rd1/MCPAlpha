package Engine.Physics;

import Engine.Utils.Math.Vector3;
import Engine.Utils.Math.Vector3Int;
import Minecraft.Block.BlockFormat;

public class RaycastResult {
    public final Vector3 point;
    public final Vector3Int blockPosition;
    public final BlockFormat block;
    public final Vector3 normal;
    public final boolean hit;

    public RaycastResult(Vector3 point, Vector3Int blockPosition, Vector3 normal, boolean hit) {
        this.point = point;
        this.blockPosition = blockPosition;
        this.normal = normal;
        this.hit = hit;

        block = null;
    }

    public RaycastResult(Vector3 point, Vector3Int blockPosition, BlockFormat block, Vector3 normal, boolean hit) {
        this.point = point;
        this.blockPosition = blockPosition;
        this.block = block;
        this.normal = normal;
        this.hit = hit;
    }
}
