package world;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import entity.Enemy;
import entity.FollowEnemy;
import entity.Player;
import items.BombItem;
import items.BombSackItem;
import items.GoldBarItem;
import items.HealthPotItem;
import items.Item;
import items.RubyItem;
import items.TemporaryItem;
import items.TreasureChestItem;
import items.WeaponPileItem;
import testMine.Displayer;
import testMine.Generate;
import testMine.Listener;
import tile.EntranceTile;
import tile.GameTick;
import tile.GoldTile;
import tile.RegularTile;
import tile.SilverTile;
import tile.Tile;
import tile.TrapTile;
import weapons.PickaxeWeapon;
import weapons.SpearWeapon;
import weapons.SpinWeapon;

public class World
{
	private int xDim;

	private int yDim;

	public Tile[][] theWorld;

	private ArrayList<Player> thePlayers = new ArrayList<Player>();

	private ArrayList<Enemy> theEnemies = new ArrayList<Enemy>();

	private ArrayList<Item> theItems = new ArrayList<Item>();

	private ArrayList<GameTick> theTicks = new ArrayList<GameTick>();

	private Timer worldTimer;

	private Displayer dis;

	private BufferedImage playerImage, bombImage, attackImage, treasureImage, enemyImage, goldTileImage,
			silverTileImage, bombSackImage, healthPotImage, goldBarImage, rubyImage, pickAxeAttackImage,
			spinAttackImage, weaponPileImage, spearAttackImage;

	private Listener lis;

	private static Color brown = new Color(90, 55, 20);

	private long countoftime = 0;

	private int entranceX = 0;

	private int entranceY = 0;

	private int TileSize;

	private int[] endGold;

	private String[] endText;

	private int totalPlayers;

	private Random rand;

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
	public World(int x, int y, int playersx, Listener lis, Displayer dis, int TileSize)
	{
		rand = new Random();
		this.TileSize = TileSize;
		setxDim(x);
		setyDim(y);
		totalPlayers = playersx;
		this.lis = lis;
		this.setDis(dis);
		theWorld = new Tile[y][x];
		setEndGold(new int[playersx]);
		setEndText(new String[playersx]);
		loadImages();
		firstGame();
		lis.addWorld(this);
	}

	public void firstGame()
	{
		dis.setGameStart(true);
		dis.setGameRun(false);
		dis.setGameFinish(false);
		lis.gameStart(true);
		lis.gameRun(false);
		lis.gameFinish(false);
		dis.repaint();
	}

	public void newGame()
	{
		countoftime = 0;
		convertWorld(xDim, yDim);
		for (int i = 0; i < totalPlayers; i++)
		{
			thePlayers.add(new Player(entranceX, entranceY, 10, Color.PINK, this, 10, "human"));
			thePlayers.get(i).setImage(playerImage);
			thePlayers.get(i).setWeapon(new PickaxeWeapon(attackImage, pickAxeAttackImage, thePlayers.get(i)));
		}
		dis.setGameStart(false);
		dis.setGameRun(true);
		dis.setGameFinish(false);
		lis.gameStart(false);
		lis.gameRun(true);
		lis.gameFinish(false);
		dis.repaint();
		runWorld();
	}

