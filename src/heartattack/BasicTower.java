
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
    protected Image turret;
    protected Vector2 aimDirection;
    protected Enemy target;
    
    protected long lastFire;
    
    protected final static float fireSpeed = 300;
    protected ArrayDeque<Bullet> bulletQue;
    protected ArrayDeque<Bullet> activeBullets;
    
    
    
    public BasicTower()
    {
        super();
        
        aimDirection = new Vector2(1.0f,0.0f);
        
        bulletQue = new ArrayDeque<>(20);
        activeBullets = new ArrayDeque<>(20);
        
        for (int i = 0; i < 20; i++)
        {
            bulletQue.addLast(new Bullet());
        }
        
        fireRate = 500;
        lastFire = System.currentTimeMillis();
        
        range = 200;
        damage = 7;
    }
    
    //what do when you place it
    @Override
    public void place()
    {
        enabled = true;
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
            else // shift direction towards the target
            {
                aimDirection = position.getDirection(target.position);
                turret.setRotation(aimDirection.toDegrees());

                //If the target is out of range, reset
                if (position.getSquareDistance(target.position) > range*range)
                {
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
        if (enabled && System.currentTimeMillis() - lastFire > fireRate && aimDirection != new Vector2(1.0f,0.0f))
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
    //init(String) is for one picture towers
    @Override
    public void init2(String turretPath,String basePath) throws SlickException
    {
        super.init(basePath);
        turret = new Image(turretPath);
        turret.setCenterOfRotation(turret.getWidth()/2, turret.getHeight()/2);
        for (Bullet i : bulletQue) 
        {
            i.init("bullet.png");
        }
    }
    
    @Override
    public void update(int delta)
    {
        if (currentState == states.ACTIVE)
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
        } else if (currentState == states.PLACING)
        {
            position = InputController.msPosition;
        }
    }
    
    @Override
    public void render(Graphics g)
    {
        if (currentState == states.ACTIVE)
            {
            super.render(g);
            turret.draw((int)position.x, (int)position.y);
            for (Bullet i : activeBullets) 
            {
                i.render(g);
            }
        } else if (currentState == states.PLACING)
        {
            texture.draw((int)position.x, (int)position.y, new Color(255,255,255,127));
            turret.draw((int)position.x, (int)position.y, new Color(255,255,255,127));
        }
        //g.drawOval(position.x-range + width/2, position.y-range + height/2, range*2, range*2);
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
