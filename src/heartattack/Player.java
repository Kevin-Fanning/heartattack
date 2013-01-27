
package heartattack;

import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

//XML stuff
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

/**
 *
 * @author Kevin
 */
//This class holds the player's resources and displays the GUI and store
//Behold the power of the blob class. oops :(
public class Player {
    public enum TowerType {
        BASIC,
        LASER,
        CANNON,
        SPLASH,
        FREEZE
    }

    public static Image[] towerTexes = new Image[3];    //images for the store thumbnails
    
    protected static int redBlood = 20;      //the amount of red blood the player has
    protected static int whiteBlood = 0;    //.. white blood..
    protected static int plasma = 200; //.. plasma ..
    
    protected static TowerType heldType;    //The type of tower the player is holding
    protected static Vector2 heldPosition;  //Where the tower is held at before buying(mouse position)
    protected static boolean canPlace;
    
    protected static Tower lastSelected;    //The last tower the player has highlighted
    
    protected static boolean isBuying = false;  //Is the player in buy mode?
    
    protected static LinkedList<Tower> towerList;   //A reference to the main app's tower list
    
    protected static LinkedList<StoreIcon> icons = new LinkedList<>(); //A list of tower icons
   
    protected static Image layerMask;
    
    protected static DocumentBuilderFactory dbf;
    protected static DocumentBuilder db;
    protected static File xmlFile;
    protected static Element basicTowerEl;
    protected static Element laserTowerEl;
    protected static Element cannonTowerEl;
    
