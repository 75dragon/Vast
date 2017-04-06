package testMine;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


/**
 * Player class.
 *
 * @author Bryan Wu, Austin Cheng
 * @version May 22, 2016
 * @author Period: 3
 * @author Assignment: Java Final
 *
 * @author Sources: Me n u
 */
public class Player extends Character
{
    private int gold;

    private int bombs;
    
    Timer moveMe;

    int light;//TODO
    /**
     * @param x
     *            x
     * @param y
     *            y
     * @param vX
     *            vX
     * @param vY
     *            vY
     * @param hitPoints
     *            hp
     * @param color
     *            default color
     * @param world
     *            ...
     * @param speed
     *            how fast
     */
    public Player( int x, int y, int vX, int vY, int hitPoints, Color color, World world, double speed )
    {
        super( x, y, vX, vY, hitPoints, color, world, speed );
        gold = 0;
        bombs = 100;
        light = 1000;
        startMoving();
    }


    /**
     * @param damage
     *            damage value
     * 
     */
    public void takeDamage( double damage )
    {
        if ( isAlive() )
        {
            setHP( getHP() - damage );
            System.out.println( getHP() );
        }
        if ( !isAlive() )
        {
            getWorld().playerDeath( this );
        }
    }

    /**
     * @param damage
     *            damage value
     * 
     */
    public void healHealth( int heal )
    {
        if ( isAlive() )
        {
            setHP( getHP() + heal );
            System.out.println( getHP() );
        }
    }

    /**
     * Deploys bomb
     */
    public void bomb()
    {
        if ( bombs > 0 )
        {
            bombs--;
            getWorld().bombArea( getX(), getY(), 3000 );
        }
    }


    /**
     * Mines tile in front of player.
     */
    public void mine()
    {
        if ( getVX() == 0 && getVY() == 0 ) // player starts out with no
                                            // direction!
        {
            System.out.println( "move!" );
        }
        else if ( getY() + getVY() + hei / 2 < 0 || getY() + getVY() + hei / 2 > getWorld().yDim
            || getX() + getVX() + wid / 2 < 0 || getX() + getVX() + wid / 2 > getWorld().xDim )
        {
            System.out.println( "cant mine the abyss..." );
        }
        else
        {
            getWorld().theWorld[(int)( getY() + getVY() + hei / 2 )][(int)( getX() + getVX() + wid / 2 )].getTile()
                .mineTile();
        }
    }

    public void startMoving()
    {
        moveMe = new Timer( 10, new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                updatePos();
            }
        } );
        moveMe.start();
    }
    /**
     * Returns player's gold amount.
     * 
     * @return gold value
     */
    public int getGold()
    {
        return gold;
    }


    /**
     * Adds gold value to player's current amount.
     * 
     * @param g
     *            gold value
     */
    public void addGold( int g )
    {
        gold += g;
        System.out.println( "I have this much gold: " + gold );
    }


    public void addBombs( int amount )
    {
        bombs = bombs + amount;
    }


    public int getBombs()
    {
        return bombs;
    }


    public double getWidth()
    {
        return wid;
    }


    public double getHeight()
    {
        return hei;
    }
    
    public void removePlayer()
    {
       moveMe.stop();
    }
}
