package entity;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.Timer;

import world.World;


public class Enemy extends Character
{
    int speed = 200;

    Timer move, attackTimer;

    int cooldownCount, maxedOut;

    Random rand = new Random();
    
    BufferedImage attkImg;

    Player canHit;
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
    public Enemy( int x, int y, int velX, int velY, int hitPoints, Color c, BufferedImage atkimg,  World world, String name )
    {
        super( x, y, velX, velY, hitPoints, c, world, 12, name );
        vX = velX;
        vY = velY;
        maxedOut = 50;
        cooldownCount = -200;
        attkImg = atkimg;
    }


    /**
     * Works the AI of the enemy with a timer.
     */    
    public void aiTick()
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
            w.addRuby( x, y );
            w.enemyDeath( this );
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
        w.getDis().getWriter().addText( name + " has hit the player for " + 3 + " damage" );
        w.addSprite(attkImg,  c.getX(), c.getY(),.1 );
    }


    public void removeEnemy()
    {
        //move.stop();
    }
}
