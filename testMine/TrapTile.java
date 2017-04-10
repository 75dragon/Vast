package testMine;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


/**
 * TrapTile class. Makes a tile a deadly trap with the top left corner being
 * x/y. Constantly check for the player(maybe anything moving?) if they are
 * close by - then damages them. For now, they are instant kill! GG
 *
 * @author Bryan Wu, Austin Cheng
 * @version May 22, 2016
 * @author Period: 3
 * @author Assignment: Java Final
 *
 * @author Sources: Me & U
 */
public class TrapTile extends Tile
{
    Timer check;

    Player victem;


    /**
     * @param passable
     *            passable
     * @param loot
     *            loot
     * @param color
     *            colors
     * @param c
     *            col
     * @param r
     *            row
     * @param world
     *            world
     */
    public TrapTile( boolean passable, int loot, Color color, int c, int r, World world )
    {
        super( passable, loot, color, c, r, 0, world );
        trapTimer();
    }


    public void trapTimer()
    {
        check = new Timer( 250, new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                sneakAttack();
            }
        } );
        check.start();
    }


    public void sneakAttack()
    {
        if ( (victem = world.detectPlayer( c, r, 1 )) != null )
        {
            check.stop();
            victem.takeDamage( 15, "Tile Trap" );
            this.color = Color.CYAN;
            return;
        }
    }

    @Override
    public void blownUp()
    {
        check.stop();
    }
    
    public void removeTile()
    {
        check.stop();
    }
}
