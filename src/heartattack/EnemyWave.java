
package heartattack;

import java.util.Iterator;
import java.util.LinkedList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kevin
 */
public class EnemyWave {
    protected LinkedList<Enemy> enemyQue;
    protected LinkedList<Enemy> deployedList;
    
    protected int delayTime;
    protected long lastRelease;
    
    //TODO: make this load from a file or something
    public EnemyWave()
    {
        enemyQue = new LinkedList<>();
        deployedList = new LinkedList<>();
        for (int i = 0; i < 10; ++i)
        {
            Enemy e = new Enemy();
            enemyQue.add(e);
        }
        
        delayTime = 2000;
        lastRelease = System.currentTimeMillis();
    }
    
    public void init() throws SlickException
    {
        for (Enemy e: enemyQue)
        {
            e.init("germ.png");
        }
    }
    
    public void update(int delta)
    {
        for (Enemy e: deployedList)
        {
            e.update(delta);
        }
        if (System.currentTimeMillis() - lastRelease > delayTime)
        {
            if (!enemyQue.isEmpty())
            {
                deployedList.add(enemyQue.poll());
                lastRelease = System.currentTimeMillis();
            }
        }
    }
    
    public void render(Graphics g)
    {
        for (Enemy e: deployedList)
        {
            e.render(g);
        }
    }
    
    //Get the deployed list for targeting/collision
    public LinkedList<Enemy> getEnemies()
    {
        return deployedList;
    }
    
    public void remove(Enemy e)
    {
        deployedList.removeFirst();
    }
}
