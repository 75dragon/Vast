package testMine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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

    int light;// TODO

    Weapon holding;


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
        bombs = 5;
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
     * Draws character.
     * 
     * @param g
     *            graphics
     */
    public void drawMe( Graphics g )
    {
        if ( img == null )
        {
            g.setColor( color );
            g.fillRect( (int)( x * 40 ), (int)( y * 40 ), 40, 40 );
            g.setColor( Color.WHITE );
            g.fillRect( (int)( x * 40 ), (int)( y * 40 ), 40, 5 );
            g.setColor( Color.RED );
            g.fillRect( (int)( x * 40 ), (int)( y * 40 ), (int)( 40 * hp / maxHp ), 5 );
            // g.setColor( Color.MAGENTA );
            // g.drawString( x + ", " + y, (int)( x * 40 ), (int)( y * 40 ) );
        }
        else
        {
            g.setColor( color );
            g.fillRect( (int)( x * 40 ), (int)( y * 40 ), (int)( 40 * wid ), (int)( 40 * hei ) );
            g.drawImage( img, (int)( x * 40 ) + (int)( 10 * wid ), (int)( y * 40 ) + (int)( 10 * hei ), null );
            g.setColor( Color.WHITE );
            g.fillRect( (int)( x * 40 ), (int)( y * 40 ), 40, 5 );
            g.setColor( Color.RED );
            g.fillRect( (int)( x * 40 ), (int)( y * 40 ), (int)( 40 * hp / maxHp ), 5 );
            // g.setColor( Color.MAGENTA );
            // g.drawString( x + ", " + y, (int)( x * 40 ), (int)( y * 40 ) );
        }
        g.setFont( new Font( "Courier", Font.BOLD, 30 ) );
        g.setColor( Color.WHITE );
        g.drawString( "Bombs: " + getBombs(),
            (int)( getX() * 40 ) - 300,
            (int)( getY() * 40 ) + 250 );

        if ( holding != null )
        {
            holding.drawMe( g );
        }
    }

    /**
     * Sets the player's weapon
     * @param x the weapon, oh how kind
     */
    public void setWeapon( Weapon x )
    {
        holding = x;
    }

    /**
     * Get the player's weapon
     * @return Weapon weap-on
     */
    public Weapon getWeapon()
    {
        return holding;
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
    }


    /**
     * Adds bombs to the player.
     * 
     * @param amount
     */
    public void addBombs( int amount )
    {
        bombs = bombs + amount;
    }


    /**
     * gets the amount of bombs
     * 
     * @return int of bombs
     */
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
        if ( holding != null )
        {
            holding.stopAttacking();
        }
    }
}
