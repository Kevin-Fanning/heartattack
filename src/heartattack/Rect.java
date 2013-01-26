/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartattack;

/**
 *
 * @author Kevin
 */
public class Rect {
    public float x,y,length,width;

    public Rect()
    {
        x = 0; y = 0; width = 0; length = 0;
    }
    
    public Rect(float x, float y, float width, float length)
    {
        this.x = x; this.y = y; this.width = width; this.length = length;
    }
    
    public boolean intersects(Rect other)
    {
        if ((other.x + other.width >= x) && (other.x < x + width) && (other.y + other.length >= y) && (other.y <= y + length))
        {
            return true;
        }
        return false;
    }
}
