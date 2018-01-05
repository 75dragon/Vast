package weapons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import entity.Character;


public class Weapon
{
    String weaponName;

    Timer attackSend;

    int range, damage;

    int cooldownCount, maxedOut;

    double attackSpeed;

    BufferedImage img, attackImg;

    double dura;

    Character theWielder;

    boolean canAttack, wantToAttack;


    public Weapon(
        int rng,
        int dmg,
        double atkspd,
        BufferedImage image,
        BufferedImage atkImg,
        double durability,
        Character gottem )
    {
        range = rng;
        damage = dmg;
        attackSpeed = atkspd;
        img = image;
        attackImg = atkImg;
        dura = durability;
        theWielder = gottem;
        canAttack = true;
        wantToAttack = false;
        cooldownCount = 100;
        maxedOut = 100;
        attack();
    }


    public void attack()
    {
        attackSend = new Timer( (int)( attackSpeed * 1000 / maxedOut ), new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                if ( cooldownCount < maxedOut )
                {
                    cooldownCount++;
                }
                else
                {
                    canAttack = true;
                    attackSend.stop();
                }
            }
        } );
    }


    public void attackAction()
    {
    }


    public void duabilityChange( double amount, int percent )
    {
        dura = ( dura + amount ) * percent / 100;
        if ( dura <= 0 )
        {
            wantToAttack = false;
            weaponBreak();
        }
    }


    public void weaponBreak()
    {
        attackSend.stop();
        theWielder.setHolding( null );
        theWielder.getWorld().getDis().getWriter().addText( weaponName + " has broken!" );
    }


    public void startAttacking()
    {
        wantToAttack = true;
        attackSend.start();
        attackAction();
    }


    public void stopAttacking()
    {
        wantToAttack = false;
    }


    public void drawMe( Graphics g )
    {
        g.setFont( new Font( "Courier", Font.BOLD, 20 ) );
        g.setColor( Color.WHITE );
        g.drawString( "Weapon: " + weaponName + " " + dura + "%",
            (int)( theWielder.getX() * 40 ) ,
            (int)( theWielder.getY() * 40 ) - 350 );
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setColor( Color.YELLOW );
        Arc2D arc = new Arc2D.Double( theWielder.getX() * 40,
            theWielder.getY() * 40 - 320,
            30,
            30,
            0,
            3.6 * cooldownCount,
            Arc2D.PIE );
        g2d.fill( arc );
    }
}
