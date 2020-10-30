package testMine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import world.World;

public class Listener implements KeyListener
{
	World world;

	boolean gameStart = false;

	boolean gameRun = false;

	boolean gameFinish = false;

	public Listener()
	{

	}

	public void addWorld(World a)
	{
		world = a;
	}

	@Override
	/**
	 * creates
	 */
	public void keyPressed(KeyEvent e)
	{
		if (gameStart)
		{
			if (KeyEvent.VK_S == e.getKeyCode())
			{
				world.newGame();
			}
			return;
		}
		else if (gameFinish)
		{
			if (KeyEvent.VK_G == e.getKeyCode())
			{
				world.newGame();
			}
			return;
		}
		else
		{
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_LEFT:
					world.getPlayers().get(0).setLeft(true);
					break;
				case KeyEvent.VK_RIGHT:
					world.getPlayers().get(0).setRight(true);
					break;
				case KeyEvent.VK_UP:
					world.getPlayers().get(0).setUp(true);
					break;
				case KeyEvent.VK_DOWN:
					world.getPlayers().get(0).setDown(true);
					break;
				case KeyEvent.VK_SPACE:
					world.getPlayers().get(0).bomb();
					break;
				case KeyEvent.VK_F:
					world.getPlayers().get(0).useWeapon();
					break;
				case KeyEvent.VK_P:
					world.pause();
					break;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (gameFinish)
		{
			return;
		}
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
				world.getPlayers().get(0).setLeft(false);
				break;
			case KeyEvent.VK_RIGHT:
				world.getPlayers().get(0).setRight(false);
				break;
			case KeyEvent.VK_UP:
				world.getPlayers().get(0).setUp(false);
				break;
			case KeyEvent.VK_DOWN:
				world.getPlayers().get(0).setDown(false);
				break;
			case KeyEvent.VK_F:
				world.getPlayers().get(0).stopUsingWeapon();
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void gameStart(boolean x)
	{
		gameStart = x;
	}

	public void gameRun(boolean x)
	{
		gameRun = x;
	}

	public void gameFinish(boolean x)
	{
		gameFinish = x;
	}
}
