package tile;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entity.Player;
import world.World;

public class EntranceTile extends Tile
{
	Timer cannotLeaveYet;

	Timer checkForPlayer;

	Player exiting;

	public EntranceTile(int x, int y, World world)
	{
		super(true, 0, Color.BLUE, x, y, 0, world);
		cantExit();
	}

	@Override
	public void blownUp()
	{
		// cant destroy me hahaha!
	}

	public void cantExit()
	{
		EntranceTile hold = this;
		cannotLeaveYet = new Timer(10000, new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				cannotLeaveYet.stop();
				hold.checkExit();
			}

		});
		cannotLeaveYet.start();
	}

	public void checkExit()
	{
		EntranceTile hold = this;
		checkForPlayer = new Timer(500, new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				hold.exiting = world.detectPlayer(hold.c, hold.r, .5);
				if (hold.exiting != null)
				{
					hold.playerLeaves(hold.exiting);
				}
			}

		});
		checkForPlayer.start();
	}

	public void playerLeaves(Player left)
	{
		world.getEndText()[world.getPlayers().indexOf(left)] = "Left Safely!";
		world.playerDeath(left);
	}

	public void removeTile()
	{
		cannotLeaveYet.stop();
		if (checkForPlayer != null)
		{
			checkForPlayer.stop();
		}
	}

}
