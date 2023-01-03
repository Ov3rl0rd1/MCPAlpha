package Engine.Objects;

import Engine.Rendering.Material;
import Engine.Rendering.Mesh;
import Engine.Utils.Math.Mathf;
import Engine.Utils.Math.Matrix4f;
import Engine.Utils.Math.Vector3;

public class GameObject {
    public Vector3 position, rotation, scale;
    public final Material material;
    protected Mesh mesh;

    public GameObject(Material material, Mesh mesh)
    {
        this(new Vector3(), new Vector3(), new Vector3(1, 1, 1), material, mesh);
    }

    public GameObject(Vector3 position, Vector3 rotation, Vector3 scale, Material material, Mesh mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.material = material;
        this.mesh = mesh;

        material.Create();
        if(mesh.vertices != null && mesh.vertices.size() > 0)
            mesh.Build();
    }

    public GameObject(Vector3 position, Vector3 rotation) {
        this.position = position;
        this.rotation = rotation;
        this.scale = new Vector3(1);
        this.material = null;
        this.mesh = null;
    }

    public void Draw(Camera camera)
    {
        mesh.Render(material, Matrix4f.Transform(position, rotation, scale), camera, false);
    }

    public void Update()
    {
    }

    public void Destroy()
    {
        mesh.Destroy();
    }

    public Vector3 CalculateForward()
    {
        Vector3 direction = new Vector3();
        direction.x = Mathf.CastToFloat(Math.cos(rotation.y * Mathf.Deg2Rad) * Math.cos(rotation.x * Mathf.Deg2Rad));
		direction.y = Mathf.CastToFloat(Math.sin(rotation.x * Mathf.Deg2Rad));
		direction.z = Mathf.CastToFloat(Math.sin(rotation.y * Mathf.Deg2Rad) * Math.cos(rotation.x * Mathf.Deg2Rad));

        return direction.normalized();
    }
}
