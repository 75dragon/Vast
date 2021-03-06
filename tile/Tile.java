package tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.World;

/**
 * Tile class.
 *
 * @author Bryan Wu
 * @version May 22, 2016
 * @author Period: 3
 * @author Assignment: TODO
 *
 * @author Sources: TODO
 */
public class Tile
{
	boolean pass;// tells if the player is able to walk through

	boolean destroyed = false;// if the tile is already destroyed

	int loot;// does it drop loot?

	int health;

	int c;

	int r;

	BufferedImage image;

	Color color;// color of the tile.

	World world;

	double wid = 1;

	double hei = 1;

	int playerProximity;

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
	public Tile(boolean passable, int loot, Color color, int c, int r, int hp, World world)
	{
		pass = passable;
		this.loot = loot;
		this.color = color;
		this.c = c;
		this.r = r;
		this.world = world;
		health = hp;
		playerProximity = -1;
	}

	/**
	 * slowly mines the tile
	 */
	public boolean mineTile()
	{
		if (health < 1 || pass)
		{
			return false;
		}
		else
		{
			health--;
			if (health == 0)
			{
				tileMined();
			}
			return true;
		}
	}

	/**
	 * Happens when the tile is mined
	 */
	public void tileMined()
	{
		if (destroyed == false)
		{
			pass = true;
			color = World.getBrown();
			world.getPlayers().get(0).addGold(loot);
			loot = 0;
			image = null;
			destroyed = true;
			health = 0;
		}
	}

	public void blownUp()
	{
		if (destroyed == false)
		{
			pass = true;
			color = World.getBrown();
			loot = 0;
			image = null;
			destroyed = true;
			health = 0;
		}
	}

	/**
	 * Draws tile.
	 * 
	 * @param g
	 *            graphics
	 */
	public void drawSelf(Graphics g)
	{
		if (image == null)
		{
			g.setColor(color);
			g.fillRect(c * 40, r * 40, 40, 40);
			// g.setColor(Color.MAGENTA);
			// g.drawString("" + playerProximity, c * 40, r * 40 + 10 );
		}
		else
		{
			g.setColor(color);
			g.fillRect(c * 40, r * 40, 40, 40);
			g.drawImage(image, c * 40 + 10, r * 40 + 10, null);
			// g.setColor(Color.MAGENTA);
			// g.drawString( c + ", " + r + " " + playerProximity, c * 40, r *
			// 40 );
		}
	}

	/**
	 * Checks if you currently are colliding with a tile
	 * 
	 * @param x
	 *            the x value, top left
	 * @param y
	 *            the y value, top left
	 * @param xWidth
	 *            width
	 * @param yHeight
	 *            height
	 * @return true, if one of the corners are overlapping. else false.
	 */
	public boolean rColl(double x, double y, double xWidth, double yHeight)
	{
		if (!pass)
		{
			if (y > r && y < r + hei && x > c && x < c + hei)
			{
				return true;
			}

			if (y + yHeight > r && y + yHeight < r + hei && x > c && x < c + hei)
			{
				return true;
			}

			if (y > r && y < r + hei && x + xWidth > c && x + xWidth < c + hei)
			{
				return true;
			}

			if (y + yHeight > r && y + yHeight < r + hei && x + xWidth > c && x + xWidth < c + hei)
			{
				return true;
			}
		}
		return false;
	}

	public int getPlayerProximity()
	{
		return playerProximity;
	}

	public void setPlayerProximity(int closeness)
	{
		playerProximity = closeness;
	}

	/**
	 * Returns this tile.s
	 * 
	 * @return tile
	 */
	public Tile getTile()
	{
		return this;
	}

	/**
	 * Returns true if tile is passable--false otherwise.
	 * 
	 * @return if passables
	 */
	public boolean isPassable()
	{
		return pass;
	}

	/**
	 * Returns color of tile.
	 * 
	 * @return color
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Sets image of tile.
	 * 
	 * @param i
	 *            image
	 */
	public void setImage(BufferedImage i)
	{
		image = i;
	}

	/**
	 * for stopping timers tiles...
	 */
	public void removeTile()
	{
		return;
	}
}
