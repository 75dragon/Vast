package items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import entity.Player;
import world.World;


public class HealthPotItem extends Item
{
    Timer check;

    int amount;

    Player victim;


    public HealthPotItem( double x, double y, int heal, BufferedImage img, World world )
    {
        super( x, y, 0, img, world );
        amount = heal;
    }

    public void checkArea()
    {
        if ( ( victim = world.detectPlayer( d.getX(), d.getY(), 1 ) ) != null )
        {
            victim.healHealth( amount );
            world.getDis().getWriter().addText( "Health Restored" );
            world.itemDeath( this );
            return;
        }
    }
}
