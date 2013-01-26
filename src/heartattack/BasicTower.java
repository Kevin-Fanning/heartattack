
package heartattack;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Kevin
 */
public class BasicTower extends Tower {
    protected Image turret;
    protected float angle;
    
    public BasicTower()
    {
        super();
    }
    
    //what do when you place it
    @Override
    public void place()
    {
        enabled = true;
    }
    
    //holding it before it's placed
    @Override
    public void hover()
    {
        
    }
    
    //aim at the closest baddie
    @Override
    public void aim(Vector2 target)
    {
        Vector2 aimPos = position.getDirection(target);
        turret.setRotation(aimPos.toDegrees());
        System.out.println(aimPos.toDegrees());
    }
    
    //fire the weapon
    @Override
    public void fire()
    {
        
    }
    
    //Get rid of the tower
    @Override
    public void sell()
    {
        
    }

    public void init(String turretPath,String basePath) throws SlickException
    {
        super.init(basePath);
        turret = new Image(turretPath);
        turret.setCenterOfRotation(turret.getWidth()/2, turret.getHeight()/2);
    }
    @Override
    public void render()
    {
        super.render();
        turret.draw((int)position.x, (int)position.y);
        
    }
}
