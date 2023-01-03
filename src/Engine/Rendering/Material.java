package Engine.Rendering;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Engine.Utils.Math.Matrix4f;
import Engine.Utils.Math.Vector2;
import Engine.Utils.Math.Vector3;

public class Material {
    private Shader shader;
    private String path;
    private Texture texture;
    private float width, height;
    private int textureID;

    public Material(String path, Shader shader)
    {
        this.path = path;
        this.shader = shader;
    }

    public void Create()
    {
        if(texture != null)
            return;

        try {
            texture = TextureLoader.getTexture(path.split("[.]")[1], Material.class.getResourceAsStream(path), GL11.GL_LINEAR);
            width = texture.getWidth();
            height = texture.getHeight();
            textureID = texture.getTextureID();
            GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
		    GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        } catch (IOException e) {
            System.out.println("Texture load error: " + path);
        }

        shader.Compile();
    }

    public void Destroy()
    {
        shader.Destroy();
        GL13.glDeleteTextures(textureID);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }

    //#region
    public void SetUniform(String name, float vaule)
	{
		shader.SetUniform(name, vaule);
	}

	public void SetUniform(String name, int vaule)
	{
		shader.SetUniform(name, vaule);
	}

	public void SetUniform(String name, Boolean vaule)
	{
		shader.SetUniform(name, vaule);
	}

	public void SetUniform(String name, Vector2 vaule)
	{
		shader.SetUniform(name, vaule);
	}
	public void SetUniform(String name, Vector3 vaule)
	{
		shader.SetUniform(name, vaule);
	}
	public void SetUniform(String name, Matrix4f vaule)
	{
		shader.SetUniform(name, vaule);
	}

    public void Bind()
    {
        shader.Bind();
    }

    public void Unbind()
    {
        shader.Unbind();
    }
    //#endregion
}
