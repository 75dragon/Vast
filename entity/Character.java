 package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import weapons.Weapon;
import world.World;


/**
 * Character class that holds parameters such as position (in rows and columns),
 * velocities, health points and color. A lot more now.
 *
 * @author Bryan Wu, Austin Cheng
 * @version May 22, 2016
 * @author Period: 3
 * @author Assignment: zMine
 *
 * @author Sources: None
 */
public abstract class Character
{
    Point2D.Double location;

    boolean left, right, up, down;

    double wid, hei;

    double x, y, vX, vY;

    double speed;

    double maxHp, hp;

    public World w;

    boolean alive;

    Color color;

    BufferedImage img; // image of the char

    private Weapon holding;

    boolean movedx, movedy;
    
    String name;


    /**
     * @param x
     *            x position
     * @param y
     *            y position
     * @param velX
     *            velocity x
     * @param velY
     *            velocity y
     * @param hitPoints
     *            hitPoints
     * @param world
     *            World
     */
    public Character( int x, int y, int velX, int velY, int hitPoints, Color color, World world, double speed, String givenName )
    {
        name = givenName;
        this.setX( x );
        this.setY( y );
        setVX( velX );
        setVY( velY );
        hp = hitPoints;
        maxHp = hitPoints;
        setWorld( world );
        this.color = color;
        alive = true;
        this.speed = speed;
        wid = .8;
        hei = .8;
        up = right = left = down = false;
    }


    abstract void takeDamage( double damage, String whatHitMe );

    public boolean updatePos()
    {
        movedx = false;
        movedy = false;
        if ( alive )
        {
            y = y + getVY() * speed / 200;
            movedy = true;
            if ( -1 < y + getVY() && y + hei < getWorld().getyDim() )
            {
                if ( getWorld().getTile( x, y ).rColl( x, y, wid, hei )
                    || getWorld().getTile( x + wid, y ).rColl( x, y, wid, hei )
                    || getWorld().getTile( x, y + hei ).rColl( x, y, wid, hei )
                    || getWorld().getTile( x + wid, y + hei ).rColl( x, y, wid, hei ) )
                {
                    y = y - getVY() * speed / 200;
                    movedy = false;
                }
                if ( getVY() == 0 )
                {
                    movedy = false;
                }
            }
            else
            {
                y = y - getVY() * speed / 200;
                movedy = false;
            }

            x = x + getVX() * speed / 200;
            movedx = true;
            if ( -1 < x + getVX() && x + wid < getWorld().getxDim() )
            {
                if ( getWorld().getTile( x, y ).rColl( x, y, wid, hei )
                    || getWorld().getTile( x + wid, y ).rColl( x, y, wid, hei )
                    || getWorld().getTile( x, y + hei ).rColl( x, y, wid, hei )
                    || getWorld().getTile( x + wid, y + hei ).rColl( x, y, wid, hei ) )
                {
                    x = x - getVX() * speed / 200;
                    movedx = false;
                }
                if ( getVX() == 0 )
                {
                    movedx = false;
                }
            }
            else
            {
                x = x - getVX() * speed / 200;
                movedx = false;
            }
        }
        return ( movedx || movedy );
    }


    public void setVelocitys()
    {
        vX = 0;
        vY = 0;
        if ( up )
        {
            vY--;
        }
        if ( down )
        {
            vY++;
        }
        if ( right )
        {
            vX++;
        }
        if ( left )
        {
            vX--;
        }
    }


    /**
     * Give this method a point, and if it happens to be inside them then yeah
     * 
     * @param givenX
     * @param givenY
     * @return if its inside
     */
    public boolean insideMe( double givenX, double givenY )
    {
        if ( givenX > x && givenX < x + wid && givenY > y && givenY < y + hei )
        {
            return true;
        }
        return false;
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
    }


    public void useWeapon()
    {
        if ( getHolding() != null )
        {
            getHolding().startAttacking();
        }
    }


