
package heartattack;

/**
 *
 * @author Kevin
 */
public class Vector2 {
    public float x;
    public float y;
    
    public Vector2() {
        x = 0; y = 0;
    }
    public Vector2(float x, float y)
    {
        this.x = x; this.y = y;
    }
    public Vector2(Vector2 v)
    {
        this.x = v.x;
        this.y = v.y;
    }
    public Vector2 add(Vector2 second)
    {
        return new Vector2(second.x + x, second.y + y);
    }
    public void addE(Vector2 second)
    {
        x += second.x;
        y += second.y;
    }
    public Vector2 times(float second){
        return new Vector2(x*second, y*second);
    }
    public void timesE(float second)
    {
        x *= second;
        y *= second;
    }
    public Vector2 normalize()
    {
        if (x == 0 && y == 0) 
        { 
            return new Vector2();
        }
        float magnitude = (float) Math.sqrt(x*x + y*y);
        return new Vector2(x/magnitude, y/magnitude);
    }
    
    public static Vector2 zero() 
    {
        return new Vector2();
    }
    
    //Returns a normal vector pointing from one this position to another
    public Vector2 getDirection(Vector2 other)
    {
        Vector2 temp = other.add(this.times(-1.0f));
        return temp.normalize();
    }
    
    public float dot(Vector2 other)
    {
        return x*other.x + y*other.y;
    }
    
    //Returns the vector in radians from (1,0)
    public float toDegrees()
    {
        float temp = (float)((Math.acos(this.dot(new Vector2(1,0))))*(180/Math.PI));
        if (y <=0) {
            return temp * -1;
        } else {
            return temp;
        }
    }
    
    @Override
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }
}
