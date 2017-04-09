package testMine;

import java.awt.Color;

public class RegularTile extends Tile
{
    public RegularTile( boolean passable, int loot, Color color, int x , int y, World world )
    {
        super( passable, loot, color, x, y, world );
    } 
}
