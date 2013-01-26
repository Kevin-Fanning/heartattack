
package heartattack;

import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;
import java.util.LinkedList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Kevin
 */
public class MainGameState extends BasicGameState{
    private int stateID = -1;
    
    private LinkedList<Enemy> enemyList;
    private LinkedList<Tower> towerList;
    
    
    private TextField tf;
    private TrueTypeFont font;
    
    private Image background;
    
    public MainGameState(int id)
    {
        enemyList = new LinkedList<>();
        towerList = new LinkedList<>();
        
        stateID = id;
        Enemy newEnemy = new Enemy();
        enemyList.add(newEnemy);
        
        BasicTower tower = new BasicTower();
        tower.position = new Vector2(300,150);
        towerList.add(tower);
        font = new TrueTypeFont(new Font("Arial Bold", Font.PLAIN, 12), true);
    }
    
    @Override
    public int getID() {
        return stateID;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        background = new Image("level1.png");
        tf = new TextField(gc, font, 50,50, 100, 30);
        tf.setTextColor(Color.white);
        tf.setBackgroundColor(Color.gray);
        enemyList.getFirst().init("germ.png");
        
        for (Tower i: towerList){
            i.init2("turret.png","base.png");
        }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        InputController.update(gc.getInput());

        for (Enemy i : enemyList)
        {
            i.update(delta);
        }
        for (Tower i : towerList) {     //Update towers and check if the bullets hit anything
            i.aim(enemyList);
            i.update(delta);
            i.fire();
            
            for (Bullet b : i.getBullets())
            {
                for (Enemy e : enemyList)
                {
                    if (b.boundingBox().intersects(e.boundingBox()))
                    {
                        e.damage(i.getDamage());
                        if (e.getHealth() <= 0)
                        {
                            enemyList.remove(e);
                        }
                        i.removeBullet(b);
                    }
                }
            }
        }
        
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
    {
        background.draw(0,0);
        for (Enemy i: enemyList)
        {
            i.render(g);
        }
        for (Tower i: towerList)
        {
            i.render(g);
        }
        
        //tf.render(gc, g);
    }   
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg)
    {
       
    }
}
