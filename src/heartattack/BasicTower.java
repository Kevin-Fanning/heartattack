
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
public class BasicTower extends Tower {
    protected static Image turret;
    protected static Image base;
    protected Image ownTurret;
    protected Vector2 aimDirection;
    protected Enemy target;
    
    protected long lastFire;
    
    protected static float fireSpeed;
    protected ArrayDeque<Bullet> bulletQue;
    protected ArrayDeque<Bullet> activeBullets;
    
    
    
    public BasicTower()
    {
        super();
        
        width = base.getWidth();
        height = base.getHeight();
        
        aimDirection = new Vector2(1.0f,0.0f);
        
        bulletQue = new ArrayDeque<>(20);
        activeBullets = new ArrayDeque<>(20);
        
        for (int i = 0; i < 20; i++)
        {
            bulletQue.addLast(new Bullet());
        }
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
            Bullet temp = bulletQue.poll();
            if (temp != null) {
                temp.position = new Vector2(this.position.x+this.width/2, this.position.y+this.height/2);
                temp.velocity = new Vector2(aimDirection.times(fireSpeed));
                
                activeBullets.add(temp);
                
                lastFire = System.currentTimeMillis();
            }
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
            for (Bullet i : activeBullets) 
            {
                i.update(delta);
                if (i.position.x < 0 || i.position.x > 800 || i.position.y > 600 || i.position.y < 0)
                {
                    bulletQue.add(i);
                    activeBullets.remove(i);
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
        for (Bullet i : activeBullets) 
        {
            i.render(g);
        }
        ownTurret.draw((int)position.x, (int)position.y);
        
        
    }
    
    //return an arrayque of the active bullets
    @Override
    public ArrayDeque<Bullet> getBullets()
    {
        return activeBullets;
    }
    
    //Call this to remove a bullet from the active bullet lists
    @Override
    public void removeBullet(Bullet b)
    {
        bulletQue.add(b);
        activeBullets.remove(b);
    }
}
