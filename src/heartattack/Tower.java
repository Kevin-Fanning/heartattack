
package heartattack;

import java.util.ArrayDeque;
import java.util.LinkedList;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kevin
 */
//TODO: Everything
public abstract class Tower extends Sprite{
    protected float range;      //How far the tower can shoot
    protected float fireRate;   //How fast the tower fires
    protected int damage;     //The damage to apply on a hit
    
    protected static int plasmaCost = 0;   //How much the tower costs
    protected int redCost = 0;
    protected int whiteCost = 0;
    protected int sellBack = 0;
    
    protected boolean enabled;
    
    protected boolean selected;

    
    public Tower()
    {
        range = 0;
        fireRate = 0;
        damage = 0;
        enabled = false;
        selected = false;
    }
    
    public void select()
    {
        selected = true;
    }
    public void deselect()
    {
        selected = false;
    }
    
    //aim at the closest baddie
    public abstract void aim(LinkedList<Enemy> enemyList);
    
    //fire the weapon
    public abstract void fire();
    
    //Get rid of the tower
    public abstract void sell();
    
    //Use this to grab bullets for collision detection
    public abstract ArrayDeque<Bullet> getBullets();
    
    //Use this to remove a bullet from the active que on a collision
    public abstract void removeBullet(Bullet b);
    
    public int getDamage()
    {
        return damage;
    }
    //Use this to reacquire a target when the current one dies
    public abstract void reacquire(LinkedList<Enemy> enemyList);
}
