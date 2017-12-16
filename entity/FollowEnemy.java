package entity;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.Timer;

import world.World;


public class FollowEnemy extends Enemy
{
    /**
     * Makes a follow enemy. low speed, stalks you.
     * 
     * @param x
     * @param y
     * @param velX
     * @param velY
     * @param hitPoints
     * @param c
     * @param world
     */
    public FollowEnemy( int x, int y, int velX, int velY, int hitPoints, Color c, BufferedImage atkimg, World world )
    {
        super( x, y, velX, velY, hitPoints, c, atkimg, world );
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
        vX = 0;
        vY = 0;
        int sleepNumber = getTilesPriority( x, y );
        if ( getTilesPriority( x + 1, y + 1 ) < sleepNumber )
        {
            vX = 1;
            vY = 1;
            sleepNumber = getTilesPriority( x + 1, y + 1 );
        }
        if ( getTilesPriority( x + 1, y - 1 ) < sleepNumber )
        {
            vX = 1;
            vY = -1;
            sleepNumber = getTilesPriority( x + 1, y - 1 );
        }
        if ( getTilesPriority( x - 1, y + 1 ) < sleepNumber )
        {
            vX = -1;
            vY = 1;
            sleepNumber = getTilesPriority( x - 1, y + 1 );
        }
        if ( getTilesPriority( x - 1, y - 1 ) < sleepNumber )
        {
            vX = -1;
            vY = -1;
            sleepNumber = getTilesPriority( x - 1, y - 1 );
        }
        
        
        if ( getTilesPriority( x + 1, y ) < sleepNumber )
        {
            vX = 1;
            vY = 0;
            sleepNumber = getTilesPriority( x + 1, y + 1 );
        }
        if ( getTilesPriority( x, y - 1 ) < sleepNumber )
        {
            vX = 0;
            vY = -1;
            sleepNumber = getTilesPriority( x + 1, y - 1 );
        }
        if ( getTilesPriority( x - 1, y) < sleepNumber )
        {
            vX = -1;
            vY = 0;
            sleepNumber = getTilesPriority( x - 1, y + 1 );
        }
        if ( getTilesPriority(x, y + 1 ) < sleepNumber )
        {
            vX = 0;
            vY = 1;
            sleepNumber = getTilesPriority( x - 1, y - 1 );
        }
        updatePos();
    }


    public int getTilesPriority( double x, double y )
    {
        if ( x < 0 || x > getWorld().getxDim() - 1 || y < 0 || y > getWorld().getyDim() - 1 )
        {
            return getWorld().getxDim() * getWorld().getyDim();
        }
        return getWorld().getTile( x, y ).getPlayerProximity();
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
     * Enemy attacks player
     * 
     * @param c
     *            character
     */
    public void enemyAttack( Player c )
    {
        c.takeDamage( 2, "A monster" );
        w.addSprite( attkImg, c.getX(), c.getY(), .1 );
    }
}