    public static void init(LinkedList<Tower> towerList) throws SlickException
    {
        plasma = 200;
        redBlood = 20;
        
        StoreIcon basicTowerIcon = new StoreIcon(TowerType.BASIC);
        basicTowerIcon.setTooltip("A basic shooter tower. \n100 Plasma");
        basicTowerIcon.init("base.png");
        basicTowerIcon.position = new Vector2(100,550);
        icons.add(basicTowerIcon);
        
        StoreIcon laserTowerIcon = new StoreIcon(TowerType.LASER);
        laserTowerIcon.setTooltip("Laser tower. \n300 Plasma");
        laserTowerIcon.init("base.png");
        laserTowerIcon.position = new Vector2(150,550);
        icons.add(laserTowerIcon);
        
        StoreIcon cannonTowerIcon = new StoreIcon(TowerType.CANNON);
        cannonTowerIcon.setTooltip("Infrequent power\n250 Plasma");
        cannonTowerIcon.init("base.png");
        cannonTowerIcon.position = new Vector2(200,550);
        icons.add(cannonTowerIcon);
        
        Player.towerList = towerList;
        
        towerTexes[0] = BasicTower.base;
        towerTexes[1] = LaserTower.base;
        towerTexes[2] = CannonTower.base;
        
        layerMask = Level.getLayerMask();
        
        
        heldPosition = new Vector2();
        
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            xmlFile = new File("src/TowerSpecs.xml");
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nl = doc.getElementsByTagName("Tower");
            for (int i = 0; i < nl.getLength(); ++i)
            {
                if ("Basic".equals(((Element)nl.item(i)).getAttribute("name")))
                {
                    basicTowerEl = (Element)nl.item(i);
                    BasicTower.plasmaCost = Integer.parseInt(basicTowerEl.getElementsByTagName("plasmaCost").item(0).getTextContent());
                }
                else if ("Laser".equals(((Element)nl.item(i)).getAttribute("name")))
                {
                    laserTowerEl = (Element)nl.item(i);
                    LaserTower.plasmaCost = Integer.parseInt(laserTowerEl.getElementsByTagName("plasmaCost").item(0).getTextContent());
                }
                else if ("Cannon".equals(((Element)nl.item(i)).getAttribute("name")))
                {
                    cannonTowerEl = (Element)nl.item(i);
                    CannonTower.plasmaCost = Integer.parseInt(cannonTowerEl.getElementsByTagName("plasmaCost").item(0).getTextContent());
                }
            }
        }
        catch (Exception e) {System.out.println(e.getMessage());}
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
                        if (canPlace) 
                        {
                            
                            bt.range = Integer.parseInt(basicTowerEl.getElementsByTagName("range").item(0).getTextContent());
                            bt.fireRate = Integer.parseInt(basicTowerEl.getElementsByTagName("fireRate").item(0).getTextContent());
                            bt.damage = Integer.parseInt(basicTowerEl.getElementsByTagName("damage").item(0).getTextContent());
                            BasicTower.fireSpeed = Integer.parseInt(basicTowerEl.getElementsByTagName("fireSpeed").item(0).getTextContent());
                            bt.position = new Vector2(InputController.msPosition.x, InputController.msPosition.y);
                            towerList.add(bt);
                            plasma -= BasicTower.plasmaCost;
                        }
                        break;
                    }
                    case LASER:
                    {
                        LaserTower lt = new LaserTower();
                        if (canPlace)
                        {
                            lt.range = Integer.parseInt(laserTowerEl.getElementsByTagName("range").item(0).getTextContent());
                            lt.fireRate = Integer.parseInt(laserTowerEl.getElementsByTagName("fireRate").item(0).getTextContent());
                            lt.damage = Integer.parseInt(laserTowerEl.getElementsByTagName("damage").item(0).getTextContent());
                            LaserTower.fireSpeed = Integer.parseInt(laserTowerEl.getElementsByTagName("fireSpeed").item(0).getTextContent());
                            lt.position = new Vector2(InputController.msPosition.x, InputController.msPosition.y);
                            towerList.add(lt);
                            plasma -= LaserTower.plasmaCost;
                        }
                        break;
                    }
                    case CANNON:
                    {
                        CannonTower ct = new CannonTower();
                        if (canPlace)
                        {
                            ct.range = Integer.parseInt(cannonTowerEl.getElementsByTagName("range").item(0).getTextContent());
                            ct.fireRate = Integer.parseInt(cannonTowerEl.getElementsByTagName("fireRate").item(0).getTextContent());
                            ct.damage = Integer.parseInt(cannonTowerEl.getElementsByTagName("damage").item(0).getTextContent());
                            CannonTower.fireSpeed = Integer.parseInt(cannonTowerEl.getElementsByTagName("fireSpeed").item(0).getTextContent());
                            ct.position = new Vector2(InputController.msPosition.x, InputController.msPosition.y);
                            towerList.add(ct);
                            plasma -= CannonTower.plasmaCost;
                        }
                        break;
                    }  
                }
            }
            canPlace = true;
            for (Tower t: towerList)
            {
                if (t.boundingBox().intersects(new Rect(heldPosition.x, heldPosition.y, towerTexes[0].getWidth(), towerTexes[0].getHeight())))
                {
                    canPlace = false;
                }
            }
            switch (heldType)
            {
                case BASIC:
                {
                    if (plasma < BasicTower.plasmaCost)
                    {
                        canPlace = false;
                    }
                    break;
                }
                case LASER:
                {
                    if (plasma < LaserTower.plasmaCost)
                    {
                        canPlace = false;
                    }
                }
            }
            //TODO: if tower is not on the walls, canPlace = false
            Color curColor = layerMask.getColor((int)InputController.msPosition.x, (int)InputController.msPosition.y);
            if (curColor.r == 1.0f)
            {
                canPlace = false;
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
        //Display the currencies
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
                case BASIC :
                {
                    if (canPlace)
                    {
                        towerTexes[0].draw(heldPosition.x, heldPosition.y, new Color(255,255,255,127));//No tint, but half transparency
                    }
                    else
                    {
                        towerTexes[0].draw(heldPosition.x, heldPosition.y, new Color(255, 50,50,127));//Red tint and half transparency
                    }
                    break;
                }
                case LASER:
                {
                    if (canPlace)
                    {
                        towerTexes[1].draw(heldPosition.x, heldPosition.y, new Color(255,255,255,127));
                    }
                    else
                    {
                        towerTexes[1].draw(heldPosition.x, heldPosition.y, new Color(255,50,50,127));
                    }
                    break;
                }
                case CANNON:
                {
                    if (canPlace)
                    {
                        towerTexes[2].draw(heldPosition.x, heldPosition.y, new Color(255,255,255,127));
                    }
                    else
                    {
                        towerTexes[2].draw(heldPosition.x, heldPosition.y, new Color(255,50,50,127));
                    }
                    break;
                }
            }
        }
    }
    
    public static void loseLife()
    {
        redBlood--;
    }
    public static void addPlasma(int amt)
    {
        plasma += amt;
    }
}
