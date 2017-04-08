package testMine;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class GoldBarItem extends Item
{
    Timer check;
    int amount;
    Player victem;
    
    public GoldBarItem( double x, double y, int goldAmount, Image img, World world )
    {
        super( x, y, 0, img, world );
        amount = goldAmount;
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
            victem.addGold( 1000 );
            world.itemDeath( this );
            return;
        }
    }
}
