package testMine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Weapon
{
    String weaponName;
    
    Timer attackSend;

    int range;

    int damage;

    double attackSpeed;

    Image img;

    Image attackImg;

    double dura;

    Player theWeilder;


    public Weapon( int rng, int dmg, double atkspd, Image image, Image atkImg, double durability, Player gottem )
    {
        range = rng;
        damage = dmg;
        attackSpeed = atkspd;
        img = image;
        attackImg = atkImg;
        dura = durability;
        theWeilder = gottem;
        attack();
    }


    public void attack()
    {
        attackSend = new Timer( (int)( attackSpeed * 1000 ), new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                System.out.println( "HAHAHAHAHAHA" );
            }
        } );
    }


    public void duablityChange( double amount, int percent )
    {
        dura = ( dura + amount ) * percent;
        if ( dura < 0 )
        {
            weaponBreak();
        }
    }


    public void weaponBreak()
    {
        // TODO
    }


    public void startAttacking()
    {
        attackSend.start();
    }


    public void stopAttacking()
    {
        attackSend.stop();
    }

    public void drawMe(Graphics g)
    {
        g.setFont( new Font( "Courier", Font.BOLD, 30 ) );
        g.setColor( Color.WHITE );
        g.drawString( "Weapon: " + weaponName,
            (int)( theWeilder.getX() * 40 ) - 300,
            (int)( theWeilder.getY() * 40 ) + 300 );
    }
}
