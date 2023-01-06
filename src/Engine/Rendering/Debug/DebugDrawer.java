package Engine.Rendering.Debug;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL20;

import Engine.Objects.Camera;
import Engine.Rendering.Shader;
import Engine.Utils.Math.Matrix4f;
import Engine.Utils.Math.Vector3;
import Engine.Utils.Math.Vector3Int;

public class DebugDrawer {
    
    private static DebugDrawer Instance;

    private final List<DebugDrawable> content = new ArrayList<DebugDrawable>();
    private final Shader debugShader;
    private final Camera camera;

    public DebugDrawer(Camera targetCamera)
    {
        if(Instance == null)
            Instance = this;
        else
            System.err.println("(DebugDrawer): Something wrong!");

        camera = targetCamera;

        debugShader = new Shader("/resources/shaders/debug/debugVertex.glsl", "/resources/shaders/debug/debugFragment.glsl");
        debugShader.Compile();
    }

    public void DrawAll()
    {
        GL20.glDisable(GL20.GL_DEPTH_TEST);
        Matrix4f projectionMat = Matrix4f.Projection(camera);
        Matrix4f viewMat = Matrix4f.View(camera.position, camera.rotation);

        for (DebugDrawable debugDrawable : content) {
            debugDrawable.Draw(debugShader, projectionMat, viewMat);
        }
        GL20.glEnable(GL20.GL_DEPTH_TEST);
    }

    public static void Clear()
    {
        for (DebugDrawable debugDrawable : Instance.content) {
            debugDrawable.Destroy();
        }
        Instance.content.clear();
    }

    public static void AddLine(Vector3 origin, Vector3 end, Vector3 color)
    {
        DebugLine line = new DebugLine(origin, end, color);
        Instance.content.add(line);
    }

    public static void AddCube(Vector3Int position, Vector3 color)
    {
        DebugCube cube = new DebugCube(position, color);
        Instance.content.add(cube);
    }
}
