
package heartattack;

import java.util.LinkedList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kevin
 */
//This class holds the player's resources and displays the GUI
public class Player {
    protected enum towerType {
        BASIC,
        SPLASH,
        AREA
    }
    protected static int redBlood;
    protected static int whiteBlood;
    protected static int plasma = 1000;
    
    protected static LinkedList<StoreIcon> icons = new LinkedList<>();
    
    
    
    public Player()
    {
        StoreIcon basicTowerIcon = new StoreIcon();
        icons.add(basicTowerIcon);
    }
    
    public void init() throws SlickException
    {
        for (StoreIcon i : icons)
        {
            i.init("base.png");
            i.position = new Vector2(50, 550);
        }
    }
    
    public void update(int delta)
    {
        for (StoreIcon i: icons)
        {
            i.update(delta);
        }
    }
    
    public void render(Graphics g)
    {
        for (StoreIcon i: icons)
        {
            i.render(g);
        }
    }
}
