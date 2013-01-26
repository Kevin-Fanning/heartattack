
package heartattack;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.*;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.TrueTypeFont;
import java.awt.Font;
import org.newdawn.slick.Color;
/**
 *
 * @author Kevin
 */
public class MainMenuState extends BasicGameState {
    private int stateID = -1;
    private Enemy newEnemy;
    private BasicTower tower;
    
    private TextField tf;
    private TrueTypeFont font;
    
    public MainMenuState(int id)
    {
        stateID = id;
        
        newEnemy = new Enemy();
        newEnemy.setVelocity(new Vector2(50.0f,1.0f));
        
        tower = new BasicTower();
        tower.position = new Vector2(400,300);
        
        font = new TrueTypeFont(new Font("Arial Bold", Font.PLAIN, 12), true);
    }
    
    @Override
    public int getID() {
        return stateID;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        tf = new TextField(gc, font, 50,50, 100, 30);
        tf.setTextColor(Color.white);
        tf.setBackgroundColor(Color.gray);
        newEnemy.init("germ.png");
        
        tower.init("turret.png","base.png");
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        //TODO: Get some input updates
        InputController.update(gc.getInput());

        newEnemy.update(delta);
        tower.aim(newEnemy.position);
        tower.update(delta);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
    {
        //TODO: Draw some buttons
        newEnemy.render();
        tower.render();
        
        //tf.render(gc, g);
    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg)
    {
        
    }
    
}
