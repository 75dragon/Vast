package testMine;

import java.awt.Color;


public class RegularTile extends Tile
{
    /**
     * Used to make the 2 basic tiles, dark and passable. Will have hp 0 or 1 based of passable.
     * @param passable
     * @param loot
     * @param color
     * @param x
     * @param y
     * @param world
     */
    public RegularTile( boolean passable, int loot, Color color, int x, int y, World world )
    {
        super( passable, loot, color, x, y, passable?0:1, world );
    }
}
