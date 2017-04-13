package testMine;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.Timer;


public class Enemy extends Character
{
    int speed = 200;

    Timer move, attackTimer;

    int cooldownCount, maxedOut;

    Random rand = new Random();
    
    BufferedImage attkImg;

    /**
     * Makes a default enemy. High speed, keeps going in a direction till It
     * stops, then repeats
     * 
     * @param x
     * @param y
     * @param velX
     * @param velY
     * @param hitPoints
     * @param c
     * @param world
     */
    public Enemy( int x, int y, int velX, int velY, int hitPoints, Color c, BufferedImage atkimg,  World world )
    {
        super( x, y, velX, velY, hitPoints, c, world, 12 );
        vX = velX;
        vY = velY;
        ai();
        maxedOut = 50;
        cooldownCount = -200;
        attkImg = atkimg;
    }


    /**
     * Works the AI of the enemy with a timer.
     */
    public void ai()
    {
        int delay = 20;
        move = new Timer( delay, new ActionListener()
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
                    attemptToAttack();
                }
                move();
            }

        } );
        move.start();
    }


    /**
     * Move in a straight line until you run into a wall, then ramdomize
     * direction and repeat
     */
    public void move()
    {
        if ( !updatePos() )
        {
            while ( !updatePos() )
            {
                vX = rand.nextInt( 3 ) - 1;
                vY = rand.nextInt( 3 ) - 1;
            }
        }
    }


    /**
     * Attack the player if he is nearby
     */
    public void attemptToAttack()
    {
        Player canHit;
        if ( ( canHit = getWorld().detectPlayer( getX(), getY(), 1 ) ) != null )
        {
            enemyAttack( canHit );
            cooldownCount = 0;
        }
    }


    /**
     * Takes damage
     * 
     * @param damage
     *            damage
     */
    public void takeDamage( double damage, String whoHitMe )
    {
        setHP( getHP() - damage );
        System.out.println( hp );
        if ( !isAlive() )
        {
            getWorld().addRuby( x, y );
            getWorld().enemyDeath( this );
        }
    }


    /**
     * Checks if alive
     * 
     * @return
     */
    public boolean isAlive()
    {
        return hp > 0;
    }


    /**
     * Enemy attacks player
     * 
     * @param c
     *            character
     */
    public void enemyAttack( Player c )
    {
        c.takeDamage( 3, "A monster" );
        w.addSprite(attkImg,  c.getX(), c.getY(),.1 );
    }


    public void removeEnemy()
    {
        move.stop();
    }
}
