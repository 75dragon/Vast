package items;

import java.awt.image.BufferedImage;

import world.World;

public class BombItem extends Item
{
	int countDown;

	public BombItem(double x, double y, int countDown, BufferedImage img, World world)
	{
		super(x, y, 0, img, world);
		this.countDown = countDown * 1000;
	}

	/**
	 * a single tick is 10, so multiply by 100 and countdown
	 */
	public void itemTick()
	{
		countDown = countDown - 10;
		if (countDown < 0)
		{
			world.clearArea(d.getX(), d.getY(), 3.5, 15);
			System.out.println(d.getX() + " " + d.getY() + " bomb BOOM!");
			world.itemDeath(this);
		}
	}
}
