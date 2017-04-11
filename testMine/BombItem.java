package testMine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;


public class BombItem extends Item
{
    Timer timeBomb;

    int countDown;


    public BombItem( double x, double y, int countDown, BufferedImage img, World world )
    {
        super( x, y, 0, img, world );
        this.countDown = countDown;
        bombArea();
    }


    public void bombArea()
    {
        Item theBomb = this;
        timeBomb = new Timer( countDown * 1000, new ActionListener()
        {

            @Override
            public void actionPerformed( ActionEvent e )
            {
                timeBomb.stop();
                world.clearArea( d.getX(), d.getY(), 3, 15 );
                System.out.println( d.getX() + " " + d.getY() + " BOOM!" );
                world.itemDeath( theBomb );
            }

        } );
        timeBomb.start();
    }


    public void removeItem()
    {
        timeBomb.stop();
    }

}
