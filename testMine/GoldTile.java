package testMine;

import java.awt.Color;

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
        super( false, 1000, Color.YELLOW, c, r, world );
    }

}
