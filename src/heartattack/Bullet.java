
package heartattack;

import org.newdawn.slick.SlickException;

/**
 *
 * @author Kevin
 */
public class Bullet extends Sprite {
    protected Tower owner;
    
    public Bullet(Tower owner)
    {
        super();
        this.owner = owner;
    }
    
    @Override
    public void init(String path) throws SlickException
    {
        super.init(path);
    }
}
