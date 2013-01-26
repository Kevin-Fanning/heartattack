/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartattack;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Kevin
 */
public abstract class Sprite {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    
    private Image texture;
    
    public void Sprite(String path)
    {
        position = new Vector2();
        velocity = new Vector2();
        acceleration = new Vector2();
    }
    
    public void init(String path) throws SlickException
    {
        texture = new Image(path);
    }
    
    public void update()
    {
        velocity.addE(acceleration);
        position.addE(velocity);
    }
    
    public void render()
    {
        if (texture != null) 
        {
            texture.draw(position.x, position.y);
        }
    }
    
    public Rect boundingBox()
    {
        return new Rect(position.x, position.y, texture.getTextureWidth(), texture.getTextureHeight());
    }
}
