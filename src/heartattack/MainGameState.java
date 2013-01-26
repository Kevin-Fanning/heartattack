/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartattack;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.*;
/**
 *
 * @author Kevin
 */
public class MainGameState extends BasicGameState{
    private int stateID = -1;
    
    public MainGameState(int id)
    {
        stateID = id;
    }
    
    @Override
    public int getID() {
        return stateID;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg)
    {
        
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
    {
        
    }    
}
