package Engine.Utils.Math;

public class Vector2Int {
    public int x;
    public int y;

    public Vector2Int()
    {
        
    }

    public Vector2Int(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public float magnitude()
    {
        return (float)Math.sqrt(x*x+y*y);
    }

    public int sqrMagnitude()
    {
        return x*x+y*y;
    }

    public Vector2 normalized()
    {
        float length = magnitude();
        return new Vector2(x / length, y / length);
    }

    public float dot(Vector2Int a)
    {
        return x * a.x + y * a.y;
    }

    public static float dot(Vector2Int a, Vector2Int b)
    {
        return b.x * a.x + b.y * a.y;
    }

    public static float distance(Vector2Int a, Vector2Int b)
    {
        return Math.abs(Vector2Int.Subtract(a, b).magnitude());
    }

    //#region Operators

    public static Vector2Int Add(Vector2Int a, Vector2Int b) 
    { 
        return new Vector2Int(a.x + b.x, a.y + b.y); 
    }

    public static Vector2Int Subtract(Vector2Int a, Vector2Int b) 
    { 
        return new Vector2Int(a.x - b.x, a.y - b.y); 
    }

    public static Vector2Int Multiply(Vector2Int a, Vector2Int b) 
    { 
        return new Vector2Int(a.x * b.x, a.y * b.y); 
    }

    public static Vector2 Multiply(Vector2Int a, float b) 
    { 
        return new Vector2(a.x * b, a.y * b); 
    }

    public static Vector2 Divide(Vector2Int a, Vector2Int b) 
    { 
        return new Vector2(a.x / b.x, a.y / b.y); 
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
        Vector2Int a = (Vector2Int)obj;
        return a.x == x && a.y == y;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    public static boolean Equals(Vector2Int a, Vector2Int b)
    {
        if(a == null || b == null)
            return false;
        return a.x == b.x && a.y == b.y;
    }
    //#endregion
}
