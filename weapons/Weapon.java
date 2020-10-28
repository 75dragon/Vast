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


    /**
     * Timer to manage attack. Makes a timer with attackSpeed * 1000 / maxedOut
     * When timer completes, stops and lets the player attack turn on the timer
     * after an attack to start the cool down
     */
    public void attack()
    {
    	System.out.println("attack!");
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


    /**
     * what happens on a attack
     */
    public void attackAction()
    {
    }


    /**
     * reduce the durability of the weapon after you hit something durability =
     * ( durability + amount ) * percent / 100
     * 
     * @param amount
     *            if you want a flat amount, use this value (negative usually)
     *            and set percent to 100
     */
    public void durabilityChange( double amount, int percent )
    {
        dura = ( dura + amount ) * percent / 100;
        if ( dura <= 0 )
        {
            wantToAttack = false;
            weaponBreak();
        }
    }


    /**
     * Break the current weapon and remove it.
     */
    public void weaponBreak()
    {
        attackSend.stop();
        theWielder.setHolding( null );
        theWielder.getWorld().getDis().getWriter().addText( weaponName + " has broken!" );
    }


    /**
     * 
     * TODO Write your method description here.
     */
    public void startAttacking()
    {
        wantToAttack = true;
        attackSend.start();
        attackAction();
    }


    /**
     * stop attacking
     */
    public void stopAttacking()
    {
        wantToAttack = false;
    }


    public void drawMe( Graphics g )
    {
        g.setFont( new Font( "Courier", Font.BOLD, 20 ) );
        g.setColor( Color.WHITE );
        g.drawString( "Weapon: " + weaponName + " " + dura + "%",
            (int)( theWielder.getX() * 40 ),
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
