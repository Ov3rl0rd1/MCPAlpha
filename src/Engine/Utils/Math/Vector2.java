package Engine.Utils.Math;

public class Vector2 {
    public float x;
    public float y;

    public Vector2()
    {
        
    }

    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float magnitude()
    {
        return (float)Math.sqrt(x*x+y*y);
    }

    public float sqrMagnitude()
    {
        return x*x+y*y;
    }

    public Vector2 normalized()
    {
        float length = sqrMagnitude();
        return new Vector2(x / length, y / length);
    }

    public float dot(Vector2 a)
    {
        return x * a.x + y * a.y;
    }

    public static float dot(Vector2 a, Vector2 b)
    {
        return b.x * a.x + b.y * a.y;
    }

    public static float distance(Vector2 a, Vector2 b)
    {
        return Math.abs(Vector2.Subtract(a, b).magnitude());
    }

    //#region Operators

    public static Vector2 Add(Vector2 a, Vector2 b) 
    { 
        return new Vector2(a.x + b.x, a.y + b.y); 
    }

    public static Vector2 Subtract(Vector2 a, Vector2 b) 
    { 
        return new Vector2(a.x - b.x, a.y - b.y); 
    }

    public static Vector2 Multiply(Vector2 a, Vector2 b) 
    { 
        return new Vector2(a.x * b.x, a.y * b.y); 
    }

    public static Vector2 Multiply(Vector2 a, float b) 
    { 
        return new Vector2(a.x * b, a.y * b); 
    }

    public static Vector2 Divide(Vector2 a, Vector2 b) 
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
        Vector2 a = (Vector2)obj;
        return a.x == x && a.y == y;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        return result;
    }
    public static boolean Equals(Vector2 a, Vector2 b)
    {
        if(a == null || b == null)
            return false;
        return a.x == b.x && a.y == b.y;
    }
    //#endregion
}
