package Engine.Utils.Math;

public class Vector3Int {
    public int x;
    public int y;
    public int z;

    public Vector3Int()
    {
    }

    public Vector3Int(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3Int(float x, float y, float z)
    {
        this.x = (int)x;
        this.y = (int)y;
        this.z = (int)z;
    }

    public Vector3Int(Vector3 v)
    {
        x = (int)v.x;
        y = (int)v.y;
        z = (int)v.z;
    }

    public float magnitude()
    {
        return (float)Math.sqrt(x*x+y*y+z*z);
    }

    public int sqrMagnitude()
    {
        return x*x+y*y+z*z;
    }

    public Vector3Int normalized()
    {
        float length = magnitude();
        return new Vector3Int(x / length, y / length, z / length);
    }

    public Vector3Int sign()
    {
        return new Vector3Int(Math.signum(x), Math.signum(y), Math.signum(z));
    }

    public int dot(Vector3Int a)
    {
        return x * a.x + y * a.y + z * a.z;
    }

    public static int dot(Vector3Int a, Vector3Int b)
    {
        return b.x * a.x + b.y * a.y + b.z * a.z;
    }

    public static Vector3Int Add(Vector3Int a, Vector3Int b) 
    { 
        return new Vector3Int(a.x + b.x, a.y + b.y, a.z + b.z); 
    }

    public static Vector3 Add(Vector3Int a, Vector3 b)
    { 
        return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z); 
    }

    public static Vector3Int Subtract(Vector3Int a, Vector3Int b) 
    { 
        return new Vector3Int(a.x - b.x, a.y - b.y, a.z - b.z); 
    }

    public static Vector3 Subtract(Vector3Int a, Vector3 b) 
    { 
        return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z); 
    }

    public static Vector3Int Multiply(Vector3Int a, Vector3Int b) 
    { 
        return new Vector3Int(a.x * b.x, a.y * b.y, a.z * b.z); 
    }

    public static Vector3 Multiply(Vector3Int a, Vector3 b) 
    { 
        return new Vector3(a.x * b.x, a.y * b.y, a.z * b.z); 
    }

    public static Vector3Int Multiply(Vector3Int a, int b) 
    { 
        return new Vector3Int(a.x * b, a.y * b, a.z * b); 
    }

    public static Vector3 Divide(Vector3Int a, Vector3Int b) 
    { 
        return new Vector3(a.x / (float)b.x, a.y / (float)b.y, a.z / (float)b.z); 
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
        Vector3Int a = (Vector3Int)obj;
        return a.x == x && a.y == y && a.z == z;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ", " + z + ");";
    }

    public static boolean Equals(Vector3Int a, Vector3Int b)
    {
        return a.x == b.x && a.y == b.y && a.z == b.z;
    }
}
