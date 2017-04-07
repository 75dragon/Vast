package testMine;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


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
    public TemporaryItem( double x, double y, double countDown, Image img, World world )
    {
        super( x, y, 0, img, world );
        this.countDown = countDown;
        TempSprite();
    }


    public void TempSprite()
    {
        Item theSprite = this;
        temp = new Timer( (int)( countDown * 1000 ), new ActionListener()
        {

            @Override
            public void actionPerformed( ActionEvent e )
            {
                temp.stop();
                world.itemDeath( theSprite );
            }

        } );
        temp.start();
    }


    public void removeItem()
    {
        temp.stop();
    }
}
