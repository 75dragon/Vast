package items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import world.World;


public class TemporaryItem extends Item
{
    Timer temp;

    double countDown;


    /**
     * Draws a temporary sprite for countDown seconds
     * 
     * @param x
     * @param y
     * @param countDown
     * @param img
     * @param world
     */
    public TemporaryItem( double x, double y, double countDown, BufferedImage img, World world )
    {
        super( x, y, 0, img, world );
        this.countDown = countDown * 1000;
    }

    public void itemTick()
    {
    	System.out.println("temptick");
    	countDown = countDown - 10;
    	if ( countDown < 0 )
    	{
            world.itemDeath( this );
    	}
    }
}
