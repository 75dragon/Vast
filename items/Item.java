package items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import entity.Player;
import world.World;

public class Item
{
	Point2D.Double d;

	int gold;

	BufferedImage img;

	double hei;

	double wid;

	World world;

	boolean test = false;

	int defaultDelay = 100;

	int delayCount = 0;

	Player victim;

	/**
	 * Use this class if you want to draw a PermantSprite. or else, just use the
	 * other classes.
	 * 
	 * @param x
	 * @param y
	 * @param gold
	 * @param img
	 */
	public Item(double x, double y, int gold, BufferedImage img, World world)
	{
		d = new Point2D.Double(x, y);
		this.world = world;
		this.gold = gold;
		this.img = img;
		hei = 1;
		wid = 1;
	}

	public void drawMe(Graphics g)
	{
		if (img == null)
		{
			g.setColor(Color.ORANGE);
			g.fillRect((int) (d.getX() * 40), (int) (d.getY() * 40), 40, 40);
		}
		else
		{
			g.drawImage(img, (int) (d.getX() * 40 - (img.getWidth() - 40) * .5),
					(int) (d.getY() * 40 - (img.getHeight() - 40) * .5), null);
		}
		if (test)
		{
			g.setColor(Color.MAGENTA);
			g.drawString(d.getX() + ", " + d.getY(), (int) (d.getX() * 40), (int) (d.getY() * 40));
		}
	}

	public Point2D.Double getPoint()
	{
		return d;
	}

	/**
	 * Return the distance between something's center and the player's center
	 * 
	 * @param player
	 *            player chosen
	 * @param gx
	 *            x
	 * @param gy
	 *            y
	 * @param wid
	 *            wid
	 * @param hei
	 *            hei
	 * @return the distance double
	 */
	public double distanceFromPlayer(Player player, int gx, int gy, int wid, int hei)
	{
		return Math.sqrt(((player.getX() + player.getWid() / 2) - (gx + wid / 2))
				* ((player.getX() + player.getWid() / 2) - (gx + wid / 2))
				+ ((player.getY() + player.getHei() / 2) - (gy + hei / 2))
						* ((player.getY() + player.getHei() / 2) - (gy + hei / 2)));
	}

	public void itemTick()
	{
		delayCount += 1;
		if (delayCount >= defaultDelay)
		{
			checkArea();
			delayCount = 0;
		}
	}

	public void checkArea()
	{
		if ((victim = world.detectPlayer(d.getX(), d.getY(), 1)) != null)
		{
			onItemPickup();
			world.itemDeath(this);
			return;
		}
	}

	/**
	 * On item pickup
	 */
	public void onItemPickup()
	{
		return;
	}

	public void onItemDestroy()
	{
		return;
	}
}
