
package heartattack;

import java.util.LinkedList;

/**
 *
 * @author Kevin
 */
//TODO: Everything
public abstract class Tower extends Sprite{
    protected float range;
    protected float fireRate;
    protected float damage;
    
    protected boolean enabled;
    
    public Tower()
    {
        range = 0;
        fireRate = 0;
        damage = 0;
        enabled = false;
    }
    
    //what do when you place it
    public abstract void place();
    
    //holding it before it's placed
    public abstract void hover();
    
    //aim at the closest baddie
    public abstract void aim(LinkedList<Enemy> enemyList);
    
    //fire the weapon
    public abstract void fire();
    
    //Get rid of the tower
    public abstract void sell();
}
