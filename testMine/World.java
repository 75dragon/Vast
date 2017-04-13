package testMine;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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

    BufferedImage playerImage, bombImage, attackImage, treasureImage, enemyImage, goldTileImage, silverTileImage,
                    bombSackImage, healthPotImage, goldBarImage, rubyImage, pickAxeAttackImage, spinAttackImage,
                    weaponPileImage;

    Listener lis;

    static Color brown = new Color( 90, 55, 20 );

    long countoftime = 0;

    int entranceX = 0;

    int entranceY = 0;

    int TileSize;

    int[] endGold;

    String[] endText;

    int totalPlayers;

    Random rand;


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
        rand = new Random();
        this.TileSize = TileSize;
        xDim = x;
        yDim = y;
        totalPlayers = playersx;
        this.lis = lis;
        this.dis = dis;
        theWorld = new Tile[y][x];
        endGold = new int[playersx];
        endText = new String[playersx];
        loadImages();
        convertWorld( x, y );
        for ( int i = 0; i < playersx; i++ )
        {
            thePlayers.add( new Player( entranceX, entranceY, 10, Color.PINK, this, 5 ) );
            thePlayers.get( i ).setImage( playerImage );
            thePlayers.get( i ).setWeapon( new PickaxeWeapon( attackImage, pickAxeAttackImage, thePlayers.get( i ) ) );
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
            // bombImage = ImageIO.read( new File( "Bomb10x10.jpg" ) );
            playerImage = ImageIO.read( getClass().getResource( "/gautum - Copy.jpg" ) );
            bombImage = ImageIO.read( getClass().getResource( "/BlueShell.jpg" ) );
            attackImage = ImageIO.read( getClass().getResource( "/attackImage.jpg" ) );
            treasureImage = ImageIO.read( getClass().getResource( "/TreasureChest.png" ) );
            bombSackImage = ImageIO.read( getClass().getResource( "/bombSack.png" ) );
            enemyImage = ImageIO.read( getClass().getResource( "/enemy1.jpg" ) );
            goldTileImage = ImageIO.read( getClass().getResource( "/goldtile2.jpg" ) );
            silverTileImage = ImageIO.read( getClass().getResource( "/silvertile.jpg" ) );
            healthPotImage = ImageIO.read( getClass().getResource( "/HealthPot.png" ) );
            goldBarImage = ImageIO.read( getClass().getResource( "/GoldBar.png" ) );
            rubyImage = ImageIO.read( getClass().getResource( "/Ruby.png" ) );
            pickAxeAttackImage = ImageIO.read( getClass().getResource( "/PickAxeStrike.png" ) );
            spinAttackImage = ImageIO.read( getClass().getResource( "/SpinAttack.png" ) );
            weaponPileImage = ImageIO.read( getClass().getResource( "/WeaponPile.png" ) );
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
                else if ( gen.world[i][j].equals( "W" ) )
                {
                    theItems.add( new WeaponPileItem( j, i, weaponPileImage, this ) );
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

    /**
     * removes a player from the list
     * 
     * @param player
     *            that needs to be removed
     */
    public void playerDeath( Player player )
    {
        endGold[thePlayers.indexOf( player )] = player.getGold();
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
        theItems.add( new BombItem( x, y, 3, bombImage, this ) );
        System.out.println( x + " " + y + " start ticking!" );
    }


    public void addGoldBar( double x, double y )
    {
        theItems.add( new GoldBarItem( x, y, goldBarImage, this ) );
    }


    public void addRuby( double x, double y )
    {
        theItems.add( new RubyItem( x, y, rubyImage, this ) );
    }


    public void giveRandomItem( Player x )
    {
        if ( rand.nextInt( 2 ) == 0 )
        {
            x.setWeapon( new PickaxeWeapon( attackImage, pickAxeAttackImage, x ) );
        }
        else
        {
            x.setWeapon( new SpinWeapon( attackImage, spinAttackImage, x ) );
        }
    }


    /**
     * add atk sprite for time seconds TODO: get rid of this
     * 
     * @param x
     * @param y
     * @param time
     */
    public void addAttackSprite( double x, double y, double time )
    {
        theItems.add( new TemporaryItem( x, y, time, attackImage, this ) );
    }


    /**
     * add sprite for time seconds
     * 
     * @param img
     * @param x
     * @param y
     * @param time
     */
    public void addAttackSprite( BufferedImage img, double x, double y, double time )
    {
        theItems.add( new TemporaryItem( x, y, time, img, this ) );
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
    public void clearArea( double x, double y, double radius, int damageDealt )
    {
        for ( int xx = 0; xx < xDim; xx++ )
        {
            for ( int yy = 0; yy < yDim; yy++ )
            {
                if ( distance( xx, x ) + distance( yy, y ) <= radius )
                {
                    if ( theWorld[yy][xx] instanceof TrapTile )
                    {
                        theWorld[yy][xx].blownUp();
                        theWorld[yy][xx] = new RegularTile( true, 0, brown, xx, yy, this );
                    }
                    else
                    {
                        theWorld[yy][xx].blownUp();
                    }
                }
            }
        }
        for ( int i = thePlayers.size() - 1; i > -1; i-- )
        {
            if ( distance( thePlayers.get( i ).getX(), x ) + distance( thePlayers.get( i ).getY(), y ) < radius )
            {
                thePlayers.get( i ).takeDamage( damageDealt, "Explosion" );
            }
        }
        for ( int i = theEnemies.size() - 1; i > -1; i-- )
        {
            if ( distance( theEnemies.get( i ).getX(), x ) + distance( theEnemies.get( i ).getY(), y ) < radius )
            {
                theEnemies.get( i ).takeDamage( damageDealt, "Explosion" );
            }
        }
    }


    public boolean spinSwordClearArea( double x, double y, double radius, int damageDealt )
    {
        boolean hitEnemy = false;
        for ( int i = theEnemies.size() - 1; i > -1; i-- )
        {
            if ( distance( theEnemies.get( i ).getX(), x ) + distance( theEnemies.get( i ).getY(), y ) < radius )
            {
                theEnemies.get( i ).takeDamage( damageDealt, "Spin Sword" );
                hitEnemy = true;
            }
        }
        return hitEnemy;
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
