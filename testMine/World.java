package testMine;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Timer;


public class World
{
    int xDim;// x lenght of the world

    int yDim;// y lenght of the world

    public Tile[][] theWorld;// store all our blocks

    ArrayList<Player> thePlayers = new ArrayList<Player>();// players

    ArrayList<Enemy> theEnemies = new ArrayList<Enemy>();// enemies

    ArrayList<Item> theItems = new ArrayList<Item>();

    Timer worldTimer; // our world time, its our animation!

    Displayer dis;

    Image playerImage, bombImage, attackImage, treasureImage, enemyImage, goldTileImage, silverTileImage, 
    bombSackImage, healthPotImage;

    Listener lis;

    static Color brown = new Color( 90, 55, 20 );

    Timer bombTimer;

    long countoftime = 0;

    int entranceX = 0;

    int entranceY = 0;

    int TileSize;
    
    int[] endGold;

    int totalPlayers;
    /**
     * lets create a whole new world!
     * 
     * @param x
     *            the x Dimention of the world
     * @param y
     *            the y Dimention of the world
     * @param playersx
     *            the amount of player
     * @param enemiesx
     *            the amount of enemies
     * @param lis
     *            the listener to listen to the keystrokes.
     * @param TileSize
     *            TODO will make things scale based of this size - which will be
     *            how many pixels by pixels each block is
     * 
     */
    public World( int x, int y, int playersx, Listener lis, Displayer dis, int TileSize )
    {
        this.TileSize = TileSize;
        xDim = x;
        yDim = y;
        totalPlayers = playersx;
        this.lis = lis;
        this.dis = dis;
        theWorld = new Tile[y][x];
        endGold = new int[playersx];
        loadImages();
        convertWorld( x, y );
        for ( int i = 0; i < playersx; i++ )
        {
            thePlayers.add( new Player( entranceX, entranceY, 0, 0, 10, Color.PINK, this, 5 ) );
            thePlayers.get( i ).setImage( playerImage );
        }
        this.dis.setGameRun( true );
        runWorld();
        lis.addWorld( this );
    }


