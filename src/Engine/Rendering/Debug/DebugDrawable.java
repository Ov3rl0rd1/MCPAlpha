package Engine.Rendering.Debug;

import Engine.Rendering.Shader;
import Engine.Utils.Math.Matrix4f;
import Engine.Utils.Math.Vector3;

public abstract class DebugDrawable {

    protected final Vector3 color;

    public DebugDrawable(Vector3 color)
    {
        this.color = color;
    }

    public abstract void Destroy();

    public abstract void Draw(Shader shader, Matrix4f projectionMat, Matrix4f viewMat);
}
