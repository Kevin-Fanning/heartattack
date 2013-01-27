
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
    protected LinkedList<Vector2> waypoints;
    protected Iterator<Vector2> wayItr;
    protected Vector2 curWaypoint;
    protected boolean isFinished;
    public boolean alive = true;
    
    protected int health;
    
    protected float speed = 40;
    protected int bounty = 10;
    
    protected static Image texture;
    
    public Enemy()
    {
        super();
        waypoints = Level.getWaypoints();
        curWaypoint = waypoints.getFirst();
        position = new Vector2(curWaypoint.x - width/2, curWaypoint.y - height/2);
        wayItr = waypoints.listIterator(1);
        curWaypoint = wayItr.next();
        
        velocity = position.getDirection(curWaypoint).times(speed);
        
        health = 100;
        
        isFinished = false;
        width = texture.getWidth();
        height = texture.getHeight();
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
