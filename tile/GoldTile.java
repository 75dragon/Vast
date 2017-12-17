package tile;

import java.awt.Color;

import world.World;


/**
 * GoldTile class.
 *
 * @author Bryan Wu
 * @version May 22, 2016
 * @author Period: 3
 * @author Assignment: TODO
 *
 * @author Sources: TODO
 */
public class GoldTile extends Tile
{
    /**
     * @param c
     *            col
     * @param r
     *            row
     * @param world
     *            world
     */
    public GoldTile( int c, int r, World world )
    {
        super( false, 1000, Color.YELLOW, c, r, 3, world );
    }


    public void blownUp()
    {
        if ( destroyed == false )
        {
            pass = true;
            color = World.getBrown();
            world.addGoldBar( c, r );
            loot = 0;
            image = null;
            destroyed = true;
            health = 0;
        }
    }
}
