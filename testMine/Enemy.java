package testMine;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;


public class Enemy extends Character
{
    int speed = 200;

    Timer move;

    Timer attackTimer;

    Random rand = new Random();


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
    public Enemy( int x, int y, int velX, int velY, int hitPoints, Color c, World world )
    {
        super( x, y, velX, velY, hitPoints, c, world, 12 );
        vX = velX;
        vY = velY;
        ai();
        attackPattern( 1000 );

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
                move();
            }

        } );
        move.start();
    }


    /**
     * Works the Attack of the enemy with a timer.
     */
    public void attackPattern( int delay )
    {
        attackTimer = new Timer( delay, new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                attemptToAttack();
            }
        } );
        attackTimer.start();
    }

    int aiX = rand.nextInt( 3 ) - 1;

    int aiY = rand.nextInt( 3 ) - 1;


    /**
     * Move in a straight line until you run into a wall, then ramdomize
     * direction and repeat
     */
    public void move()
    {
        if ( !updatePos( aiX, aiY ) )
        {
            while ( !updatePos( ( aiX = rand.nextInt( 3 ) - 1 ), ( aiY = rand.nextInt( 3 ) - 1 ) ) )
            {
            }
        }
    }


    /**
     * Attack the player if he is nearby
     */
    public void attemptToAttack()
    {
        Player canHit;
        if ( ( canHit = getWorld().detectPlayer( getX(), getY(), 1.5 ) ) != null )
        {
            enemyAttack( canHit );
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
        w.addAttackSprite( c.getX(), c.getY(), .1 );
    }


    public void removeEnemy()
    {
        move.stop();
        attackTimer.stop();
    }
}
