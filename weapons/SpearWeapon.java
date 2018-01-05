package weapons;

import java.awt.image.BufferedImage;

import entity.Player;


public class SpearWeapon extends Weapon
{
    /**
     * A heavy, brittle weapon that once going, will not stop.
     * 
     * @param image
     * @param atkImg
     * @param had
     */
    public SpearWeapon( BufferedImage image, BufferedImage atkImg, Player had )
    {
        super( 2, 7, 1, image, atkImg, 100, had );
        weaponName = "Spear";
    }


    public void attackAction()
    {
        if ( canAttack && wantToAttack )
        {
            theWielder.getWorld().addSprite( attackImg,
                theWielder.getX() + theWielder.getVX(),
                theWielder.getY() + theWielder.getVY(),
                .1 );
            if ( theWielder.getWorld().hitEnemiesInArea( theWielder.getX(), theWielder.getY(), 2, damage, weaponName ) )
            {
                duabilityChange( -5, 100 );
            }
            canAttack = false;
            cooldownCount = 0;
            attackSend.start();
        }
    }
}
