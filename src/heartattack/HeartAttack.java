
package heartattack;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Kevin
 */
public class HeartAttack extends StateBasedGame {
    public final static int MAIN_MENU_STATE = 0;
    public final static int LEVEL_SELECT_STATE = 1;
    public final static int GAME_STATE = 2;
    
    public HeartAttack()
    {
        super("Heart Attack");
    }
    @Override
    public void initStatesList(GameContainer gc) throws SlickException
    {
        this.addState(new MainMenuState(MAIN_MENU_STATE));
        this.addState(new LevelSelectState(LEVEL_SELECT_STATE));
        this.addState(new MainGameState(GAME_STATE));
        
    }

    
    public static void main(String[] args) throws SlickException
    {
        
        
        HeartAttack h = new HeartAttack();
        AppGameContainer app = new AppGameContainer(h);
        
        app.setDisplayMode(800,600, false);
        app.setMinimumLogicUpdateInterval(2);
        
        app.setShowFPS(false);
        
        app.start();
    }
}
