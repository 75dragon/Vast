package testMine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Displayer extends JPanel
{
    World world;

    Graphics g;

    Enemy enemy;

    Player player;

    Dimension x;

    private String words = "Gold: ";

    private String say = "";

    private String whatistime = "Time underground: ";

    private String saytime = "";

    int disX;

    int disY;

    int tileSize;

    boolean gameStart = false;

    boolean gameRun = false;

    boolean gameFinish = false;


    public Displayer( int disX, int disY, int tileSize )
    {
        this.disX = disX;
        this.disY = disY;
        this.tileSize = tileSize;
    }


    public void addWorld( World w )
    {
        world = w;
        x = new Dimension( disX, disY );
        this.setSize( x );
    }


    @Override
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        this.g = g;
        if ( gameRun )
        {
            drawGame();
        }
        if (gameFinish)
        {
            drawEnd();
        }
    }


    public void drawGame()
    {
        g.setColor( Color.BLACK );
        g.fillRect( -disX, -disY, disX * 3, disY * 3 );
        g.translate( disX / 2 - (int)( world.thePlayers.get( 0 ).getX() * tileSize ),
            disY / 2 - (int)( world.thePlayers.get( 0 ).getY() * tileSize ) );
        for ( int x = 0; x < world.theWorld[0].length; x++ )
        {
            for ( int y = 0; y < world.theWorld.length; y++ )
            {
                drawTile( world.theWorld[y][x], x, y );
            }
        }

        for ( int x = 0; x < world.theItems.size(); x++ )
        {
            drawItem( world.theItems.get( x ) );
        }

        for ( int x = 0; x < world.theEnemies.size(); x++ )
        {
            drawEnemy( world.theEnemies.get( x ) );
        }

        for ( int x = 0; x < world.thePlayers.size(); x++ )
        {
            drawPlayer( world.thePlayers.get( x ) );
        }

        g.setColor( Color.WHITE );
        say = world.thePlayers.get( 0 ).getGold() + "";
        say = words + say;
        saytime = world.countoftime / 200 + "";
        saytime = whatistime + saytime;
        g.setFont( new Font( "Courier", Font.BOLD, 30 ) );

        g.translate( 0, 0 );

        g.drawString( say,
            (int)( world.thePlayers.get( 0 ).getX() * tileSize ) - 200,
            (int)( world.thePlayers.get( 0 ).getY() * tileSize ) - 300 );
        g.drawString( saytime,
            (int)( world.thePlayers.get( 0 ).getX() * tileSize ) - 200,
            (int)( world.thePlayers.get( 0 ).getY() * tileSize ) - 260 );
    }

    public void drawEnd()
    {
        g.setFont( new Font( "Courier", Font.BOLD, 30 ) );
        g.setColor( Color.BLACK );
        g.fillRect( 0, 0, disX, disY);
        g.setColor( Color.WHITE );
        g.drawString( "Game Over", 50, 100 );
        for( int i = 1; i < world.getTotalPlayers() + 1; i++)
        {
            g.drawString( "Player " + i + " Final gold: " + world.endGold[i - 1], 100, 100 * i + 100 );
        }
        for( int i = 1; i < world.getTotalPlayers() + 1; i++)
        {
            g.drawString( world.endText[i - 1], 150, 100 * i + 150 );
        }
    }

    private void drawItem( Item item )
    {
        if ( 6 > distance( world.thePlayers.get( 0 ).getX(), item.getPoint().getX() )
            + distance( world.thePlayers.get( 0 ).getY(), item.getPoint().getY() ) )
        {
            item.drawMe( g );
        }
    }


    private void drawPlayer( Player player2 )
    {
        player2.drawMe( g );
    }


    private void drawEnemy( Enemy enemy2 )
    {
        if ( 6 > distance( world.thePlayers.get( 0 ).getX(), enemy2.getX() )
            + distance( world.thePlayers.get( 0 ).getY(), enemy2.getY() ) )
        {
            enemy2.drawMe( g );
        }
    }


    public void drawTile( Tile dTile, int x, int y )
    {
        if ( 6 > distance( world.thePlayers.get( 0 ).getX(), x ) + distance( world.thePlayers.get( 0 ).getY(), y ) )
        {
            dTile.drawSelf( g );
        }
    }


    public double distance( double x, double y )
    {
        return Math.abs( x - y );
    }
    
    public void setGameRun( boolean x )
    {
        gameRun = x;
    }
    
    public void setGameFinish( boolean x)
    {
        gameFinish = x;
    }
}