    /**
     * load all the images in this method
     */
    public void loadImages()
    {
        System.out.println( "Loading images" );
        try
        {
            playerImage = ImageIO.read( new File( "gautum - Copy.jpg" ) );
            //bombImage = ImageIO.read( new File( "Bomb10x10.jpg" ) );
            bombImage = ImageIO.read( new File( "BlueShell.jpg" ) );
            attackImage = ImageIO.read( new File( "attackImage.jpg" ) );
            treasureImage = ImageIO.read( new File( "treasure.jpg" ) );
            bombSackImage = ImageIO.read( new File( "bombSack.png" ) );
            enemyImage = ImageIO.read( new File( "enemy1.jpg" ) );
            goldTileImage = ImageIO.read( new File( "goldtile2.jpg" ) );
            silverTileImage = ImageIO.read( new File( "silvertile.jpg" ) );
            healthPotImage = ImageIO.read(  new File( "HealthPot.png") );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }


    /**
     * converts the world from a 2d string array to a 2d tile array.
     * 
     * @param x
     *            width
     * @param y
     *            height
     */
    public void convertWorld( int x, int y )
    {
        System.out.println( "Converting world" );
        int enemyIndex = 0;
        Generate gen = new Generate( x, y );
        for ( int i = 0; i < y; i++ )
        {
            for ( int j = 0; j < x; j++ )
            {
                if ( gen.world[i][j].equals( "G" ) )
                {
                    theWorld[i][j] = new GoldTile( j, i, this );
                    theWorld[i][j].setImage( goldTileImage );
                }
                else if ( gen.world[i][j].equals( "S" ) )
                {
                    theWorld[i][j] = new SilverTile( j, i, this );
                    theWorld[i][j].setImage( silverTileImage );
                }
                else if ( gen.world[i][j].equals( "t" ) )
                {
                    theWorld[i][j] = new TrapTile( true, 0, Color.RED, j, i, this );
                }
                else if ( gen.world[i][j].equals( "#" ) )
                {
                    theWorld[i][j] = new RegularTile( false, 0, Color.BLACK, j, i, this );
                }
                else if ( gen.world[i][j].equals( "g" ) )
                {
                    theEnemies.add( new Enemy( j, i, 1, 1, 3, Color.PINK, this ) );
                    theEnemies.get( enemyIndex ).setImage( enemyImage );
                    enemyIndex++;
                    theWorld[i][j] = new RegularTile( true, 0, Color.GREEN, j, i, this );
                }
                else if ( gen.world[i][j].equals( "E" ) )
                {
                    entranceX = j;
                    entranceY = i;
                    theWorld[i][j] = new EntranceTile( j, i, this );
                }
                else if ( gen.world[i][j].equals( "T" ) )
                {
                    theItems.add( new TreasureChestItem( j, i, 2500, treasureImage, this ) );
                    theWorld[i][j] = new RegularTile( true, 0, Color.GREEN, j, i, this );
                }
                else if ( gen.world[i][j].equals( "B" ) )
                {
                    theItems.add( new BombSackItem( j, i, 5, bombSackImage, this ) );
                    theWorld[i][j] = new RegularTile( true, 0, Color.GREEN, j, i, this );
                }
                else if ( gen.world[i][j].equals( "H" ) )
                {
                    theItems.add( new HealthPotItem( j, i, 5, healthPotImage, this ) );
                    theWorld[i][j] = new RegularTile( true, 0, Color.GREEN, j, i, this );
                }
                else
                {
                    theWorld[i][j] = new RegularTile( true, 0, Color.GREEN, j, i, this );
                }
            }
        }
    }


    /**
     * removes a enemy from the list.
     * 
     * @param enemy
     *            that needs to be removed
     */
    public void enemyDeath( Enemy enemy )
    {
        enemy.removeEnemy();
        theEnemies.remove( enemy );
    }

    int playerhold;
    /**
     * removes a player from the list
     * 
     * @param player
     *            that needs to be removed
     */
    public void playerDeath( Player player )
    {
        playerhold = thePlayers.indexOf( player );
        endGold[playerhold] = player.getGold();
        if ( thePlayers.size() == 1 )
        {
            endGame();
            return;
        }
        player.removePlayer();
        player.setAlive( false );
        player.setColor( Color.cyan );
        thePlayers.remove( player );
    }


    public void itemDeath( Item ditem )
    {
        ditem.removeItem();
        theItems.remove( ditem );
    }


    /**
     * the distance between 2 doubles
     * 
     * @param d
     *            point one
     * @param y
     *            point two
     * @return the distance between the points
     */
    public double distance( double d, double y )
    {
        return Math.abs( d - y );
    }


    /**
     * runs the world's timer to send to the other classes set delay to be the
     * amount of FPS (1000 / delay = fps)
     */
    public void runWorld()
    {
        int delay = 10;
        worldTimer = new Timer( delay, new ActionListener()
        {

            @Override
            public void actionPerformed( ActionEvent e )
            {
                dis.repaint();
                countoftime++;
            }

        } );
        worldTimer.start();
    }


    /**
     * Bombs an area in the world, clearing the blocks in a 2 block radius. Can
     * kill stuff!
     * 
     * @param x
     *            the
     * @param y
     *            spot
     * @param delay
     *            how long till boom!
     */
    public void bombArea( double x, double y, int delay )
    {
        theItems.add(  new BombItem(x, y, 3, bombImage, this) );
        System.out.println( x + " " + y + " start ticking!" );
    }

    /**
     * add atk sprite for time seconds
     * @param x
     * @param y
     * @param time
     */
    public void addAttackSprite(double x, double y, double time)
    {
        theItems.add( new TemporaryItem(x, y, time, attackImage, this) );
    }

    /**
     * Clears an area, like creating a cavern but also killing things in it!
     * 
     * @param r1
     *            the
     * @param c1
     *            spot
     * @param cavenRadius
     *            the radius
     */
    public void clearArea( double x, double y, double radius )
    {
        for ( int xx = 0; xx < xDim; xx++ )
        {
            for ( int yy = 0; yy < yDim; yy++ )
            {
                if ( distance( xx, x ) + distance( yy, y ) <= radius )
                {
                    if ( theWorld[yy][xx] instanceof TrapTile )
                    {
                        theWorld[yy][xx].destroyTile();
                        theWorld[yy][xx] = new RegularTile( true, 0, brown, xx, yy, this );
                    }
                    else
                    {
                        theWorld[yy][xx].destroyTile();
                    }
                }
            }
        }

        for ( int i = 0; i < thePlayers.size(); i++ )
        {
            if ( distance( thePlayers.get( i ).getX(), x ) + distance( thePlayers.get( i ).getY(), y ) < radius )
            {
                playerDeath( thePlayers.get( i ) );
            }
        }
        for ( int i = 0; i < theEnemies.size(); i++ )
        {
            if ( distance( theEnemies.get( i ).getX(), x ) + distance( theEnemies.get( i ).getY(), y ) < radius )
            {
                enemyDeath( theEnemies.get( i ) );
                i--;
            }
        }
    }


    /**
     * Returns a player if it is in a radius to the given tile's location
     * basically, checks the center of a tile to the center of a player
     * 
     * @param x
     *            cord
     * @param y
     *            cord
     * @param radius
     *            radius
     * @return the player, if found. Null otherwise.
     */
    public Player detectPlayer( double x, double y, double radius )
    {
        for ( Player p : thePlayers )
        {
            if ( distance( p.getX() + p.getWidth() / 2, x + .5 )
                + distance( p.getY() + p.getHeight() / 2, y + .5 ) < radius )
            {
                System.out.println( "detected!" );
                return p;
            }
        }
        return null;
    }


    /**
     * Sets the displayer, starts the world time!
     * 
     * @param d
     *            the displayer.
     */
    public void setDisplayer( Displayer d )
    {
        dis = d;
    }


    /**
     * Returns list of enemies.
     * 
     * @return enemies
     */
    public ArrayList<Enemy> getEnemies()
    {
        return theEnemies;
    }


    /**
     * Returns list of players.
     * 
     * @return players
     */
    public ArrayList<Player> getPlayers()
    {
        return thePlayers;
    }
    
    public ArrayList<Item> getItems()
    {
        return theItems;
    }


    public Tile getTile( double x, double y )
    {
        return theWorld[(int)y][(int)x];
    }


    public void endGame()
    {
        System.out.println( "GaMe OvEr" );
        dis.setGameRun( false );
        dis.setGameFinish( true );
        worldTimer.stop();
        while ( theItems.size() > 0 )
        {
            theItems.get( 0 ).removeItem();
            theItems.remove( 0 );
        }
        while ( theEnemies.size() > 0 )
        {
            theEnemies.get( 0 ).removeEnemy();
            theEnemies.remove( 0 );
        }
        lis.gameRun( false );
        lis.gameFinish( true );
        while ( thePlayers.size() > 0 )
        {
            thePlayers.get( 0 ).removePlayer();
            thePlayers.remove( 0 );
        }
        for ( int i = 0; i < yDim; i++ )
        {
            for ( int j = 0; j < xDim; j++ )
            {
                theWorld[i][j].removeTile();
            }            
        }
    }
    
    public int getTotalPlayers()
    {
        return totalPlayers;
    }
}
