
package heartattack;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kevin
 */
public class StoreIcon extends Sprite {
    private String tooltipText = "";
    private boolean displayTooltip = false;
    private Player.TowerType towerType;
    
    private int cost;
    public StoreIcon(Player.TowerType towerType)
    {
        super();
        this.towerType = towerType;
    }
    public StoreIcon(float x, float y)
    {
        super();
        position.x = x; position.y = y;
    }
    
    //Displays a tooltip
    public void tooltip(Graphics g)
    {
        g.setColor(new Color(230,220,150,255));
        g.fillRect(InputController.input.getMouseX(), InputController.input.getMouseY()-50, 200, 50);
        g.setColor(Color.black);
        g.drawString(tooltipText, InputController.msPosition.x, InputController.msPosition.y-50);
        //TODO: write some text
    }
    
    @Override
    public void render(Graphics g) 
    {
        super.render(g);
        if (this.boundingBox().intersects(InputController.msPosition))
        {
            tooltip(g);
        }
    }
    
    public int getCost()
    {
        return cost;
    }
    public Player.TowerType getType()
    {
        return towerType;
    }
    
    public void setTooltip(String text)
    {
        tooltipText = text;
    }
}
