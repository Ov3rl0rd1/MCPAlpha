package Engine.Utils.Math;

import java.util.Comparator;

public class Vector3 implements Comparator<Vector3> {
    public float x;
    public float y;
    public float z;

    public Vector3()
    {
    }

    public Vector3(float value)
    {
        this.x = value;
        this.y = value;
        this.z = value;
    }

    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vector)
    {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public Vector3(Vector3Int vector)
    {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public void Set(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float magnitude()
    {
        return (float)Math.sqrt(x*x+y*y+z*z);
    }

    public float sqrMagnitude()
    {
        return x*x+y*y+z*z;
    }

    public Vector3 normalized()
    {
        float length = magnitude();
        return new Vector3(x / length, y / length, z / length);
    }

    public Vector3Int sign()
    {
        return new Vector3Int(Math.signum(x), Math.signum(y), Math.signum(z));
    }

    public Vector3Int signNotZero()
    {
        return new Vector3Int(x >= 0 ? 1 : -1, y >= 0 ? 1 : -1, z >= 0 ? 1 : -1);
    }

    public Vector3Int ceil()
    {
        return new Vector3Int(x, y, z);
    }

    public float dot(Vector3 a)
    {
        return x * a.x + y * a.y + z * a.z;
    }

    public static float dot(Vector3 a, Vector3 b)
    {
        return b.x * a.x + b.y * a.y + b.z * a.z;
    }

    //#region Operators

    public static Vector3 Add(Vector3 a, Vector3 b) 
    { 
        return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z); 
    }

    public static Vector3 Add(Vector3 a, Vector3Int b) 
    { 
        return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z); 
    }

    public static Vector3 Subtract(Vector3 a, Vector3 b) 
    { 
        return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z); 
    }

    public static Vector3 Multiply(Vector3 a, Vector3 b) 
    { 
        return new Vector3(a.x * b.x, a.y * b.y, a.z * b.z); 
    }

    public static Vector3 Multiply(Vector3 a, Vector3Int b) 
    { 
        return new Vector3(a.x * b.x, a.y * b.y, a.z * b.z); 
    }

    public static Vector3 Multiply(Vector3 a, float b) 
    { 
        return new Vector3(a.x * b, a.y * b, a.z * b); 
    }

    public static Vector3 Divide(Vector3 a, Vector3 b) 
    { 
        return new Vector3(a.x / b.x, a.y / b.y, a.z / b.z); 
    }

    public static Vector3 Divide(Vector3 a, Vector3Int b) 
    { 
        return new Vector3(a.x / b.x, a.y / b.y, a.z / b.z); 
    }

    public static Vector3 Divide(Vector3Int a, Vector3 b) 
    { 
        return new Vector3(a.x / b.x, a.y / b.y, a.z / b.z); 
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
        Vector3 a = (Vector3)obj;
        return a.x == x && a.y == y && a.z == z;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }
    
    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ", " + z + ");";
    }

    @Override
    public int compare(Vector3 v1, Vector3 v2) {
        return Float.compare(v1.magnitude(), v2.magnitude());
    }

    public static boolean Equals(Vector3 a, Vector3 b)
    {
        return a.x == b.x && a.y == b.y && a.z == b.z;
    }
    
    //#endregion
}
