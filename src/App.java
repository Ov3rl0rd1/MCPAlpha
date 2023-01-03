import Engine.Rendering.CubeMesh;
import Engine.Rendering.Material;
import Engine.Rendering.Mesh;

import java.nio.FloatBuffer;
import java.util.EnumSet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;
import org.lwjglx.test.spaceinvaders.Game;

import Engine.Objects.Camera;
import Engine.Objects.GameObject;
import Engine.Objects.Scene;
import Engine.Physics.Physics;
import Engine.Rendering.Shader;
import Engine.Rendering.CubeMesh.Faces;
import Engine.Utils.Math.Vector3;
import Engine.io.Input;
import Engine.io.Window;
import Minecraft.Block.Block;
import Minecraft.Block.BlockFormat;
import Minecraft.World.ChunkManager;

public class App implements Runnable {
	public Thread game;
	public Window window;
	public final int WIDTH = 1280, HEIGHT = 720;

	private Camera camera;
	private Scene scene;
	private Material material;
	private ChunkManager chunkManager;

	public static void main(String[] args) {
		new App().Start();
	}

	public void Start() {
		game = new Thread(this, "game");
		game.start();
	}
	
	public void Init() {
		window = new Window(WIDTH, HEIGHT, "Game");
		window.SetBackgroundColor(0.191f, 0.804f, 0.836f);
		window.Create();
		InitScene();
	}

	private void InitScene()
	{
		Shader shader = new Shader("/resources/shaders/mainVertex.glsl", "/resources/shaders/mainFragment.glsl");
		material = new Material("/resources/textures/terrain.png", shader);
		scene = new Scene();
		
		chunkManager = new ChunkManager(material, scene);
		Physics.Init(chunkManager);
		camera = new Camera((float)WIDTH / (float)HEIGHT, chunkManager);
		chunkManager.Init(camera);
	}
	
	public void run() {
		Init();
		while (!window.ShouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			update();
			render();
			if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.SetFullscreen(!window.IsFullscreen());
			if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.MouseState(true);
		}
		Close();
	}

	private void update() {
		window.Update();
		//gameObject.Update();
		camera.Update();
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) 
		{
		}
	}

	private void Close()
	{
		window.Destroy();
		scene.Destroy();
		material.Destroy();
	}
	
	private void render() {
		scene.Render(camera);
		window.SwapBuffers();
	}

}