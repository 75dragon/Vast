package weapons;

import java.awt.image.BufferedImage;

import entity.Player;

public class SpinWeapon extends Weapon
{
	/**
	 * Attacks all enemies around the player
	 * 
	 * @param image
	 * @param atkImg
	 * @param had
	 */
	public SpinWeapon(BufferedImage image, BufferedImage atkImg, Player had)
	{
		super(1, 5, .75, image, atkImg, 100, had);
		weaponName = "Spin Sword";
	}

	public void attackAction()
	{
		if (canAttack && wantToAttack)
		{
			theWielder.getWorld().addSprite(attackImg, theWielder.getX(), theWielder.getY(), .1);
			if (theWielder.getWorld().hitEnemiesInArea(theWielder.getX(), theWielder.getY(), 2, damage, weaponName))
			{
				durabilityChange(-12, 100);
			}
			canAttack = false;
			attackCount = 0;
		}
	}
}
