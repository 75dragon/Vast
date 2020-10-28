package items;

import java.awt.image.BufferedImage;

import world.World;

public class RubyItem extends Item
{
	int amount;

	public RubyItem(double x, double y, BufferedImage img, World world)
	{
		super(x, y, 0, img, world);
		amount = 750;
	}

	public void onItemPickup()
	{
		victim.addGold(amount);
		world.getDis().getWriter().addText("Aquired a Ruby");
	}
}
