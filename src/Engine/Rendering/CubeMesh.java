package Engine.Rendering;

import java.util.EnumSet;

import Engine.Utils.Math.Vector2;
import Engine.Utils.Math.Vector3;
import Engine.Utils.Math.Vector3Int;
import Minecraft.Block.Block;
import Minecraft.Block.BlockFormat;

public final class CubeMesh {
    public enum Faces
    {
        Back,
        Front,
        Right,
        Left,
        Top,
        Bottom;

        public long getStatusFlagValue(){
            return 1 << this.ordinal();
        }

        public static Faces fromInteger(int x) {
            switch(x) {
                case 0:
                    return Back;
                case 1:
                    return Front;
                case 2:
                    return Right;
                case 3:
                    return Left;
                case 4:
                    return Top;
                case 5:
                    return Bottom;
            }
            return null;
        }
    }

    public static final Vector3[][] vertices = new Vector3[][]
    {
        //Back
        new Vector3[] 
        {
            new Vector3(0,  1, 0),
            new Vector3(0, 0, 0),
            new Vector3(1, 0, 0),
            new Vector3(1,  1, 0)
        },
        //Front
        new Vector3[] 
        {
            new Vector3(0,  1,  1),
            new Vector3(0, 0,  1),
            new Vector3(1, 0,  1),
            new Vector3(1,  1,  1)
        },
        //Right
        new Vector3[] 
        {
            new Vector3(1,  1, 0),
            new Vector3(1, 0, 0),
            new Vector3(1, 0,  1),
            new Vector3(1,  1,  1)
        },
        //Left
        new Vector3[] 
        {
            new Vector3(0,  1, 0),
            new Vector3(0, 0, 0),
            new Vector3(0, 0,  1),
            new Vector3(0,  1,  1)
        },
        //Top
        new Vector3[] 
        {
            new Vector3(0,  1,  1),
            new Vector3(0,  1, 0),
            new Vector3(1,  1, 0),
            new Vector3(1,  1,  1)
        },
        //Bottom
        new Vector3[] 
        {
            new Vector3(0, 0,  1),
            new Vector3(0, 0, 0),
            new Vector3(1, 0, 0),
            new Vector3(1, 0,  1)
        },
    };
    public static final int[] triangles = new int[]
    {
            //Back face
			1, 0, 3,	
			1, 3, 2,	
			
			//Front face
			0, 1, 3,	
			3, 1, 2,
			
			//Right face
			1, 0, 3,	
			1, 3, 2,
			
			//Left face
			0, 1, 3,	
			3, 1, 2,
			
			//Top face
			1, 0, 3,	
			1, 3, 2,
			
			//Bottom face
			0, 1, 3,	
			3, 1, 2,
    };

    public static final Vector2[][] uvs = new Vector2[][] 
    {
        new Vector2[]
        {
            //Back face
			new Vector2(0.0f, 0.0f),
			new Vector2(0.0f, 1.0f),
			new Vector2(1.0f, 1.0f),
			new Vector2(1.0f, 0.0f),
        },
        new Vector2[]
        {
            //Front face
			new Vector2(0.0f, 0.0f),
			new Vector2(0.0f, 1.0f),
			new Vector2(1.0f, 1.0f),
			new Vector2(1.0f, 0.0f),
        },
        new Vector2[]
        {
            //Right face
			new Vector2(0.0f, 0.0f),
			new Vector2(0.0f, 1.0f),
			new Vector2(1.0f, 1.0f),
			new Vector2(1.0f, 0.0f),
        },
        new Vector2[]
        {
            //Left face
			new Vector2(0.0f, 0.0f),
			new Vector2(0.0f, 1.0f),
			new Vector2(1.0f, 1.0f),
			new Vector2(1.0f, 0.0f),
        },
        new Vector2[]
        {
            //Top face
			new Vector2(0.0f, 0.0f),
			new Vector2(0.0f, 1.0f),
			new Vector2(1.0f, 1.0f),
			new Vector2(1.0f, 0.0f),
        },
        new Vector2[]
        {
            //Bottom face
			new Vector2(0.0f, 0.0f),
			new Vector2(0.0f, 1.0f),
			new Vector2(1.0f, 1.0f),
			new Vector2(1.0f, 0.0f),
        },
    };

    public static void AddToMesh(EnumSet<Faces> faces, Mesh mesh, Vector3Int position, BlockFormat block)
    {
        int currentIndex = 0;
        int verticesCount = mesh.vertices.size();
        for (Faces face : faces) {
            int index = face.ordinal();

            for(int i = currentIndex*4; i < currentIndex*4+4; i++)
            {
                mesh.vertices.add(Vector3.Add(vertices[index][i-currentIndex*4], position));
            }
            for(int i = currentIndex*4; i < currentIndex*4+4; i++)
            {
                mesh.uvs.add(new Vector2(uvs[index][i-currentIndex*4].x * Block.TEXTURE_SIZE + block.uvAtlas[index].x,
                    uvs[index][i-currentIndex*4].y * Block.TEXTURE_SIZE + block.uvAtlas[index].y));
            }

            for(int i = index*6; i < index*6+6; i++)
            {
                mesh.triangles.add(triangles[i] + verticesCount + currentIndex*4);
            }

            currentIndex+=1;
        }
    }
}
