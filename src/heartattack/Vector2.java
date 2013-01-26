/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
}
