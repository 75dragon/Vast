package items;

import java.awt.image.BufferedImage;

import world.World;

public class GoldBarItem extends Item
{
	int amount;

	public GoldBarItem(double x, double y, BufferedImage img, World world)
	{
		super(x, y, 0, img, world);
		amount = 1000;
	}

	public void onItemPickup()
	{
		victim.addGold(amount);
		world.getDis().getWriter().addText("Aquired a Gold Bar");
	}
}
