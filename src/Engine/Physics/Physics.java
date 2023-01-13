package Engine.Physics;

import Engine.Rendering.Debug.DebugDrawer;
import Engine.Utils.Math.Vector3;
import Engine.Utils.Math.Vector3Int;
import Minecraft.Block.BlockFormat;
import Minecraft.World.ChunkManager;

public class Physics {
    private static ChunkManager chunkManager;

    public static void Init(ChunkManager chunkManager)
    {
        Physics.chunkManager = chunkManager;
    }

    public static RaycastResult Raycast(Vector3 origin, Vector3 direction, float maxDistance)
    {
        Vector3Int blockCenter = origin.ceilNotY();
        Vector3Int blockCenterToOriginSign = Vector3Int.Subtract(blockCenter, origin).sign();
		System.out.println("Origin: " + blockCenter + " Direction: " + direction);

        Vector3Int step = direction.sign();

		Vector3 goodNormalDirection = new Vector3(
            (float)(direction.x == 0.0f ? 1e-10 * blockCenterToOriginSign.x : direction.x),
            (float)(direction.y == 0.0f ? 1e-10 * blockCenterToOriginSign.y : direction.y),
            (float)(direction.z == 0.0f ? 1e-10 * blockCenterToOriginSign.z : direction.z));


        RaycastResult result;
        if ((result = doRaycast(origin, direction, blockCenter, step)).hit)
		{
			return result;
		}

		Vector3 tDelta;
        Vector3 tMax;

        float minTValue = 0;
        do
        {
            tDelta = Vector3.Divide(Vector3Int.Subtract(blockCenter, origin), goodNormalDirection);
            tMax = tDelta;

            if (tMax.x < tMax.y)
            {
                if (tMax.x < tMax.z)
                {
                    blockCenter.x += step.x;
                    minTValue += tDelta.x;
                }

                else
                {
                    blockCenter.z += step.z;
					minTValue += tDelta.z;
                }
            }
            else
            {
                if (tMax.y < tMax.z)
                {
                    blockCenter.y += step.y;
					minTValue += tDelta.y;
                }

                else
                {
                    blockCenter.z += step.z;
					minTValue += tDelta.z;
                }
            }

            System.out.println(blockCenter.toString());

            if ((result = doRaycast(origin, direction, blockCenter, step)).hit)
			{
				return result;
			}

        } while(minTValue < maxDistance);

        return new RaycastResult(null, null, null, false);
    }

    public static RaycastResult doRaycast(Vector3 origin, Vector3 direction, Vector3Int blockCorner, Vector3Int step)
    {
        DebugDrawer.AddCube(blockCorner, new Vector3(1, 0, 0));
		//System.out.println(blockCorner.toString());
        BlockFormat block = chunkManager.GetBlock(blockCorner);
        if(block == null)
            return new RaycastResult(null, null, null, false);

        Vector3 blockCenter = Vector3Int.Add(blockCorner, (Vector3Int.Multiply(step, new Vector3(0.5f))));

        Vector3 min = Vector3.Subtract(blockCenter, new Vector3(0.5f));
        Vector3 max = Vector3.Add(blockCenter, new Vector3(0.5f));

        Vector3 invDirection = Vector3.Divide(new Vector3(1), direction);

        float t1 = (min.x - origin.x) * (invDirection.x);
        float t2 = (max.x - origin.x) * (invDirection.x);
        float t3 = (min.y - origin.y) * (invDirection.y);
        float t4 = (max.y - origin.y) * (invDirection.y);
        float t5 = (min.z - origin.z) * (invDirection.z);
        float t6 = (max.z - origin.z) * (invDirection.z);

        float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));
            
        float depth = tmax > Math.max(tmin, 0.0) ? tmin : -1;

        if(depth == -1)
            return new RaycastResult(null, null, null, false);

        Vector3 point = Vector3.Add(origin, Vector3.Multiply(direction, depth));
        Vector3 hitNormal = Vector3.Subtract(blockCenter, point);

        float maxComponent = Math.max(Math.abs(hitNormal.x), Math.max(Math.abs(hitNormal.y), Math.abs(hitNormal.z)));
        hitNormal = Math.abs(hitNormal.x) == maxComponent
						? Vector3.Multiply(new Vector3(1, 0, 0), Math.signum(hitNormal.x))
						: Math.abs(hitNormal.y) == maxComponent
						? Vector3.Multiply(new Vector3(0, 1, 0), Math.signum(hitNormal.y))
						: Vector3.Multiply(new Vector3(0, 0, 1), Math.signum(hitNormal.z));
        return new RaycastResult(point, blockCorner, block, hitNormal, true);
    }
}