	/**
	 * load all the images in this method
	 */
	public void loadImages()
	{
		System.out.println("Loading images");
		try
		{
			playerImage = ImageIO.read(getClass().getResource("/imgs/Player.jpg"));
			bombImage = ImageIO.read(getClass().getResource("/imgs/BlueShell.jpg"));
			attackImage = ImageIO.read(getClass().getResource("/imgs/AttackImageStar.jpg"));
			treasureImage = ImageIO.read(getClass().getResource("/imgs/TreasureChest.png"));
			bombSackImage = ImageIO.read(getClass().getResource("/imgs/BombSack.png"));
			enemyImage = ImageIO.read(getClass().getResource("/imgs/Enemy1.jpg"));
			goldTileImage = ImageIO.read(getClass().getResource("/imgs/GoldTile.jpg"));
			silverTileImage = ImageIO.read(getClass().getResource("/imgs/SilverTile.jpg"));
			healthPotImage = ImageIO.read(getClass().getResource("/imgs/HealthPot.png"));
			goldBarImage = ImageIO.read(getClass().getResource("/imgs/GoldBar.png"));
			rubyImage = ImageIO.read(getClass().getResource("/imgs/Ruby.png"));
			pickAxeAttackImage = ImageIO.read(getClass().getResource("/imgs/PickAxeStrike.png"));
			spinAttackImage = ImageIO.read(getClass().getResource("/imgs/SpinAttack.png"));
			weaponPileImage = ImageIO.read(getClass().getResource("/imgs/WeaponPile.png"));
			spearAttackImage = ImageIO.read(getClass().getResource("/imgs/Spear.png"));
		}
		catch (IOException e)
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
	public void convertWorld(int x, int y)
	{
		System.out.println("Converting world");
		int enemyIndex = 0;
		Generate gen = new Generate(x, y);
		for (int i = 0; i < y; i++)
		{
			for (int j = 0; j < x; j++)
			{
				// preset tiles:
				switch (gen.getWorld()[i][j])
				{
					case "G": // gold tile
						theWorld[i][j] = new GoldTile(j, i, this);
						theWorld[i][j].setImage(goldTileImage);
						break;
					case "S": // silver tile
						theWorld[i][j] = new SilverTile(j, i, this);
						theWorld[i][j].setImage(silverTileImage);
						break;
					case "t": // trap tile
						TrapTile holdT = new TrapTile(true, 0, Color.RED, j, i, this);
						theWorld[i][j] = holdT;
						theTicks.add(holdT);
						break;
					case "#": // wall tile
						Color hold = new Color(120 + rand.nextInt(21), 20 + rand.nextInt(21), rand.nextInt(21));
						theWorld[i][j] = new RegularTile(false, 0, hold, j, i, this);
						break;
					case "E": // entrance
						entranceX = j;
						entranceY = i;
						EntranceTile thisE = new EntranceTile(j, i, this);
						theWorld[i][j] = thisE;
						theTicks.add(thisE);
						break;
					default: // rest of the tiles are regular and walkable
						Color holdR = new Color(150 + rand.nextInt(21), 150 + rand.nextInt(21), 150 + rand.nextInt(21));
						theWorld[i][j] = new RegularTile(true, 0, holdR, j, i, this);
						switch (gen.getWorld()[i][j])
						{
							case "g": // spawn an enemy
								if (rand.nextInt(2) == 0)
								{
									theEnemies.add(new Enemy(j, i, 1, 1, 3, Color.PINK, attackImage, this, "Dasher"));
								}
								else
								{
									theEnemies.add(
											new FollowEnemy(j, i, 1, 1, 3, Color.PINK, attackImage, this, "Stalker"));
								}
								theEnemies.get(enemyIndex).setImage(enemyImage);
								enemyIndex++;
								break;
							case "T": // treasure item
								theItems.add(new TreasureChestItem(j, i, 2500, treasureImage, this));
								break;
							case "B": // bomb sack item
								theItems.add(new BombSackItem(j, i, 5, bombSackImage, this));
								break;
							case "H": // health pot item
								theItems.add(new HealthPotItem(j, i, 5, healthPotImage, this));
								break;
							case "W": // weapon item
								theItems.add(new WeaponPileItem(j, i, weaponPileImage, this));
								break;
						}
				}
			}
		}
	}

	public void generateDistrikaMap(int currentX, int currentY, int proxy)
	{
		if (proxy > 5)
		{
			return;
		}
		if (currentX >= getxDim() || currentX < 0 || currentY >= getyDim() || currentY < 0)
		{
			return;
		}
		if (!theWorld[currentY][currentX].isPassable())
		{
			return;
		}
		if (theWorld[currentY][currentX].getPlayerProximity() < proxy)
		{
			return;
		}
		theWorld[currentY][currentX].setPlayerProximity(proxy);
		// generateDistrikaMap(currentX - 1, currentY - 1, proxy + 1);
		generateDistrikaMap(currentX - 1, currentY, proxy + 1);
		// generateDistrikaMap(currentX - 1, currentY + 1, proxy + 1);
		generateDistrikaMap(currentX, currentY - 1, proxy + 1);
		generateDistrikaMap(currentX, currentY + 1, proxy + 1);
		// generateDistrikaMap(currentX + 1, currentY - 1, proxy + 1);
		generateDistrikaMap(currentX + 1, currentY, proxy + 1);
		// generateDistrikaMap(currentX + 1, currentY + 1, proxy + 1);
		return;
	}

	public void resetDistrikaMap()
	{
		for (int i = 0; i < getyDim(); i++)
		{
			for (int j = 0; j < getxDim(); j++)
			{
				theWorld[i][j].setPlayerProximity(getxDim() * getyDim());
			}
		}
	}

	/**
	 * removes a enemy from the list.
	 * 
	 * @param enemy
	 *            that needs to be removed
	 */
	public void enemyDeath(Enemy enemy)
	{
		enemy.removeEnemy();
		theEnemies.remove(enemy);
	}

	/**
	 * removes a player from the list
	 * 
	 * @param player
	 *            that needs to be removed
	 */
	public void playerDeath(Player player)
	{
		getEndGold()[thePlayers.indexOf(player)] = player.getGold();
		if (thePlayers.size() == 1)
		{
			endGame();
			return;
		}
		player.removePlayer();
		player.setAlive(false);
		player.setColor(Color.cyan);
		thePlayers.remove(player);
	}

	public void itemDeath(Item ditem)
	{
		ditem.onItemDestroy();
		theItems.remove(ditem);
	}

	public void gameTickDeath(GameTick gTick)
	{
		theTicks.remove(gTick);
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
	public double distance(double d, double y)
	{
		return Math.abs(d - y);
	}

	/**
	 * runs the world's timer to send to the other classes set delay to be the
	 * amount of FPS (1000 / delay = fps)
	 */
	public void runWorld()
	{
		System.out.println(".gameTime");
		int delay = 10;
		worldTimer = new Timer(delay, new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				getDis().repaint();
				setCountoftime(getCountoftime() + 1);
				if (getCountoftime() % 100 == 0)
				{
					resetDistrikaMap();
					for (int i = 0; i < thePlayers.size(); i++)
					{
						generateDistrikaMap((int) thePlayers.get(i).getX(), (int) thePlayers.get(i).getY(), 0);
					}
				}
				for (int i = 0; i < theItems.size(); i++)
				{
					theItems.get(i).itemTick();
				}
				for (int i = 0; i < theEnemies.size(); i++)
				{
					theEnemies.get(i).enemyTick();
				}
				for (int i = 0; i < thePlayers.size(); i++)
				{
					thePlayers.get(i).playerTick();
				}
				for (int i = 0; i < theTicks.size(); i++)
				{
					theTicks.get(i).onTick();
				}
			}

		});
		worldTimer.start();
	}

