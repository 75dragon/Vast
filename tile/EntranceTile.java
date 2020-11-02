package tile;

import java.awt.Color;
import entity.Player;
import world.World;

public class EntranceTile extends Tile implements GameTick
{
	int leaveDelay = 1000;
	int leaveCheckDelay = 50;
	int leaveCheckCount = 0;

	Player exiting;

	public EntranceTile(int x, int y, World world)
	{
		super(true, 0, Color.BLUE, x, y, 0, world);
	}

	@Override
	public void blownUp()
	{
		// cant destroy me hahaha!
	}

	public void playerLeaves(Player left)
	{
		world.getEndText()[world.getPlayers().indexOf(left)] = "Left Safely!";
		world.playerDeath(left);
	}

	@Override
	public void onTick()
	{
		if (leaveDelay > 0)
		{
			leaveDelay--;
			return;
		}
		if (leaveCheckCount < leaveCheckDelay)
		{
			leaveCheckCount++;
		}
		else
		{
			exiting = world.detectPlayer(c, r, .5);
			if (exiting != null)
			{
				playerLeaves(exiting);
			}
			leaveCheckCount = 0;
		}
	}

}
