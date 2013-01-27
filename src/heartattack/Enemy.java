
package heartattack;

import java.util.Iterator;
import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
/**
 *
 * @author Kevin
 */
public class Enemy extends Sprite {
    protected LinkedList<Vector2> waypoints;
    protected Iterator<Vector2> wayItr;
    protected Vector2 curWaypoint;
    protected boolean isFinished;
    public boolean alive = true;
    
    protected int health;
    
    protected float speed = 40;
    protected int bounty = 10;
    
    public Enemy()
    {
        super();
        waypoints = Level.getWaypoints();
//        waypoints = new LinkedList<>();
//        waypoints.add(new Vector2(-100,100));
//        waypoints.add(new Vector2(600,100));
//        waypoints.add(new Vector2(600, 300));
//        waypoints.add(new Vector2(100,300));
//        waypoints.add(new Vector2(100, 500));
//        waypoints.add(new Vector2(900, 500));
        
        curWaypoint = waypoints.getFirst();
        position = new Vector2(curWaypoint.x - width/2, curWaypoint.y - height/2);
        wayItr = waypoints.listIterator(1);
        curWaypoint = wayItr.next();
        
        velocity = position.getDirection(curWaypoint).times(speed);
        
        health = 100;
        
        isFinished = false;
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
        super.update(delta);
    }
    @Override
    public void render(Graphics g) 
    {
        texture.drawCentered((int)position.x,(int)position.y);
        g.setColor(Color.green);
        g.fillRect(position.x-width/2, position.y-height/2, health/2, 10.0f);
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
}
