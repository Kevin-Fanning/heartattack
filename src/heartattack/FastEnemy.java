/**
 * @author Kevin
 */

package heartattack;

public class FastEnemy extends Enemy
{
    public FastEnemy()
    {
        super();
        velocity.timesE(1/speed);
        health = 50;
        speed = 90;
        velocity.timesE(speed);
    }
}