package testMine;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class BombSackItem extends Item
{
    Timer check;
    int amount;
    Player victem;
    
    public BombSackItem( double x, double y, int bombs, Image img, World world )
    {
        super( x, y, 0, img, world );
        amount = bombs;
        pickupTimer();
    }
    
    public void pickupTimer()
    {
        check = new Timer( 1000, new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                checkArea();
            }
        } );
        check.start();
    }


    public void checkArea()
    {
        if ( (victem = world.detectPlayer( d.getX(), d.getY(), 1 )) != null )
        {
            check.stop();
            victem.addBombs( amount );
            world.itemDeath( this );
            return;
        }
    }
    
}
