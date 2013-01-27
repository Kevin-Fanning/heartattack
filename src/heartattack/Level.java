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
    
    public static void loadLevel(String name) throws SlickException
    {
        background = new Image(name + ".png");
        layerMask = new Image(name+"Mask.png");
        
    }
    
    public static Image getLayerMask()
    {
        return layerMask;
    }
}