
package heartattack;

import java.util.LinkedList;
import org.lwjgl.input.Cursor;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kevin
 */
//This class holds the player's resources and displays the GUI and store
public class Player {
    public enum TowerType {
        BASIC,
        SPLASH,
        FREEZE
    }

    public static Image[] towerTexes = new Image[1];    //images for the store thumbnails
    
    protected static int redBlood = 0;      //the amount of red blood the player has
    protected static int whiteBlood = 0;    //.. white blood..
    protected static int plasma = 200; //.. plasma ..
    
    protected static TowerType heldType;    //The type of tower the player is holding
    protected static Vector2 heldPosition;  //Where the tower is held at before buying(mouse position)
    
    protected static Tower lastSelected;    //The last tower the player has highlighted
    
    protected static boolean isBuying = false;  //Is the player in buy mode?
    
    protected static LinkedList<Tower> towerList;   //A reference to the main app's tower list
    
    protected static LinkedList<StoreIcon> icons = new LinkedList<>(); //A list of tower icons
   
    
    public static void init(LinkedList<Tower> towerList) throws SlickException
    {
        StoreIcon basicTowerIcon = new StoreIcon(TowerType.BASIC);
        basicTowerIcon.setTooltip("A basic shooter tower. \n100 Plasma");
        icons.add(basicTowerIcon);
        
        
        Player.towerList = towerList;
        
        towerTexes[0] = new Image("base.png");
        
        for (StoreIcon i : icons)
        {
            i.init("base.png");
            i.position = new Vector2(50, 550);
        }
        heldPosition = new Vector2();
    }
    
    public static void update(GameContainer gc, int delta)
    {
        for (StoreIcon i: icons)
        {
            i.update(delta);
        }
        if (isBuying)
        {
            //Right click cancels buy mode
            if (InputController.rightMouse)
            {
                isBuying = false;
            }
            //left click places a tower
            if (InputController.leftMouse)
            {
                switch (heldType)
                {
                    case BASIC:
                    {
                        BasicTower bt = new BasicTower();
                        if (bt.plasmaCost <= plasma) 
                        {
                            plasma -= bt.plasmaCost;
                            bt.position = new Vector2(InputController.msPosition.x, InputController.msPosition.y);
                            towerList.add(bt);
                        }
                        break;
                    }
                }
            }
            heldPosition = InputController.msPosition;
        }
        else
        {
            if (InputController.rightMouse)
            {
                if (lastSelected != null) 
                {
                    lastSelected.deselect();
                }
            }
            if (InputController.leftMouse)
            {
                for (StoreIcon i: icons)
                {
                    //If the player has clicked on a tower icon when not in buy mode
                    if (i.boundingBox().intersects(InputController.msPosition))
                    {
                        isBuying = true;
                        heldType = i.getType();
                    }
                }
                for (Tower t: towerList)
                {
                    //If the player has clicked an already placed tower
                    if (t.boundingBox().intersects(InputController.msPosition))
                    {
                        if (lastSelected != null)
                        {
                            lastSelected.deselect();
                        }
                        lastSelected = t;
                        t.select();
                    }
                }
            }
        }
    }
    
    public static void render(Graphics g)
    {
        g.setColor(Color.gray);
        g.fillRect(10,10,380,20);
        g.setColor(Color.yellow);
        g.drawString("Plasma: " + plasma, 20, 12);
        g.setColor(Color.red);
        g.drawString("Red Blood: " + redBlood, 140, 12);
        g.setColor(Color.white);
        g.drawString("White Blood: " + whiteBlood, 260, 12);
        for (StoreIcon i: icons)
        {
            i.render(g);
        }
        if (isBuying)
        {
            switch (heldType)
            {
                //TODO: draw in red if not able to place
                case BASIC :
                {
                    towerTexes[0].draw(heldPosition.x, heldPosition.y, new Color(255,255,255,127));
                }
            }
        }
    }
}
