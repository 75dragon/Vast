package testMine;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class PickaxeWeapon extends Weapon
{

    public PickaxeWeapon( Image image, Image atkImg, Player had )
    {
        super( 1, 1, .3 , image, atkImg, 100, had );
        weaponName = "Pickaxe";
    }

    /**
     * This weapon cannot attack, however it can be used to clear solid walls.
     * basically, it takes the direction you are going in, and checks if its in bounds. then, it hits that tile for 1.
     */
    public void attack()
    {
        attackSend = new Timer( (int)( attackSpeed * 1000 ), new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                if ( theWeilder.getVX() == 0 && theWeilder.getVY() == 0 )
                {
                    System.out.println( "move!" );
                }
                else if ( theWeilder.getY() + theWeilder.getVY() + theWeilder.hei / 2 < 0
                    || theWeilder.getY() + theWeilder.getVY() + theWeilder.hei / 2 > theWeilder.getWorld().yDim
                    || theWeilder.getX() + theWeilder.getVX() + theWeilder.wid / 2 < 0
                    || theWeilder.getX() + theWeilder.getVX() + theWeilder.wid / 2 > theWeilder.getWorld().xDim )
                {
                    System.out.println( "cant mine the abyss..." );
                }
                else
                {
                    theWeilder.getWorld().theWorld[(int)( theWeilder.getY() + theWeilder.getVY()
                        + theWeilder.hei / 2 )][(int)( theWeilder.getX() + theWeilder.getVX() + theWeilder.wid / 2 )]
                            .getTile().mineTile();
                    theWeilder.getWorld().addAttackSprite( theWeilder.getX() + theWeilder.getVX(),
                        theWeilder.getY() + theWeilder.getVY(),
                        .1 );
                }
            }
        } );
    }
}
