package Minecraft.World;

import java.util.EnumSet;

import Engine.Objects.Camera;
import Engine.Objects.GameObject;
import Engine.Rendering.CubeMesh;
import Engine.Rendering.Material;
import Engine.Rendering.Mesh;
import Engine.Rendering.CubeMesh.Faces;
import Engine.Utils.Math.Matrix4f;
import Engine.Utils.Math.Vector2Int;
import Engine.Utils.Math.Vector3;
import Engine.Utils.Math.Vector3Int;
import Minecraft.Block.Block;
import Minecraft.Block.BlockFormat;

public class Chunk extends GameObject {
    public static final int Width = 16;
    public static final int Height = 256;

    public final Vector2Int chunkPoistion;
    
    private BlockFormat[][][] data = new BlockFormat[Width][Height][Width];
    private final Mesh transparentMesh;
    private TerrainGenerator terrainGenerator;
    private ChunkManager chunkManager;
    private boolean _data;
    private boolean _mesh;

    public Chunk(Material material, Vector2Int chunkPoistion, TerrainGenerator terrainGenerator, ChunkManager chunkManager)
    {
        super(material, new Mesh());
        this.terrainGenerator = terrainGenerator;
        this.chunkPoistion = chunkPoistion;
        this.chunkManager = chunkManager;
        transparentMesh = new Mesh();
        position = new Vector3(chunkPoistion.x * Width, 0, chunkPoistion.y * Width);
    }

    public void GenerateData()
    {
        if(_data)
            return;

        for(int x = 0; x < Width; x++)
        {
            for(int z = 0; z < Width; z++)
            {
                int height = terrainGenerator.GetHeight((int)position.x + x, (int)position.z + z);
                for(int y = 0; y < Math.max(height, TerrainGenerator.OceanLevel); y++)
                {
                    if(y == 0)
                    {
                        data[x][y][z] = new BlockFormat(Block.BEDROCK);
                    }
                    else if(y < height - 2)
                    {
                        data[x][y][z] = new BlockFormat(Block.STONE);
                    }
                    else if(y < height - 1)
                    {
                        if(y+1 < TerrainGenerator.OceanLevel)
                            data[x][y][z] = new BlockFormat(Block.SAND);
                        else
                            data[x][y][z] = new BlockFormat(Block.DIRT);
                    }
                    else if(y > height)
                    {
                        data[x][y][z] = new BlockFormat(Block.WATER);
                    }
                    else
                    {
                        data[x][y][z] = new BlockFormat(y > TerrainGenerator.OceanLevel ? Block.GRASS : Block.SAND);
                    }
                }
            }
        }

        _data = true;
    }

    public void GenerateMesh()
    {
        if(_mesh)
            return;

        for(int x = 0; x < Width; x++)
        {
            for(int z = 0; z < Width; z++)
            {
                for(int y = 0; y < Height; y++)
                {
                    if(data[x][y][z] == null)
                        continue;

                    final int xCoords[] = { x, x, x + 1, x - 1, x, x };
                    final int yCoords[] = { y, y, y, y, y + 1, y - 1};
                    final int zCoords[] = { z - 1, z + 1, z, z, z, z };

                    EnumSet<Faces> faces = EnumSet.noneOf(Faces.class);
                    for(int i = 0; i < 6; i++)
                    {
                        Vector3Int sideBlock = new Vector3Int(xCoords[i], yCoords[i], zCoords[i]);

                        if(sideBlock.y < 0 || sideBlock.y >= Height)
                        {
                            faces.add(Faces.fromInteger(i));
                            continue;
                        }

                        BlockFormat block = chunkManager.GetBlock(chunkPoistion, sideBlock);
                        if(block == null)
                        {
                            faces.add(Faces.fromInteger(i));
                            continue;
                        }
                        else if(data[x][y][z].isBlendable == true)
                            continue;
                        else if(block.isSolid == false || block.isTransparent)
                            faces.add(Faces.fromInteger(i));
                    }
                    
                    if(data[x][y][z].isTransparent == false)
                        CubeMesh.AddToMesh(faces, mesh, new Vector3(x, y, z), data[x][y][z]);
                    else
                        CubeMesh.AddToMesh(faces, transparentMesh, new Vector3(x, y, z), data[x][y][z]);
                }
            }
        }

        mesh.Build();
        transparentMesh.Build();

        _mesh = true;
    }

    public BlockFormat GetBlock(Vector3Int localPosition)
    {
        return data[localPosition.x][localPosition.y][localPosition.z];
    }

    public int GetSpawnHeight(Vector2Int localPos)
    {
        for(int y = Height-1; y > 0; y--)
        {
            if(data[localPos.x][y][localPos.y] != null)
                return y;
        }

        return 0;
    }

    public void Draw(Camera camera)
    {
        mesh.Render(material, Matrix4f.Transform(position, rotation, scale), camera, false);
        transparentMesh.Render(material, Matrix4f.Transform(position, rotation, scale), camera, true);
    }

    public boolean IsMeshGenerated()
    {
        return _mesh;
    }

    public boolean IsDataGenerated()
    {
        return _data;
    }
}
