package items;

import java.awt.image.BufferedImage;

import world.World;

public class WeaponPileItem extends Item
{
	int amount;

	public WeaponPileItem(double x, double y, BufferedImage img, World world)
	{
		super(x, y, 0, img, world);
	}

	public void onItemPickup()
	{
		world.giveRandomItem(victim);
		world.getDis().getWriter().addText("Aquired a new weapon");
	}
}
