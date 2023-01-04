package Engine.Rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import Engine.Objects.Camera;
import Engine.Utils.Math.*;

public class Mesh {
    public List<Vector3> vertices = new ArrayList<Vector3>();
    public List<Integer> triangles = new ArrayList<Integer>();
    public List<Vector2> uvs = new ArrayList<Vector2>();

    private boolean isGenerated;
    private int vao, pbo, ibo, tbo;

    public Mesh()
    {
    }

    public Mesh(List<Vector3> vertices, List<Integer> triangles, List<Vector2> uvs)
    {
        this.vertices = vertices;
        this.triangles = triangles;
        this.uvs = uvs;
    }

    public void Render(Material material, Matrix4f transform, Camera camera, boolean transparent)
    {
        if(isGenerated == false)
            return;

        if(transparent)
            EnableTransparent();

        GL30.glBindVertexArray(vao);
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, material.getTextureID());
        material.Bind();
        //material.SetUniform("sunlightIntensity", 0f);
        material.SetUniform("model", transform);
        material.SetUniform("projection", Matrix4f.Projection(camera.fov, camera.getAspect(), camera.near, camera.far));
        material.SetUniform("view", Matrix4f.View(camera.position, camera.rotation));
        GL11.glDrawElements(GL11.GL_TRIANGLES, triangles.size(), GL11.GL_UNSIGNED_INT, 0);
        material.Unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);

        if(transparent)
            DisableTransparent();
    }

    public void Build()
    {
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        Object[] verticesArray = vertices.toArray();
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(verticesArray.length * 3);
        float[] positionData = new float[verticesArray.length * 3];
        for(int i = 0; i < verticesArray.length; i++)
        {
            positionData[i*3] = ((Vector3)verticesArray[i]).x;
            positionData[i*3+1] = ((Vector3)verticesArray[i]).y;
            positionData[i*3+2] = ((Vector3)verticesArray[i]).z;
        }
        positionBuffer.put(positionData).flip();

        pbo = StoreData(positionBuffer, 0, 3);

        Object[] uvsArray = uvs.toArray();
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(verticesArray.length * 2);
        float[] textureData = new float[verticesArray.length * 2];
        for(int i = 0; i < verticesArray.length; i++)
        {
            textureData[i*2] = ((Vector2)uvsArray[i]).x;
            textureData[i*2+1] = ((Vector2)uvsArray[i]).y;
        }
        textureBuffer.put(textureData).flip();

        tbo = StoreData(textureBuffer, 2, 2);

        int[] trianglesArray = triangles.stream().mapToInt(Integer::intValue).toArray();
        IntBuffer trianglesBuffer = MemoryUtil.memAllocInt(trianglesArray.length);
        trianglesBuffer.put(trianglesArray).flip();

        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, trianglesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        MemoryUtil.memFree(trianglesBuffer);

        isGenerated = true;
    }

    public void Add(Collection<Vector3> verts, Collection<Integer> tris, Collection<Vector2> uvs)
    {
        Destroy();

        vertices.addAll(verts);
        triangles.addAll(tris);
        this.uvs.addAll(uvs);
    }

    public void Destroy()
    {
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(ibo);
        GL15.glDeleteBuffers(tbo);

        GL30.glDeleteVertexArrays(vao);
    }

    private void EnableTransparent()
    {
        GL20.glEnable(GL20.GL_BLEND);
        GL20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void DisableTransparent()
    {
        GL20.glDisable(GL20.GL_BLEND);
    }

    private int StoreData(FloatBuffer buffer, int index, int size)
    {
        int bufferId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        MemoryUtil.memFree(buffer);
        return bufferId;
    }
}
