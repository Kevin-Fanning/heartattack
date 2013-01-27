/**
 * @author Kevin
 */
package heartattack;

import java.util.ArrayDeque;
import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class FreezeTower extends Tower
{
    public static Image base;
    public static int plasmaCost;
    
    private boolean isFiring;
    private long lastFire;
    private float fireSize;
    private int alpha;
    
    protected Sound fireSound;
    
    public FreezeTower()
    {
        super();
        isFiring = false;
        fireSize = 0;
        alpha = 255;
        
        width = base.getWidth();
        height = base.getHeight();
        
        try {fireSound = new Sound("src/ice.wav");} catch (Exception e) {System.out.println(e);}
    }
    
    public static void loadImage(String path) throws SlickException
    {
        base = new Image(path);
    }
    
    @Override
    public void aim(LinkedList<Enemy> enemyList)
    {
        if (!isFiring && (System.currentTimeMillis() - lastFire > fireRate))
        {
            for (Enemy e : enemyList)
            {
                if (e.position.getSquareDistance(new Vector2(position.x+width/2, position.y+height)) < range*range)
                {
                    e.damage(damage);
                    e.slow(1800);
                    isFiring = true;
                    fireSound.play();
                    lastFire = System.currentTimeMillis();
                }
            }
            
        }
    }
    
    @Override
    public void reacquire(LinkedList<Enemy> enemyList)
    {
        //Pointless for area attack. I'm bad at polymorphism
    }
    @Override
    public ArrayDeque getBullets()
    {
        //Pointless for area attack. I'm bad at polymorphism
        return new ArrayDeque<>();
    }
    
    @Override
    public void removeBullet(Bullet b)
    {
        //Pointless for area attack. I'm bad at polymorphism
    }
    
    @Override
    public void fire()
    {
        //I realized this was just update...
    }
    
    @Override
    public void update(int delta)
    {
        if (isFiring && fireSize <= range)
        {
            fireSize += range * 1.5f * delta / 1000;
        }
        else if (isFiring && fireSize > range)
        {
            alpha -= 510 * delta / 1000;
        }
        if (alpha <= 20)
        {
            isFiring = false;
            alpha = 255;
            fireSize = 0;
        }
    }
    
    @Override
    public void render(Graphics g)
    {
        if (selected)
        {
            g.setAntiAlias(false);
            g.setColor(new Color(0,200,0,80));
            g.fillOval(position.x-range + width/2, position.y-range + height/2, range*2, range*2);
        }
        if (isFiring)
        {
            g.setAntiAlias(false);
            g.setColor(new Color(200,200,255,alpha));
            g.fillOval(position.x - fireSize+width/2, position.y-fireSize+height/2, fireSize*2, fireSize*2);
        }
        base.draw(position.x, position.y);
    }
}