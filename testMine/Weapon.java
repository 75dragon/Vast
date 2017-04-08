package testMine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Weapon
{
    Timer attackSend;
    int range;
    int damage;
    int attackSpeed;
    Image img;
    Image attackImg;
    double dura;
    
    public Weapon(int rng, int dmg, int atkspd, Image image, Image atkImg, double durability)
    {
        range = rng;
        damage = dmg;
        attackSpeed = atkspd;
        img = image;
        attackImg = atkImg;
        dura = durability;
        attack();
    }
    
    public void attack()
    {
        attackSend = new Timer( attackSpeed * 1000, new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                System.out.println( "HAHAHAHAHAHA" );
            }
        } );
    }
    
    public void duablityChange( double amount, int percent)
    {
        dura = (dura + amount) * percent;
        if ( dura < 0 )
        {
            weaponBreak();
        }
    }
    
    public void weaponBreak()
    {
        //TODO
    }
    
    public void startAttacking()
    {
        attackSend.start();
    }
    
    public void stopAttacking()
    {
        attackSend.stop();
    }

    
}
