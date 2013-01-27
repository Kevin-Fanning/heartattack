
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
    
    
    private Image background;
    
    public MainGameState(int id)
    {
        stateID = id;
        

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
                try {
            Level.loadLevel("level1");
        } catch (SlickException e)
        {
            System.out.println(e.getMessage());
        }
        towerList = new LinkedList<>();
        
        
        
        enemyWave = new EnemyWave();
        
        Player.towerList = towerList;
        background = new Image("level1.png");
        BasicTower.loadImages("turret.png","base.png");
        LaserTower.loadImages("turret.png","base.png");
        CannonTower.loadImages("turret.png","base.png");
        FreezeTower.loadImage("base.png");
        Bullet.loadImage("bullet.png");
        Enemy.loadImage("germ.png");
        FastEnemy.loadImage("ameoba");
        ArmoredEnemy.loadImages("egg");
        Player.init(towerList);   
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        InputController.update(gc.getInput());

        enemyWave.update(delta);
        if (enemyWave.isBeaten())
        {
            sbg.enterState(HeartAttack.MAIN_MENU_STATE);
        }
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
                        i.removeBullet(b);
                    }
                }
            }
        }
        Player.update(gc, delta);
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
        
        Player.render(g);
    }   
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        init(gc, sbg);
    }
}
