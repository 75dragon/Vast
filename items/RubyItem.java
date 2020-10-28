package items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import entity.Player;
import world.World;


public class RubyItem extends Item
{
    Timer check;

    int amount;

    Player victem;


    public RubyItem( double x, double y, BufferedImage img, World world )
    {
        super( x, y, 0, img, world );
        amount = 750;
    }

    public void checkArea()
    {
        if ( ( victem = world.detectPlayer( d.getX(), d.getY(), 1 ) ) != null )
        {
            victem.addGold( amount );
            world.getDis().getWriter().addText( "Aquired a Ruby" );
            world.itemDeath( this );
            return;
        }
    }
}