    public void stopUsingWeapon()
    {
        if ( getHolding() != null )
        {
            getHolding().stopAttacking();
        }
    }


    /**
     * Return the value of hp for this player.
     * 
     * @return
     */
    public double getHP()
    {
        return hp;
    }


    /**
     * Sets hp to new hp value
     * 
     * @param newHP
     *            new hp value
     */
    public void setHP( double newHP )
    {
        hp = newHP;
        if ( hp <= 0 )
        {
            setAlive( false );
        }
    }


    /**
     * Returns the value of velocity along the horizontal axis.
     * 
     * @return c velocity
     */
    public double getVX()
    {
        return vX;
    }


    /**
     * Sets the value of velocity along the horizontal axis.
     * 
     * @param newVC
     *            new c velocity
     */
    public void setVX( double newVX )
    {
        vX = newVX;
    }


    public void changeVX( double addVX )
    {
        vX = vX + addVX;
    }


    /**
     * Returns the value of velocity along the vertical axis.
     * 
     * @return r velocity
     */
    public double getVY()
    {
        return vY;
    }


    /**
     * Sets the value of velocity along the vertical axis.
     * 
     * @param new
     *            r velocity
     */
    public void setVY( double newVY )
    {
        vY = newVY;
    }


    public void changeVY( double addY )
    {
        vY = vY + addY;
    }


    /**
     * Returns column number.
     * 
     * @return column #
     */
    public double getX()
    {
        return x;
    }


    /**
     * Sets column number.
     * 
     * @param c
     *            column #
     */
    public void setX( int x )
    {
        this.x = x;
    }


    /**
     * Returns rows number.
     * 
     * @return row #
     */
    public double getY()
    {
        return y;
    }


    /**
     * Sets row number.
     * 
     * @param r
     *            row #
     */
    public void setY( int y )
    {
        this.y = y;
    }


    /**
     * Returns color of character.
     * 
     * @return color
     */
    public Color getColor()
    {
        return color;
    }


    /**
     * Sets color of character.
     * 
     * @param c
     */
    public void setColor( Color c )
    {
        color = c;
    }


    /**
     * Sets image of character.
     * 
     * @param i
     *            image
     */
    public void setImage( BufferedImage i )
    {
        img = i;
    }


    /**
     * Returns world.
     * 
     * @return world
     */
    public World getWorld()
    {
        return w;
    }


    /**
     * Sets world to w.
     * 
     * @param w
     *            world
     */
    public void setWorld( World w )
    {
        this.w = w;
    }


    /**
     * Returns if alive
     * 
     * @return boolean of healthy
     */
    public boolean isAlive()
    {
        return hp > 0;
    }


    public void setAlive( boolean newStatus )
    {
        alive = newStatus;
    }


    /**
     * Sets the player's weapon
     * 
     * @param x
     *            the weapon, oh how kind
     */
    public void setWeapon( Weapon x )
    {
        if ( getHolding() != null )
        {
            getHolding().weaponBreak();
        }
        setHolding( x );
    }


    /**
     * Get the player's weapon
     * 
     * @return Weapon weap-on
     */
    public Weapon getWeapon()
    {
        return getHolding();
    }


    public void setLeft( boolean left )
    {
        this.left = left;
        setVelocitys();
    }


    public void setRight( boolean right )
    {
        this.right = right;
        setVelocitys();
    }


    public void setUp( boolean up )
    {
        this.up = up;
        setVelocitys();
    }


    public void setDown( boolean down )
    {
        this.down = down;
        setVelocitys();
    }


    public double getWid()
    {
        return wid;
    }


    public void setWid( double wid )
    {
        this.wid = wid;
    }


    public double getHei()
    {
        return hei;
    }


    public void setHei( double hei )
    {
        this.hei = hei;
    }


    public Weapon getHolding()
    {
        return holding;
    }


    public void setHolding( Weapon holding )
    {
        this.holding = holding;
    }
}
