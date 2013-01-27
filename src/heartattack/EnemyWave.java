
package heartattack;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Kevin
 */
public class EnemyWave {
    protected LinkedList<Enemy> enemyQue;
    protected LinkedList<Enemy> deployedList;
    
    protected int delayTime;
    protected long lastRelease;
    
    protected int currentWave;
    protected NodeList waveNodes;
    
    protected boolean levelBeaten = false;
    
    //TODO: make this load from a file or something
    public EnemyWave()
    {
        enemyQue = new LinkedList<>();
        deployedList = new LinkedList<>();
        loadLevel("src/level1.xml");
        
        delayTime = 2000;
        lastRelease = System.currentTimeMillis();
        currentWave = 1;
        levelBeaten = false;
    }
    
    private void fillQue()
    {
        if (currentWave < waveNodes.getLength())
        {
            NodeList waveContents = waveNodes.item(currentWave).getChildNodes();
            for (int i = 0; i < waveContents.getLength(); ++i)
            {
                if (waveContents.item(i).getNodeName().equals("Normal"))
                {
                    float diff = Float.parseFloat(((Element)waveContents.item(i)).getAttribute("dif"));
                    for (int j = 0; j < Integer.parseInt(waveContents.item(i).getTextContent()); ++j)
                    {
                        Enemy e = new Enemy();
                        
                        e.setHealth(e.health*diff);
                        e.bounty *= diff;
                        enemyQue.add(e);
                    }
                }
                if (waveContents.item(i).getNodeName().equals("Fast"))
                {
                    float diff = Float.parseFloat(((Element)waveContents.item(i)).getAttribute("dif"));
                    for (int j = 0; j < Integer.parseInt(waveContents.item(i).getTextContent()); ++j)
                    {
                        Enemy e = new FastEnemy();
                        
                        e.setHealth(e.health*diff);
                        e.bounty *= diff;
                        enemyQue.add(e);
                    }
                }
                if (waveContents.item(i).getNodeName().equals("Armored"))
                {
                    float diff = Float.parseFloat(((Element)waveContents.item(i)).getAttribute("dif"));
                    for (int j = 0; j < Integer.parseInt(waveContents.item(i).getTextContent()); ++j)
                    {
                        Enemy e = new ArmoredEnemy();
                        
                        e.setHealth(e.health*diff);
                        e.bounty *= diff;
                        enemyQue.add(e);
                    }
                }
            }
            currentWave++;
        }
        else
        {
            //TODO: YOU BEAT THE LEVEL!!
            levelBeaten = true;
        }
    }
    
    public void loadLevel(String filename)
    {
        try 
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File xmlFile = new File(filename);
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();
            waveNodes = doc.getElementsByTagName("Wave");
            fillQue();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    public void update(int delta)
    {
        Iterator<Enemy> itr = deployedList.listIterator();
        while (itr.hasNext()) 
        {
            Enemy e = itr.next();
            
            e.update(delta);
            if (e.isFinished())
            {
                itr.remove();
            }
            else if (e.health <= 0)
            {
                Player.addPlasma(e.bounty);
                itr.remove();
            }
        }
        if (System.currentTimeMillis() - lastRelease > delayTime)
        {
            if (!enemyQue.isEmpty())
            {
                deployedList.add(enemyQue.poll());
                lastRelease = System.currentTimeMillis();
            }
        }
        
        if (deployedList.isEmpty() && enemyQue.isEmpty())
        {
            fillQue();
        }
    }
    
    public void render(Graphics g)
    {
        for (Enemy e: deployedList)
        {
            e.render(g);
        }
        g.setColor(Color.black);
        g.fillRect(690,540,100,60);
        g.setColor(Color.white);
        g.drawString("Wave: " + currentWave, 700, 550);
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
    public boolean isBeaten()
    {
        return levelBeaten;
    }
}
