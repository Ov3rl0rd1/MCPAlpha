package Engine.Rendering.Debug;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import Engine.Rendering.Shader;
import Engine.Utils.Math.Matrix4f;
import Engine.Utils.Math.Vector3;

public class DebugLine extends DebugDrawable {
    private final Vector3 origin, end;
    private int vao, vbo;

    public DebugLine(Vector3 origin, Vector3 end, Vector3 color)
    {
        super(color);
        this.origin = origin;
        this.end = end;

        Build();
    }


    @Override
    public void Draw(Shader shader, Matrix4f projectionMat, Matrix4f viewMat) {
        shader.Bind();
        shader.SetUniform("projection", projectionMat);
        shader.SetUniform("view", viewMat);
        shader.SetUniform("debugColor", color);
        GL30.glBindVertexArray(vao);
        GL20.glDrawArrays(GL20.GL_LINES, 0, 2);
        shader.Unbind();
    }

    @Override
    public void Destroy()
    {
        GL30.glDeleteVertexArrays(vao);
        GL15.glDeleteBuffers(vbo);
    }

    private void Build()
    {
        float[] vertices = {
            origin.x, origin.y, origin.z,
            end.x, end.y, end.z,
        };

        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        vao = GL30.glGenVertexArrays();
        vbo = GL15.glGenBuffers();
        GL30.glBindVertexArray(vao);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(0);

        GL20.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); 
        GL30.glBindVertexArray(0); 
    }
}
