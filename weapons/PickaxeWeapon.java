package weapons;

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
        super( 1, 4, .3, image, atkImg, 100, had );
        weaponName = "Pickaxe";
    }


    public void attackAction()
    {
    	System.out.println("attackaction!");
        if ( theWielder.getWorld().hitEnemiesInArea( theWielder.getX() + theWielder.getVX(),
            theWielder.getY() + theWielder.getVY(),
            .75,
            damage , weaponName) )
        {
            durabilityChange( -5, 100 );
        }
        if ( canAttack && wantToAttack )
        {
            centerVY = theWielder.getY() + theWielder.getVY() + theWielder.getHei() / 2;
            centerVX = theWielder.getX() + theWielder.getVX() + theWielder.getWid() / 2;
            if ( theWielder.getVX() == 0 && theWielder.getVY() == 0 )
            {
                System.out.println( "move!" );
            }
            else if ( centerVY < 0 || centerVY > theWielder.getWorld().getyDim() || centerVX < 0
                || centerVX > theWielder.getWorld().getxDim() )
            {
                System.out.println( "cant mine the abyss..." );
            }
            else
            {
                if ( theWielder.getWorld().theWorld[(int)centerVY][(int)centerVX].getTile().mineTile() )
                {
                    durabilityChange( -3, 100 );
                }

                theWielder.getWorld().addSprite( attackImg,
                    theWielder.getX() + theWielder.getVX(),
                    theWielder.getY() + theWielder.getVY(),
                    .1 );
            }

            canAttack = false;
            cooldownCount = 0;
            attackSend.start();
        }
    }
}
