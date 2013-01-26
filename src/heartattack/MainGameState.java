
package heartattack;

import java.util.Iterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
    
    private Player player;
    
    private Image background;
    
    public MainGameState(int id)
    {
        
        towerList = new LinkedList<>();
        
        stateID = id;
        
        enemyWave = new EnemyWave();
        
        player = new Player();
    }
    
    //Required for Slick2D. ignore it
    @Override
    public int getID() {
        return stateID;
    }
    
    //Initialize values for everything
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        background = new Image("level1.png");

        enemyWave.init();
        
        for (Tower i: towerList){
            i.init2("turret.png","base.png");
        }
        
        player.init();
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        InputController.update(gc.getInput());
        //Place a tower on click
        if (InputController.input.isMousePressed(0))
        {
            BasicTower t = new BasicTower();
            try {
                t.init2("turret.png","base.png");
            }catch (SlickException e)
            { 
                System.out.println(e.getMessage());
            }
            t.position.x = InputController.input.getMouseX();
            t.position.y = InputController.input.getMouseY();
            towerList.add(t);
        }

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
        player.update(delta);
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
        
        player.render(g);
    }   
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg)
    {
       
    }
}
