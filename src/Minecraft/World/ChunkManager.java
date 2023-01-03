package Minecraft.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Engine.Objects.Camera;
import Engine.Objects.Scene;
import Engine.Rendering.Material;
import Engine.Utils.Math.Vector2Int;
import Engine.Utils.Math.Vector3;
import Engine.Utils.Math.Vector3Int;
import Minecraft.Block.BlockFormat;


public class ChunkManager {

    private static final int viewRadius = 8;
    private static final int generateDataRadius = viewRadius+1;

    private Map<Vector2Int, Chunk> chunks = new HashMap<Vector2Int, Chunk>();

    private TerrainGenerator terrainGenerator;
    private final Material material;
    private final Scene scene;

    private Vector2Int oldCameraPosition;

    public ChunkManager(Material material, Scene scene)
    {
        this.material = material;
        this.scene = scene;
        this.terrainGenerator = new TerrainGenerator();
    }

    public void Init(Camera camera)
    {
        oldCameraPosition = new Vector2Int((int)camera.position.x / Chunk.Width, (int)camera.position.z / Chunk.Width);
        UpdateChunks(oldCameraPosition);
        camera.position = new Vector3(0, terrainGenerator.GetSpawnPoint(), 0);
    }

    public void Update(Vector3 playerPosition)
    {
        Vector2Int cameraChunkPosition = new Vector2Int((int)playerPosition.x / Chunk.Width, (int)playerPosition.z / Chunk.Width);

        if(Vector2Int.Equals(cameraChunkPosition, oldCameraPosition) == false)
        {
            UpdateChunks(cameraChunkPosition);
        }
    }

    private void UpdateChunks(Vector2Int newPosition)
    {
        oldCameraPosition = newPosition;
        for(int x = (int)newPosition.x-generateDataRadius; x < newPosition.x+generateDataRadius; x++)
        {
            for(int z = (int)newPosition.y-generateDataRadius; z < newPosition.y+generateDataRadius; z++)
            {
                Vector2Int chunkPosition = new Vector2Int(x, z);
                int distance = (int)Vector2Int.distance(newPosition, chunkPosition);
                if(distance <= generateDataRadius)
                    AddChunk(chunkPosition);
            }
        }

        for(int x = (int)newPosition.x-viewRadius; x < newPosition.x+viewRadius; x++)
        {
            for(int z = (int)newPosition.y-viewRadius; z < newPosition.y+viewRadius; z++)
            {
                Vector2Int chunkPosition = new Vector2Int(x, z);
                int distance = (int)Vector2Int.distance(newPosition, chunkPosition);
                if(distance <= viewRadius)
                    AddMeshToChunk(chunkPosition);
            }
        }
        
        List<Vector2Int> farChunks = new ArrayList<Vector2Int>();
        for (Entry<Vector2Int, Chunk> chunk : chunks.entrySet()) {
            if((int)Vector2Int.distance(chunk.getKey(), newPosition) > generateDataRadius)
                farChunks.add(chunk.getKey());
        }

        for (Vector2Int chunk : farChunks) {
            RemoveChunk(chunks.get(chunk));
        }
    }

    private void AddChunk(Vector2Int position)
    {
        boolean contains = chunks.containsKey(position);

        if(contains && chunks.get(position).IsMeshGenerated())
            return;

        Chunk chunk;

        if(contains == false)
        {
            chunk = new Chunk(material, position, terrainGenerator, this);
            chunks.put(position, chunk);
        }
        else
            chunk = chunks.get(position);

        if(chunk.IsDataGenerated() == false)
		    chunk.GenerateData();
        
        if(contains == false)
            scene.AddObject(chunk);
    }

    private void AddMeshToChunk(Vector2Int position)
    {
        if(chunks.get(position).IsMeshGenerated())
            return;

        Chunk chunk = chunks.get(position);

        if(chunk.IsMeshGenerated() == false)
		    chunk.GenerateMesh();
    }

    private void RemoveChunk(Chunk chunk)
    {
        chunks.remove(chunk.chunkPoistion);
        scene.DestroyObject(chunk);
    }

    public boolean CheckForBlock(Vector3Int position)
    {
        if(position.y > Chunk.Height || position.y < 0)
            return false;

        Vector2Int localChunkPosition = new Vector2Int(position.x / Chunk.Width, position.z / Chunk.Width);
        Vector3Int localBlockPosition = new Vector3Int(position.x % Chunk.Width, position.y, position.z % Chunk.Width);

        localBlockPosition.x = localBlockPosition.x < 0 ? Chunk.Width + localBlockPosition.x : localBlockPosition.x;
        localBlockPosition.z = localBlockPosition.z < 0 ? Chunk.Width + localBlockPosition.z : localBlockPosition.z;

        BlockFormat block = GetBlock(localChunkPosition, localBlockPosition);
        if(block == null)
            return false;

        if(block.isSolid == false)
            return false;

        return true;
    }

    public BlockFormat GetBlock(Vector3Int position)
    {
        if(position.y < 0 || position.y > Chunk.Height)
            return null;

        Vector2Int chunkPosition = new Vector2Int(position.x / Chunk.Width, position.z / Chunk.Width);
        Vector3Int block = new Vector3Int(position.x % Chunk.Width, position.y, position.z % Chunk.Width);
        if(block.x < 0)
            block.x = block.x + Chunk.Width;
        if(block.z < 0)
            block.z = block.z + Chunk.Width;

        Chunk chunk = chunks.get(chunkPosition);
        if(chunk == null)
            return null;

        return chunk.GetBlock(block);
    }

    public BlockFormat GetBlock(Vector2Int currentChunkPosition, Vector3Int chunkBlockPosition)
    {
        if(chunkBlockPosition.y > Chunk.Height || chunkBlockPosition.y < 0)
            return null;

        if(chunkBlockPosition.x >= Chunk.Width)
            return chunks.get(Vector2Int.Add(currentChunkPosition, new Vector2Int(1, 0))).GetBlock(new Vector3Int(chunkBlockPosition.x - Chunk.Width, chunkBlockPosition.y, chunkBlockPosition.z));
        else if(chunkBlockPosition.x < 0)
            return chunks.get(Vector2Int.Add(currentChunkPosition, new Vector2Int(-1, 0))).GetBlock(new Vector3Int(Chunk.Width + chunkBlockPosition.x, chunkBlockPosition.y, chunkBlockPosition.z));
        if(chunkBlockPosition.z >= Chunk.Width)
            return chunks.get(Vector2Int.Add(currentChunkPosition, new Vector2Int(0, 1))).GetBlock(new Vector3Int(chunkBlockPosition.x, chunkBlockPosition.y, chunkBlockPosition.z - Chunk.Width));
        else if(chunkBlockPosition.z < 0)
            return chunks.get(Vector2Int.Add(currentChunkPosition, new Vector2Int(0, -1))).GetBlock(new Vector3Int(chunkBlockPosition.x, chunkBlockPosition.y, Chunk.Width + chunkBlockPosition.z));


        return chunks.get(currentChunkPosition).GetBlock(chunkBlockPosition);
    }
}
