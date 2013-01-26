
package heartattack;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.ArrayDeque;
import java.util.LinkedList;
/**
 *
 * @author Kevin
 */
public class BasicTower extends Tower {
    protected Image turret;
    protected Vector2 aimDirection;
    
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
        //TODO: transparent while hovered. Colored red if impossible
    }
    
    //aim at the closest baddie
    @Override
    public void aim(LinkedList<Enemy> enemyList)
    {
        aimDirection = position.getDirection(enemyList.getFirst().position);
        turret.setRotation(aimDirection.toDegrees());
    }
    
    //fire the weapon
    @Override
    public void fire()
    {
        if (System.currentTimeMillis() - lastFire > fireRate)
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

    public void init(String turretPath,String basePath) throws SlickException
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
    public void render()
    {
        super.render();
        turret.draw((int)position.x, (int)position.y);
        for (Bullet i : activeBullets) 
        {
            i.render();
        }
    }
}
