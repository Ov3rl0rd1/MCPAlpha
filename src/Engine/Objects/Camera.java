package Engine.Objects;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.Sys;

import Engine.Physics.Physics;
import Engine.Physics.RaycastResult;
import Engine.Utils.Math.Mathf;
import Engine.Utils.Math.Vector3;
import Engine.io.Input;
import Minecraft.World.ChunkManager;

public class Camera extends GameObject {
    public float fov, near, far;

    private ChunkManager chunkManager;
    private float moveSpeed = 0.1f, mouseSensitivity = 0.36f;
    private double oldMouseX = 0, oldMouseY = 0, newMouseX = 0, newMouseY = 0;
    private float aspect;

    public Camera(float aspect, ChunkManager chunkManager) {
        this(chunkManager, new Vector3(), new Vector3(), 70, 0.1f, 1000f, aspect);
    }

    public Camera(ChunkManager chunkManager, float fov, float near, float far, float aspect) {
        this(chunkManager, new Vector3(), new Vector3(), fov, near, far, aspect);
    }

    public Camera(ChunkManager chunkManager, Vector3 position, Vector3 rotation, float fov, float near, float far, float aspect)
    {
        super(position, rotation);
        this.chunkManager = chunkManager;
        this.fov = fov;
        this.near = near;
        this.far = far;
        this.aspect = aspect;
    }

    public void Update()
    {
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();

        float speed = moveSpeed;

        if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL))
            speed *= 8;

        float x = (float)Math.sin(Math.toRadians(rotation.y)) * speed;
        float z = (float)Math.cos(Math.toRadians(rotation.y)) * speed;

        Vector3 direction = new Vector3();

        if(Input.isKeyDown(GLFW.GLFW_KEY_A))
            direction = Vector3.Add(direction, new Vector3(-z, 0, x));
        if(Input.isKeyDown(GLFW.GLFW_KEY_D))
            direction = Vector3.Add(direction, new Vector3(z, 0, -x));
        if(Input.isKeyDown(GLFW.GLFW_KEY_W))
            direction = Vector3.Add(direction, new Vector3(-x, 0, -z));
        if(Input.isKeyDown(GLFW.GLFW_KEY_S))
            direction = Vector3.Add(direction, new Vector3(x, 0, z));
        if(Input.isKeyDown(GLFW.GLFW_KEY_SPACE))
            direction = Vector3.Add(direction, new Vector3(0, speed, 0));
        if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
            direction = Vector3.Add(direction, new Vector3(0, -speed, 0));
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT))
        {
            RaycastResult rayCast = Physics.Raycast(position, CalculateForward(), 5f);
            if(rayCast.hit)
            {
                System.out.println(rayCast.block.id);
                System.out.println(rayCast.point);
                System.out.println(rayCast.normal);
            }
            else
                System.out.println("null");
        }

        position = Vector3.Add(direction, position);

        float dx = (float)(newMouseX - oldMouseX);
        float dy = (float)(newMouseY - oldMouseY);

        Vector3 delta = new Vector3(-dy * mouseSensitivity, -dx * mouseSensitivity, 0);
        rotation = Vector3.Add(rotation, delta);

        rotation.y = Mathf.Repeat(rotation.y, 0, 360);
        rotation.x = Mathf.Clamp(rotation.x, -89.5f, 89.5f);

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;

        chunkManager.Update(position);
    }

    public float getAspect()
    {
        return aspect;
    }
}