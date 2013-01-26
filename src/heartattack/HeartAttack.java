/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartattack;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Kevin
 */
public class HeartAttack extends BasicGame {

    public HeartAttack()
    {
        super("Heart Attack");
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        
    }
    
    @Override
    public void update(GameContainer gc, int delta) throws SlickException
    {
        
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        
    }
    
    public static void main(String[] args) throws SlickException
    {
        HeartAttack h = new HeartAttack();
        AppGameContainer app = new AppGameContainer(h);
        
        app.setDisplayMode(800,600, false);
        app.start();
    }
}
