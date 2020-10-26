package items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import entity.Player;
import world.World;


public class TreasureChestItem extends Item
{
    Timer check;

    Player victem;


    public TreasureChestItem( double x, double y, int gold, BufferedImage img, World world )
    {
        super( x, y, gold, img, world );
        treasureTimer();
    }


    public void treasureTimer()
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
        if ( ( victem = world.detectPlayer( d.getX(), d.getY(), 1 ) ) != null )
        {
            check.stop();
            victem.addGold( 2500 );
            world.getDis().getWriter().addText( "Aquired a Treasure Chest" );
            world.itemDeath( this );
            return;
        }
    }
}
