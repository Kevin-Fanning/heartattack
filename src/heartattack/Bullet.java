
package heartattack;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kevin
 */
public class Bullet extends Sprite {
    private static Image texture;
    @Override
    public void init(String path) throws SlickException
    {
        super.init(path);
    }
    
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
