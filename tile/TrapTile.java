package tile;

import java.awt.Color;
import entity.Player;
import world.World;

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
public class TrapTile extends Tile implements GameTick
{
	Player victem;

	int defaultDelay;

	int delayCount;

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
	public TrapTile(boolean passable, int loot, Color color, int c, int r, World world)
	{
		super(passable, loot, color, c, r, 0, world);
		defaultDelay = 25;
		delayCount = 0;
	}

	public void sneakAttack()
	{
		if ((victem = world.detectPlayer(c, r, 1)) != null)
		{
			world.bombArea(c, r, 2000);
			world.theWorld[c][r] = new RegularTile(false, 0, Color.PINK, c, r, world);
			world.gameTickDeath(this);
		}
	}

	@Override
	public void onTick()
	{
		delayCount += 1;
		if (delayCount >= defaultDelay)
		{
			sneakAttack();
			delayCount = 0;
		}
	}
}
