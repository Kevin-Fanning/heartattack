/**
 * @author Kevin
 */

package heartattack;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class FastEnemy extends Enemy
{
    private final static long ANIM_SPEED = 250;
    public static Image[] texture = new Image[3];
    
    private int curFrame;
    private long lastTime;
    
    
    public FastEnemy()
    {
        super();
        velocity.timesE(1/speed);
        health = 50;
        speed = 110;
        velocity.timesE(speed);
        curFrame = 0;
    }
    
    @Override
    public void update(int delta)
    {
        super.update(delta);
        if (System.currentTimeMillis() - lastTime > ANIM_SPEED)
        {
            curFrame++;
            if (curFrame > 2) {curFrame = 0;}
            lastTime = System.currentTimeMillis();
        }
    }
    
    @Override
    public void render(Graphics g)
    {
        texture[curFrame].drawCentered(position.x, position.y);
        g.setColor(Color.green);
        g.fillRect(position.x-width/2, position.y-height/2-10, health/((float)maxHealth)*width, 10.0f);
    }
    public static void loadImage(String folderName) throws SlickException
    {
        texture[0] = new Image(folderName + "/1.png");
        texture[1] = new Image(folderName + "/2.png");
        texture[2] = new Image(folderName + "/3.png");
    }
}