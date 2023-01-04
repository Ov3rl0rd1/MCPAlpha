package Engine.Rendering;

import org.lwjgl.opengl.GL20;
import Engine.Objects.Camera;
import Engine.Objects.IDrawable;

public class Renderer {

	public Renderer()
	{
		GL20.glEnable(GL20.GL_DEPTH_TEST);
		GL20.glEnable(GL20.GL_ALPHA_TEST);
		GL20.glEnable(GL20.GL_CULL_FACE);
		GL20.glDepthMask(true);
		GL20.glCullFace(GL20.GL_BACK);
	}

	public void RenderGameObject(Camera camera, IDrawable gameObject)
	{
		gameObject.Draw(camera);
	}
}
