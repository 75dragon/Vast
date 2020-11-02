package tile;

import java.awt.Color;

import world.World;

public class SilverTile extends Tile
{

	boolean gold;

	public SilverTile(int col, int row, World world)
	{
		// TODO Auto-generated constructor stub
		super(false, 500, Color.GRAY, col, row, 2, world);
		gold = true;
	}

}
