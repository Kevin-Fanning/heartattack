
package heartattack;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.ArrayDeque;
import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Kevin
 */
public class LaserTower extends Tower {
    protected static Image turret;
    protected static Image base;
    protected Image ownTurret;
    protected Vector2 aimDirection;
    protected Enemy target;
    
    protected long lastFire;
    
    protected static float fireSpeed;
    
    
    
    public LaserTower()
    {
        super();
        
        width = base.getWidth();
        height = base.getHeight();
        
        aimDirection = new Vector2(1.0f,0.0f);

        lastFire = System.currentTimeMillis();

        ownTurret = turret.getScaledCopy(1);
    }
    
    @Override
    public void reacquire(LinkedList<Enemy> enemyList)
    {
        enabled = false;
        float minDistance = range*range;
        for (Enemy e: enemyList)
        {
            float curDistance = position.getSquareDistance(e.position);
            if (curDistance < minDistance)
            {
                target = e;
                enabled = true;
                minDistance = curDistance;
                aimDirection = position.getDirection(target.position);
                ownTurret.setRotation(aimDirection.toDegrees());
            }
        }
    }
    
    //aim at the closest baddie. stay on target until they leave range.
    @Override
    public void aim(LinkedList<Enemy> enemyList)
    {
        
        if (!enemyList.isEmpty())
        { 
            
            if (target == null || !target.alive) //we need a new target
            {
                reacquire(enemyList);
                
            }
            else  // shift direction towards the target
            {
                aimDirection = position.getDirection(target.position);
                ownTurret.setRotation(aimDirection.toDegrees());

                //If the target is out of range, reset
                if (position.getSquareDistance(target.position) > range*range)
                {
                    target = null;
                    reacquire(enemyList);
                }
            }
        }
        else 
        {
            enabled = false;
        }
    }
    
    //fire the weapon
    @Override
    public void fire()
    {
        if (enabled && System.currentTimeMillis() - lastFire > fireRate)
        {
            
        }
    }
    
    //Get rid of the tower
    @Override
    public void sell()
    {
        //TODO: sell the tower
    }

    //Initialize the tower with 2 images. a turret and a base
    public static void loadImages(String turretPath,String basePath) throws SlickException
    {
        base = new Image(basePath);
        turret = new Image(turretPath);
        turret.setCenterOfRotation(turret.getWidth()/2, turret.getHeight()/2);
    }
    
    
    @Override
    public void update(int delta)
    {
        if (System.currentTimeMillis() - lastFire > fireRate)
        {
            if (target != null)
            {
                target.damage(damage);
                if (target.health <= 0)
                {
                    target = null;
                }
                lastFire = System.currentTimeMillis();
            }
        }
    }
    
    @Override
    public void render(Graphics g)
    {
        if (selected)
        {
            g.setColor(new Color(0,200,0,80));
            g.fillOval(position.x-range + width/2, position.y-range + height/2, range*2, range*2);
        }
        base.draw((int)position.x, (int)position.y);
        if (target != null)
        {
            g.setAntiAlias(true);
            g.setColor(Color.green);
            g.setLineWidth(3.0f);
            g.drawLine(position.x+width/2, position.y+height/2, target.position.x, target.position.y);
            g.setColor(Color.white);
            g.setLineWidth(1.3f);
            g.drawLine(position.x+width/2, position.y+height/2, target.position.x, target.position.y);
        }
        ownTurret.draw((int)position.x, (int)position.y);
        
        
    }
    
    //return an arrayque of the active bullets
    @Override
    public ArrayDeque<Bullet> getBullets()
    {
        //lasers don't have bullets
        return new ArrayDeque<>();
    }
    
    //Call this to remove a bullet from the active bullet lists
    @Override
    public void removeBullet(Bullet b)
    {
        //lasers don't have bullets
    }
}
