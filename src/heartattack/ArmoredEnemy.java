/**
 * @author Kevin
 */

package heartattack;

public class ArmoredEnemy extends Enemy
{
    private int armor;
    public ArmoredEnemy()
    {
        super();
        health = 120;
        speed = 35;
        armor = 4;
    }
    @Override
    public void damage(int amt)
    {
        health -= (amt-armor);
    }
}