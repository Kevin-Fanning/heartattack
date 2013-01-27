/**
 * @author Kevin
 */
package heartattack;

import java.util.LinkedList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Level
{
    public static Image background;
    private static Image layerMask;
    private static LinkedList<Vector2> waypoints;
    
    public static void loadLevel(String name) throws SlickException
    {
        background = new Image(name + ".png");
        layerMask = new Image(name+"Mask.png");
        
        waypoints = new LinkedList<>();
        waypoints.add(new Vector2(-100,100));
        waypoints.add(new Vector2(600,100));
        waypoints.add(new Vector2(600, 300));
        waypoints.add(new Vector2(100,300));
        waypoints.add(new Vector2(100, 500));
        waypoints.add(new Vector2(900, 500));
    }
    
    public static Image getLayerMask()
    {
        return layerMask;
    }
    public static LinkedList<Vector2> getWaypoints()
    {
        return waypoints;
    }
}