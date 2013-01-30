
package heartattack;

import java.util.Iterator;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.*;
import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
/**
 *
 * @author Kevin
 */
public class MainGameState extends BasicGameState{
    private int stateID = -1;
    
    private EnemyWave enemyWave;
    private LinkedList<Tower> towerList;
    
    
    private Sound ekg;
   // private Music bgMusic;
    
    private boolean justLost;
    
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
        ekg = new Sound("heartBeat.wav");

       // bgMusic = new Music("Controlled Chaos.ogg");

        
        justLost = false;
        
        towerList = new LinkedList<>();
 
        enemyWave = new EnemyWave();
        enemyWave.loadLevel(Player.levelName);
        Player.towerList = towerList;
        BasicTower.loadImages("turret.png","base.png");
        LaserTower.loadImages("pturret.png","pbase.png");
        CannonTower.loadImages("gturret.png","gbase.png");
        FreezeTower.loadImage("bbase.png");
        Bullet.loadImage("bullet.png");
        Mine.loadImage("mine.png");
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
        if (enemyWave.isBeaten() && InputController.input.isKeyPressed(Input.KEY_SPACE))
        {
            sbg.enterState(HeartAttack.MAIN_MENU_STATE);
        }
        if (Player.redBlood <= 0 && InputController.input.isKeyPressed(Input.KEY_SPACE))
        {
            sbg.enterState(HeartAttack.MAIN_MENU_STATE);
        }
        if (Player.redBlood <= 0 && !ekg.playing() && justLost == false)
        {
            //bgMusic.fade(3000, 0, true);
            ekg.play();
            justLost = true;
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
        Level.background.draw(0,0);
        
        enemyWave.render(g);
        for (Tower i: towerList)
        {
            i.render(g);
        }
        
        Player.render(g);
        
        if (enemyWave.isBeaten())
        {
            g.setColor(new Color(0,0,0,80));
            g.fillRect(0,0,gc.getWidth(), gc.getHeight());
            g.setColor(new Color(255,255,255,255));
            g.drawString("YOU WON", 350, 300);
        }
        if (Player.redBlood <= 0)
        {
            g.setColor(new Color(0,0,0,200));
            g.fillRect(0,0,gc.getWidth(),gc.getHeight());
            g.setColor(new Color(255,255,255,255));
            g.drawString("We lost another one...", 350, 300);
        }
    }   
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        init(gc, sbg);
//        if (!bgMusic.playing()) {
//            bgMusic.play();
//        }
    }
}
