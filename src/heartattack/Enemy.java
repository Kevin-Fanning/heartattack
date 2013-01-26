
package heartattack;

import java.util.Iterator;
import java.util.LinkedList;
/**
 *
 * @author Kevin
 */
public class Enemy extends Sprite {
    protected LinkedList<Vector2> waypoints;
    protected Iterator<Vector2> wayItr;
    protected Vector2 curWaypoint;
    
    protected float speed = 40;
    public Enemy()
    {
        super();
        waypoints = new LinkedList<>();
        waypoints.add(new Vector2(-100,100));
        waypoints.add(new Vector2(600,100));
        waypoints.add(new Vector2(600, 300));
        waypoints.add(new Vector2(100,300));
        waypoints.add(new Vector2(100, 500));
        waypoints.add(new Vector2(800, 500));
        
        position = waypoints.getFirst();
        wayItr = waypoints.listIterator(1);
        curWaypoint = wayItr.next();
        
        velocity = position.getDirection(curWaypoint).times(speed);
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
                //TODO: he got to the end. you lose some health
            }
        }
        super.update(delta);
    }
}
