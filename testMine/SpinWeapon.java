package testMine;

import java.awt.Image;


public class SpinWeapon extends Weapon
{
    /**
     * A heavy, brittle weapon that once going, will not stop.
     * 
     * @param image
     * @param atkImg
     * @param had
     */
    public SpinWeapon( Image image, Image atkImg, Player had )
    {
        super( 1, 1, 3, image, atkImg, 100, had );
        weaponName = "Spin Sword";
    }


    public void attackAction()
    {
        if ( canAttack && wantToAttack )
        {
            theWeilder.getWorld().addAttackSprite( attackImg, theWeilder.getX(), theWeilder.getY(), .1 );
            if ( theWeilder.getWorld().spinSwordClearArea( theWeilder.getX(), theWeilder.getY(), 2, 3 ) )
            {
                duabilityChange( -10, 100 );
            }
            canAttack = false;
            attackSend.start();
        }
    }
}