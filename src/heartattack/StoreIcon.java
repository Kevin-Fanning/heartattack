
package heartattack;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import java.awt.Font;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Kevin
 */
public class StoreIcon extends Sprite {
    private String tooltipText;
    private boolean displayTooltip = false;
    
    private Font font;
    private TrueTypeFont uFont;
    
    private int cost;
    public StoreIcon()
    {
        super();
        font = new Font("Arial Bold", Font.PLAIN, 12);
        uFont = new TrueTypeFont(font, true);
    }
    public StoreIcon(float x, float y)
    {
        super();
        position.x = x; position.y = y;
    }
    public void tooltip(Graphics g)
    {
        g.setColor(new Color(230,220,150,255));
        g.fillRect(InputController.input.getMouseX(), InputController.input.getMouseY()-50, 200, 50);
        g.setColor(Color.black);
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
}
