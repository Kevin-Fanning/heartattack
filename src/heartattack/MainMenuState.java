
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

    
    public MainMenuState(int id)
    {
        stateID = id;

    }
    
    @Override
    public int getID() {
        return stateID;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
            sbg.enterState(HeartAttack.GAME_STATE);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        //TODO: Get some input updates
        InputController.update(gc.getInput());

        
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
    {
        //TODO: Draw some buttons

    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg)
    {
        
    }
    
}
