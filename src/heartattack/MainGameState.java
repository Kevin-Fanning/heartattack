
package heartattack;

import java.awt.Font;
import java.util.Iterator;
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
    
    private EnemyWave enemyWave;
    private LinkedList<Tower> towerList;
    
    
    private TextField tf;
    private TrueTypeFont font;
    
    private Image background;
    
    public MainGameState(int id)
    {
        
        towerList = new LinkedList<>();
        
        stateID = id;
        
        enemyWave = new EnemyWave();
        
        BasicTower tower = new BasicTower();
        tower.position = new Vector2(300,150);
        towerList.add(tower);
        BasicTower tower2 = new BasicTower();
        tower2.position = new Vector2(250, 150);
        towerList.add(tower2);
        BasicTower tower3 = new BasicTower();
        tower3.position = new Vector2(350, 150);
        towerList.add(tower3);
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
        enemyWave.init();
        
        for (Tower i: towerList){
            i.init2("turret.png","base.png");
        }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        InputController.update(gc.getInput());

        enemyWave.update(delta);
        
        for (Tower i : towerList) {     //Update towers and check if the bullets hit anything
            i.aim(enemyWave.getEnemies());
            i.update(delta);
            i.fire();
            
            for (Bullet b : i.getBullets())
            {
                //For some reason you need to use iterators if you are removing
                Iterator<Enemy> itr = enemyWave.getEnemies().listIterator();
                while (itr.hasNext())
                {
                    Enemy e = itr.next();
                    if (b.boundingBox().intersects(e.boundingBox()))
                    {
                        e.damage(i.getDamage());
                        if (e.getHealth() <= 0)
                        {
                            itr.remove();
                            i.reacquire(enemyWave.getEnemies());
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
        
        enemyWave.render(g);
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
