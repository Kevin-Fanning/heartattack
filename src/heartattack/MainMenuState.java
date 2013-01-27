
package heartattack;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.*;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.TrueTypeFont;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
/**
 *
 * @author Kevin
 */
public class MainMenuState extends BasicGameState {
    private int stateID = -1;

    private TrueTypeFont font;
    private TextField tf;
    
    private boolean loadError;
    File workingDirectory;
    ArrayList<String> levels;
    
    private Image bg;
    
    public MainMenuState(int id)
    {
        stateID = id;
        font = new TrueTypeFont(new Font("Arial Bold",Font.PLAIN, 16), true);
        loadError = false;
        levels = new ArrayList<>();
    }
    
    @Override
    public int getID() {
        return stateID;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
            //sbg.enterState(HeartAttack.GAME_STATE);
            tf = new TextField(gc, font, 350, 300, 100, 20);
            tf.setBackgroundColor(Color.gray);
            workingDirectory = new File("src/");
            
            bg = new Image("title.png");
            
            for (File f: workingDirectory.listFiles())
            {
                String temp = f.getName();
                String ext = "";
                if (f.isFile()) 
                {
                    ext = temp.substring(temp.length()-3, temp.length());
                }
                if ("xml".equals(ext) && !"TowerSpecs.xml".equals(temp))
                {
                    levels.add(temp.substring(0, temp.length()-4));
                }
            }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        //TODO: Get some input updates
        InputController.update(gc.getInput());
        //sbg.enterState(HeartAttack.GAME_STATE);
        
        if (InputController.input.isKeyPressed(Input.KEY_ENTER))
        {
            try {
                Player.levelName = tf.getText();
                Player.loadLevel();
                sbg.enterState(HeartAttack.GAME_STATE);
            }
            catch (SlickException e) 
            {
                loadError = true;
            }
        }
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
    {
        bg.draw(0,0);
        for (int i = 0; i < levels.size(); ++i)
        {
            
            if (new Rect(90,90 + i*50, 100, 40).intersects(InputController.msPosition))
            {
                g.setColor(Color.gray);
                g.fillRect(90,90+i*50,100,40);
                if (InputController.leftMouse)
                {
                    Player.levelName = levels.get(i);
                    try {
                        Player.loadLevel();
                        sbg.enterState(HeartAttack.GAME_STATE);
                    } catch (SlickException e) {}
                }
            }
            g.setColor(Color.white);
            g.drawString(levels.get(i), 100, 100 + i*50);
            g.drawString("Music: \nControlled Chaos - Kevin MacLeod\nIncompetech.com", 500,520);
        }
    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg)
    {
        
    }
    
}
