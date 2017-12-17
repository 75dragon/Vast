package weapons;

import java.awt.image.BufferedImage;

import entity.Player;


public class SpinWeapon extends Weapon
{
    /**
     * A heavy, brittle weapon that once going, will not stop.
     * 
     * @param image
     * @param atkImg
     * @param had
     */
    public SpinWeapon( BufferedImage image, BufferedImage atkImg, Player had )
    {
        super( 1, 1, 4, image, atkImg, 100, had );
        weaponName = "Spin Sword";
    }


    public void attackAction()
    {
        if ( canAttack && wantToAttack )
        {
            theWeilder.getWorld().addSprite( attackImg, theWeilder.getX(), theWeilder.getY(), .1 );
            if ( theWeilder.getWorld().hitEnemiesInArea( theWeilder.getX(), theWeilder.getY(), 2, 3, weaponName ) )
            {
                duabilityChange( -12, 100 );
            }
            canAttack = false;
            cooldownCount = 0;
            attackSend.start();
        }
    }
}
