package heartattack;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Mine extends Bullet
{
    private static Image texture;
    
    public static void loadImage(String path) throws SlickException
    {
        texture = new Image(path);
    }
    
    @Override 
    public void render(Graphics g)
    {
        texture.draw(position.x, position.y);
    }
}