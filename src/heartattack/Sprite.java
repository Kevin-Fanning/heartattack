
package heartattack;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Kevin
 */
public abstract class Sprite {
    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 acceleration;
    protected int height;
    protected int width;
    
    protected Image texture;
    
    public Sprite()
    {
        position = new Vector2();
        velocity = new Vector2();
        acceleration = new Vector2();
    }
    
    public void setPosition(Vector2 v)
    {
        position = v;
    }
    public void setVelocity(Vector2 v)
    {
        velocity = v;
    }
    public void setAccel(Vector2 v)
    {
        acceleration = v;
    }
    
    public void init(String path) throws SlickException
    {
        texture = new Image(path);
        width = texture.getWidth();
        height = texture.getHeight();
    }
    
    public void update(int delta)
    {
        velocity.addE(acceleration.times(delta/1000.0f));
        position.addE(velocity.times(delta/1000.0f));
    }
    
    public void render()
    {
        if (texture != null) 
        {
            texture.draw((int)position.x, (int)position.y);
        }
    }
    
    public Rect boundingBox()
    {
        return new Rect(position.x, position.y, width, height);
    }
}
