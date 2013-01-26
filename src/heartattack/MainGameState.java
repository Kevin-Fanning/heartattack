
package heartattack;

import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;
import java.util.LinkedList;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Kevin
 */
public class MainGameState extends BasicGameState{
    private int stateID = -1;
    
    private LinkedList<Enemy> enemyList;
    private BasicTower tower;
    
    private TextField tf;
    private TrueTypeFont font;
    
    public MainGameState(int id)
    {
        enemyList = new LinkedList<>();
        stateID = id;
        Enemy newEnemy = new Enemy();
        newEnemy.setVelocity(new Vector2(50.0f,1.0f));
        enemyList.add(newEnemy);
        
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
        enemyList.getFirst().init("germ.png");
        
        tower.init("turret.png","base.png");
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        InputController.update(gc.getInput());

        for (Enemy i : enemyList)
        {
            i.update(delta);
        }
        tower.aim(enemyList);
        tower.update(delta);
        tower.fire();
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
    {
        for (Enemy i: enemyList)
        {
            i.render();
        }
        tower.render();
        
        //tf.render(gc, g);
    }   
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg)
    {
       
    }
}
