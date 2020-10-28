package items;

import java.awt.image.BufferedImage;

import world.World;

public class TreasureChestItem extends Item
{
	public TreasureChestItem(double x, double y, int gold, BufferedImage img, World world)
	{
		super(x, y, gold, img, world);
	}

	public void onItemPickup()
	{
		victim.addGold(2500);
		world.getDis().getWriter().addText("Aquired a Treasure Chest");
	}
}