	/**
	 * Bombs an area in the world, clearing the blocks in a 3 block radius. Can
	 * kill stuff!
	 * 
	 * @param x
	 *            the
	 * @param y
	 *            spot
	 * @param delay
	 *            how long till boom!
	 */
	public void bombArea(double x, double y, int delay)
	{
		theItems.add(new BombItem(x, y, 3, bombImage, this));
	}

	public void addGoldBar(double x, double y)
	{
		theItems.add(new GoldBarItem(x, y, goldBarImage, this));
	}

	public void addRuby(double x, double y)
	{
		theItems.add(new RubyItem(x, y, rubyImage, this));
	}

	/**
	 * Gives a random weapon to the player. Replaces the old one
	 * 
	 * @param x
	 *            Player
	 */
	public void giveRandomItem(Player x)
	{
		int holdr = rand.nextInt(3);
		if (holdr == 0)
		{
			x.setWeapon(new PickaxeWeapon(attackImage, pickAxeAttackImage, x));
		}
		else if (holdr == 1)
		{
			x.setWeapon(new SpinWeapon(attackImage, spinAttackImage, x));
		}
		else
		{
			x.setWeapon(new SpearWeapon(attackImage, spearAttackImage, x));
		}
	}

	/**
	 * add sprite for time seconds
	 * 
	 * @param img
	 * @param x
	 * @param y
	 * @param time
	 */
	public void addSprite(BufferedImage img, double x, double y, double time)
	{
		theItems.add(new TemporaryItem(x, y, time, img, this));
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
	public void clearArea(double x, double y, double radius, int damageDealt)
	{
		System.out.println("clearArea");
		for (int xx = 0; xx < getxDim(); xx++)
		{
			for (int yy = 0; yy < getyDim(); yy++)
			{
				if (distance(xx, x) + distance(yy, y) <= radius)
				{
					if (theWorld[yy][xx] instanceof TrapTile)
					{
						theWorld[yy][xx].blownUp();
						theWorld[yy][xx] = new RegularTile(true, 0, getBrown(), xx, yy, this);
					}
					else
					{
						theWorld[yy][xx].blownUp();
					}
				}
			}
		}
		for (int i = thePlayers.size() - 1; i > -1; i--)
		{
			if (distance(thePlayers.get(i).getX(), x) + distance(thePlayers.get(i).getY(), y) < radius)
			{
				thePlayers.get(i).takeDamage(damageDealt, "Explosion");
			}
		}
		hitEnemiesInArea(x, y, radius, damageDealt, "Explosion");
	}

	/**
	 * Hit the enemies in a area
	 * 
	 * @param x
	 *            xLocation
	 * @param y
	 *            yLocation
	 * @param radius
	 *            of damage
	 * @param damageDealt
	 *            damage dealt
	 * @param cause
	 *            what dealt the damage
	 * @return if you hit an enemy
	 */
	public boolean hitEnemiesInArea(double x, double y, double radius, int damageDealt, String cause)
	{
		boolean hitEnemy = false;
		for (int i = theEnemies.size() - 1; i > -1; i--)
		{
			// TODO fix square root
			if (distance(theEnemies.get(i).getX(), x) + distance(theEnemies.get(i).getY(), y) < radius)
			{
				theEnemies.get(i).takeDamage(damageDealt, cause);
				getDis().getWriter().addText(cause + " hit an enemy for " + damageDealt + " damage");
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
	public Player detectPlayer(double x, double y, double radius)
	{
		for (Player p : thePlayers)
		{
			if (distance(p.getX() + p.getWid() / 2, x + .5) + distance(p.getY() + p.getHei() / 2, y + .5) < radius)
			{
				System.out.println("detected!");
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
	public void setDisplayer(Displayer d)
	{
		setDis(d);
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

	public Tile getTile(double x, double y)
	{
		return theWorld[(int) y][(int) x];
	}

	public void endGame()
	{
		System.out.println("GaMe OvEr");
		dis.setGameRun(false);
		dis.setGameFinish(true);
		dis.repaint();
		worldTimer.stop();
		while (theItems.size() > 0)
		{
			theItems.get(0).onItemDestroy();
			theItems.remove(0);
		}
		while (theEnemies.size() > 0)
		{
			theEnemies.get(0).removeEnemy();
			theEnemies.remove(0);
		}
		while (theTicks.size() > 0)
		{
			theTicks.remove(0);
		}
		lis.gameRun(false);
		lis.gameFinish(true);
		while (thePlayers.size() > 0)
		{
			thePlayers.get(0).removePlayer();
			thePlayers.remove(0);
		}
		for (int i = 0; i < getyDim(); i++)
		{
			for (int j = 0; j < getxDim(); j++)
			{
				theWorld[i][j].removeTile();
			}
		}
		dis.getWriter().resetText();
	}

	public int getTotalPlayers()
	{
		return totalPlayers;
	}

	public long getCountoftime()
	{
		return countoftime;
	}

	public void setCountoftime(long countoftime)
	{
		this.countoftime = countoftime;
	}

	public int[] getEndGold()
	{
		return endGold;
	}

	public void setEndGold(int[] endGold)
	{
		this.endGold = endGold;
	}

	public String[] getEndText()
	{
		return endText;
	}

	public void setEndText(String[] endText)
	{
		this.endText = endText;
	}

	public int getyDim()
	{
		return yDim;
	}

	public void setyDim(int yDim)
	{
		this.yDim = yDim;
	}

	public int getxDim()
	{
		return xDim;
	}

	public void setxDim(int xDim)
	{
		this.xDim = xDim;
	}

	public static Color getBrown()
	{
		return brown;
	}

	public static void setBrown(Color brown)
	{
		World.brown = brown;
	}

	public Displayer getDis()
	{
		return dis;
	}

	public void setDis(Displayer dis)
	{
		this.dis = dis;
	}

	public void pause()
	{
		if (worldTimer.isRunning())
		{
			worldTimer.stop();
		}
		else
		{
			worldTimer.start();
		}
	}

	public ArrayList<GameTick> getTheTicks()
	{
		return theTicks;
	}

	public void setTheTiles(ArrayList<GameTick> theTiles)
	{
		this.theTicks = theTiles;
	}
}
