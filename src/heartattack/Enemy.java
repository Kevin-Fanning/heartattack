
package heartattack;

import java.util.Iterator;
import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Kevin
 */
public class Enemy extends Sprite {
    public static final float SLOW_AMOUNT = 2;
    
    protected LinkedList<Vector2> waypoints;
    protected Iterator<Vector2> wayItr;
    protected Vector2 curWaypoint;
    protected boolean isFinished;
    public boolean alive = true;
    
    protected int health;
    protected int maxHealth;
    
    protected float speed = 40;
    protected int bounty = 10;
    
    protected boolean isSlowed;
    protected long slowStartTime;
    protected long slowEndTime;
    
    protected static Image texture;
    
    public Enemy()
    {
        super();
//        curWaypoint = waypoints.getFirst();
//        position = new Vector2(curWaypoint.x - width/2, curWaypoint.y - height/2);
//        wayItr = waypoints.listIterator(1);
//        curWaypoint = wayItr.next();
//        
//        velocity = position.getDirection(curWaypoint).times(speed);
        
        health = 100;
        maxHealth = 100;
        
        isFinished = false;
        width = texture.getWidth();
        height = texture.getHeight();
        
        isSlowed = false;
    }
    
    public static void loadImage(String path) throws SlickException
    {
        texture = new Image(path);
        
    }
    
    @Override
    public void update(int delta)
    {
        if (position.getSquareDistance(curWaypoint) < 400.0f)
        {
            if (wayItr.hasNext())
            {
                curWaypoint = wayItr.next();
                velocity = position.getDirection(curWaypoint).times(speed);
            } else
            {
                Player.loseLife();
                isFinished = true;
            }
        }
        if (isSlowed)
        {
            if (System.currentTimeMillis() >= slowEndTime)
            {
                isSlowed = false;
                speed *= SLOW_AMOUNT;
                velocity = velocity.normalize().times(speed);
            }
        }
        super.update(delta);
    }
    @Override
    public void render(Graphics g) 
    {
        texture.drawCentered((int)position.x,(int)position.y);
        g.setColor(Color.green);
        g.fillRect(position.x-width/2, position.y-height/2-10, health/((float)maxHealth)*width, 10.0f);
    }
    
    public void damage(int amount)
    {
        health -= amount;
        if (health <= 0) { alive = false;}
    }
    
    public int getHealth()
    {
        return health;
    }
    
    @Override
    public Rect boundingBox()
    {
        return new Rect(position.x - width/2,
                        position.y - height/2,
                        width,
                        height);
    }
    
    public boolean isFinished()
    {
        return isFinished;
    }
    public int getBounty()
    {
        return bounty;
    }
    public void setHealth(float newHealth)
    {
        health = (int)newHealth;
        maxHealth = health;
    }
    public void setWaypoints(LinkedList<Vector2> list)
    {
        waypoints = list;
        curWaypoint = waypoints.getFirst();
        position = new Vector2(curWaypoint.x, curWaypoint.y);
        wayItr = waypoints.listIterator(1);
        curWaypoint = wayItr.next();
        
        velocity = position.getDirection(curWaypoint).times(speed);
    }
    public void slow(int milliseconds)
    {
        if (!isSlowed)
        {
            isSlowed = true;
            slowStartTime = System.currentTimeMillis();
            slowEndTime = slowStartTime + milliseconds;
            speed /= SLOW_AMOUNT;
            velocity = velocity.normalize().times(speed);
        }
    }
}
