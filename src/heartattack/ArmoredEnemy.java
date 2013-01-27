/**
 * @author Kevin
 */

package heartattack;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ArmoredEnemy extends Enemy
{
    private final static int ANIM_SPEED = 100;
    private static Image[] texture = new Image[4];
    private int curFrame;
    private int animDirection;
    private int armor;
    
    private long lastTime;
    public ArmoredEnemy()
    {
        super();
        velocity.timesE(1/speed);
        health = 120;
        speed = 35;
        velocity.timesE(speed);
        armor = 4;
        
        curFrame = 0;
        animDirection = 1;
    }
    
    @Override
    public void damage(int amt)
    {
        health -= Math.max(amt-armor,0);
    }
    @Override
    public void update(int delta)
    {
        super.update(delta);
        if (System.currentTimeMillis() - lastTime > ANIM_SPEED)
        {
            if (curFrame == 3) {animDirection = -1;}
            else if (curFrame == 0) {animDirection = 1;}
            curFrame += animDirection;
            lastTime = System.currentTimeMillis();
        }
    }
    
    @Override
    public void render(Graphics g)
    {
        texture[curFrame].drawCentered(position.x,position.y);
        g.setColor(Color.green);
        g.fillRect(position.x-width/2, position.y-height/2-10, health/((float)maxHealth)*width, 10.0f);
    }
    public static void loadImages(String foldername) throws SlickException
    {
        texture[0] = new Image(foldername + "/1.png");
        texture[1] = new Image(foldername + "/2.png");
        texture[2] = new Image(foldername + "/3.png");
        texture[3] = new Image(foldername + "/4.png");
    }
}