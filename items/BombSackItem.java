package items;

import java.awt.image.BufferedImage;

import world.World;

public class BombSackItem extends Item
{
	int amount;

	public BombSackItem(double x, double y, int bombs, BufferedImage img, World world)
	{
		super(x, y, 0, img, world);
		amount = bombs;
	}

	public void onItemPickup()
	{
		victim.addBombs(amount);
		world.getDis().getWriter().addText("Picked up explosives");
	}

}
