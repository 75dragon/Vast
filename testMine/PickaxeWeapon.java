package testMine;

import java.awt.image.BufferedImage;

import entity.Player;


public class PickaxeWeapon extends Weapon
{
    double centerVY;

    double centerVX;


    /**
     * This weapon cannot attack, however it can be used to clear solid walls.
     * basically, it takes the direction you are going in, and checks if its in
     * bounds. then, it hits that tile for 1.
     */
    public PickaxeWeapon( BufferedImage image, BufferedImage atkImg, Player had )
    {
        super( 1, 1, .3, image, atkImg, 100, had );
        weaponName = "Pickaxe";
    }


    public void attackAction()
    {
        if ( theWeilder.getWorld().hitEnemiesInArea( theWeilder.getX() + theWeilder.getVX(),
            theWeilder.getY() + theWeilder.getVY(),
            .75,
            3 , weaponName) )
        {
            duabilityChange( -5, 100 );
        }
        if ( canAttack && wantToAttack )
        {
            centerVY = theWeilder.getY() + theWeilder.getVY() + theWeilder.getHei() / 2;
            centerVX = theWeilder.getX() + theWeilder.getVX() + theWeilder.getWid() / 2;
            if ( theWeilder.getVX() == 0 && theWeilder.getVY() == 0 )
            {
                System.out.println( "move!" );
            }
            else if ( centerVY < 0 || centerVY > theWeilder.getWorld().getyDim() || centerVX < 0
                || centerVX > theWeilder.getWorld().getxDim() )
            {
                System.out.println( "cant mine the abyss..." );
            }
            else
            {
                if ( theWeilder.getWorld().theWorld[(int)centerVY][(int)centerVX].getTile().mineTile() )
                {
                    duabilityChange( -3, 100 );
                }

                theWeilder.getWorld().addSprite( attackImg,
                    theWeilder.getX() + theWeilder.getVX(),
                    theWeilder.getY() + theWeilder.getVY(),
                    .1 );
            }

            canAttack = false;
            cooldownCount = 0;
            attackSend.start();
        }
    }
}
