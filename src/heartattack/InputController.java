
package heartattack;

import org.newdawn.slick.Input;
import org.newdawn.slick.GameContainer;

/**
 *
 * @author Kevin
 */
public class InputController {
    public static Input input;
    private static Input lastInput;
    public static Vector2 msPosition = Vector2.zero();
    
    public static boolean leftMouse;
    public static boolean rightMouse;
    
    public static void update(Input i)
    {
        lastInput = input;
        input = i;
        
        msPosition = new Vector2(input.getMouseX(), input.getMouseY());
        leftMouse = input.isMousePressed(0);
        rightMouse = input.isMousePressed(1);
    }
    

}
