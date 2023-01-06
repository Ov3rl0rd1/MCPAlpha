package Engine.Utils.Math;

import java.util.Arrays;

import Engine.Objects.Camera;

public class Matrix4f {
    public static final int SIZE = 4;
    private float[] elements = new float[SIZE*SIZE];

//#region matrix operations

    public static Matrix4f Identity()
    {
        Matrix4f result = new Matrix4f();

        result.Set(0, 0, 1);
		result.Set(1, 1, 1);
		result.Set(2, 2, 1);
		result.Set(3, 3, 1);

        return result;
    }

    public static Matrix4f Translate(Vector3 translate)
    {
        Matrix4f result = Matrix4f.Identity();

        result.Set(3, 0, translate.x);
		result.Set(3, 1, translate.y);
		result.Set(3, 2, translate.z);

        return result;
    }

    public static Matrix4f Rotate(float angle, Vector3 axis)
    {
        Matrix4f result = Matrix4f.Identity();
        
        float cos = (float)Math.cos(Math.toRadians(angle));
        float sin = (float)Math.sin(Math.toRadians(angle));
        float iCos = 1 - cos;

        result.Set(0, 0, cos + axis.x * axis.x * iCos);
		result.Set(0, 1, axis.x * axis.y * iCos - axis.z * sin);
		result.Set(0, 2, axis.x * axis.z * iCos + axis.y * sin);
		result.Set(1, 0, axis.y * axis.x * iCos + axis.z * sin);
		result.Set(1, 1, cos + axis.y * axis.y * iCos);
		result.Set(1, 2, axis.y * axis.z * iCos - axis.x * sin);
		result.Set(2, 0, axis.z * axis.x * iCos - axis.y * sin);
		result.Set(2, 1, axis.z * axis.y * iCos + axis.x * sin);
		result.Set(2, 2, cos + axis.z * axis.z * iCos);

        return result;
    }

    public static Matrix4f Scale(Vector3 scalar)
    {
        Matrix4f result = Matrix4f.Identity();

        result.Set(0, 0, scalar.x);
        result.Set(1, 1, scalar.y);
        result.Set(2, 2, scalar.z);

        return result;
    }

    public static Matrix4f Transform(Vector3 position, Vector3 rotation, Vector3 scale)
    {
        Matrix4f result = Matrix4f.Identity();

        Matrix4f translationMat = Matrix4f.Translate(position);
        Matrix4f rotationXMat = Matrix4f.Rotate(rotation.x, new Vector3(1, 0, 0));
        Matrix4f rotationYMat = Matrix4f.Rotate(rotation.y, new Vector3(0, 1, 0));
        Matrix4f rotationZMat = Matrix4f.Rotate(rotation.z, new Vector3(0, 0, 1));
        Matrix4f scaleMat = Matrix4f.Scale(scale);

        Matrix4f rotationMat = Matrix4f.Multiply(rotationXMat, Matrix4f.Multiply(rotationYMat, rotationZMat));

        result = Matrix4f.Multiply(translationMat, Matrix4f.Multiply(rotationMat, scaleMat));

        return result;
    }

    public static Matrix4f Projection(Camera camera)
    {
        return Projection(camera.fov, camera.getAspect(), camera.near, camera.far);
    }

    public static Matrix4f Projection(float fov, float aspect, float near, float far)
    {
        Matrix4f result = Matrix4f.Identity();

        float tanFOV = (float)Math.tan(Math.toRadians(fov / 2));
        float range = far - near;

        result.Set(0, 0, 1 / (aspect * tanFOV));
        result.Set(1, 1, 1f / tanFOV);
        result.Set(2, 2, -((far + near) / range));
        result.Set(3, 2, -((2 * far * near) / range));

        result.Set(2, 3, -1);
        result.Set(3, 3, 0);

        return result;
    }

    public static Matrix4f View(Vector3 position, Vector3 rotation)
    {
        Matrix4f result = Matrix4f.Identity();

        Vector3 negative = Vector3.Multiply(position, -1);
        Matrix4f translationMat = Matrix4f.Translate(negative);
        Matrix4f rotationXMat = Matrix4f.Rotate(rotation.x, new Vector3(1, 0, 0));
        Matrix4f rotationYMat = Matrix4f.Rotate(rotation.y, new Vector3(0, 1, 0));
        Matrix4f rotationZMat = Matrix4f.Rotate(rotation.z, new Vector3(0, 0, 1));

        Matrix4f rotationMat = Matrix4f.Multiply(rotationYMat, Matrix4f.Multiply(rotationZMat, rotationXMat));

        result = Matrix4f.Multiply(translationMat, rotationMat);

        return result;
    }

    public static Matrix4f Multiply(Matrix4f matrix, Matrix4f other)
    {
        Matrix4f result = Matrix4f.Identity();

        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                result.Set(i, j, matrix.Get(i, 0) * other.Get(0, j) +
                                 matrix.Get(i, 1) * other.Get(1, j) +
                                 matrix.Get(i, 2) * other.Get(2, j) +
                                 matrix.Get(i, 3) * other.Get(3, j));
            }
        }

        return result;
    }

    //#endregion

    public float Get(int x, int y)
    {
        return elements[x + y * SIZE];
    }

    public void Set(int x, int y, float value)
    {
        elements[x + y * SIZE] = value;
    }

    public float[] GetAll()
    {
        return elements;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null)
            return false;

        if(getClass() != obj.getClass())
            return false;

        Matrix4f a = (Matrix4f)obj;
        return Arrays.equals(elements, a.elements);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(elements);
        return result;
    }

    public static boolean Equals(Matrix4f a, Matrix4f b)
    {
        return Arrays.equals(a.elements, b.elements);
    }
}
