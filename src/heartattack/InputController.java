
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
    
    public static boolean leftMouse;
    public static boolean rightMouse;
    
    public static void update(Input i)
    {
        lastInput = input;
        input = i;
        
        leftMouse = input.isMouseButtonDown(0) && !lastInput.isMouseButtonDown(0);
        rightMouse = input.isMouseButtonDown(1) && !lastInput.isMouseButtonDown(1);
    }
    

}
