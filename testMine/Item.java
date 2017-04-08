package testMine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;


public class Item
{
    Point2D.Double d;

    int gold;

    Image img;

    double hei;

    double wid;
    
    World world;

    /**
     * Use this class if you want to draw a PermantSprite. or else, just use the other classes.
     * @param x
     * @param y
     * @param gold
     * @param img
     */
    public Item( double x, double y, int gold, Image img, World world )
    {
        d = new Point2D.Double( x, y );
        this.world = world;
        this.gold = gold;
        this.img = img;
        hei = 1;
        wid = 1;
    }


    public void drawMe( Graphics g )
    {
        if ( img == null )
        {
            g.setColor( Color.ORANGE );
            g.fillRect( (int)( d.getX() * 40 ), (int)( d.getY() * 40 ), 40, 40 );
//            g.setColor( Color.MAGENTA );
//            g.drawString( d.getX() + ", " + d.getY(), (int)(d.getX() * 40), (int)(d.getY() * 40) );

        }
        else
        {
            //g.setColor( Color.ORANGE );
            //g.fillRect( (int)( d.getX() * 40 ), (int)( d.getY() * 40 ), (int)( 40 * wid ), (int)( 40 * hei ) );
            g.drawImage( img,
                (int)( d.getX() * 40 ),
                (int)( d.getY() * 40 ),
                null );
//            g.setColor( Color.MAGENTA );
//            g.drawString( d.getX() + ", " + d.getY(), (int)(d.getX() * 40), (int)(d.getY() * 40) );
        }
    }


    public Point2D.Double getPoint()
    {
        return d;
    }

    /**
     * Return the distance between something's center and the player's center
     * 
     * @param player
     *            player chosen
     * @param gx
     *            x
     * @param gy
     *            y
     * @param wid
     *            wid
     * @param hei
     *            hei
     * @return the distance double
     */
    public double distanceFromPlayer( Player player, int gx, int gy, int wid, int hei )
    {
        return Math.sqrt( ( ( player.getX() + player.getWidth() / 2 ) - ( gx + wid / 2 ) )
            * ( ( player.getX() + player.getWidth() / 2 ) - ( gx + wid / 2 ) )
            + ( ( player.getY() + player.getHeight() / 2 ) - ( gy + hei / 2 ) )
                * ( ( player.getY() + player.getHeight() / 2 ) - ( gy + hei / 2 ) ) );
    }
    
    /**
     * Removes the timers, if any
     */
    public void removeItem()
    {       
        return;
    }
}
