package items;

import java.awt.image.BufferedImage;

import world.World;

public class HealthPotItem extends Item
{
	int amount;

	public HealthPotItem(double x, double y, int heal, BufferedImage img, World world)
	{
		super(x, y, 0, img, world);
		amount = heal;
	}

	public void onItemPickup()
	{
		victim.healHealth(amount);
		world.getDis().getWriter().addText("Health Restored");
	}
}
