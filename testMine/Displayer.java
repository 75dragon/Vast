package testMine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import entity.Enemy;
import entity.Player;
import items.Item;
import tile.Tile;
import world.World;

public class Displayer extends JPanel
{
	World world;

	private GameText writer;

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

	boolean gameStart = true;

	boolean gameRun = false;

	boolean gameFinish = false;

	public Displayer(int disX, int disY, int tileSize, GameText hold)
	{
		setWriter(hold);
		this.disX = disX;
		this.disY = disY;
		this.tileSize = tileSize;
	}

	public void addWorld(World w)
	{
		world = w;
		x = new Dimension(disX, disY);
		this.setSize(x);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.g = g;
		if (gameStart)
		{
			drawStart();
		}
		if (gameRun)
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
		g.setColor(Color.BLACK);
		g.fillRect(-disX, -disY, disX * 3, disY * 3);
		g.translate(disX / 2 - (int) (world.getPlayers().get(0).getX() * tileSize),
				disY / 2 - (int) (world.getPlayers().get(0).getY() * tileSize));
		for (int x = 0; x < world.theWorld[0].length; x++)
		{
			for (int y = 0; y < world.theWorld.length; y++)
			{
				drawTile(world.theWorld[y][x], x, y);
			}
		}

		for (int x = 0; x < world.getItems().size(); x++)
		{
			drawItem(world.getItems().get(x));
		}

		for (int x = 0; x < world.getEnemies().size(); x++)
		{
			drawEnemy(world.getEnemies().get(x));
		}

		for (int x = 0; x < world.getPlayers().size(); x++)
		{
			drawPlayer(world.getPlayers().get(x));
		}

		g.setColor(Color.WHITE);
		say = world.getPlayers().get(0).getGold() + "";
		say = words + say;
		saytime = world.getCountoftime() / 100 + "";
		saytime = whatistime + saytime;
		g.setFont(new Font("Courier", Font.BOLD, 20));

		g.translate(0, 0);

		g.drawString(say, (int) (world.getPlayers().get(0).getX() * tileSize) - 350,
				(int) (world.getPlayers().get(0).getY() * tileSize) - 350);
		g.drawString(saytime, (int) (world.getPlayers().get(0).getX() * tileSize) - 350,
				(int) (world.getPlayers().get(0).getY() * tileSize) - 310);
		getWriter().printList((int) (world.getPlayers().get(0).getX() * tileSize) - 200,
				(int) (world.getPlayers().get(0).getY() * tileSize) + 260, g);
	}

	public void drawStart()
	{
		g.setFont(new Font("Courier", Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, disX, disY);
		g.setColor(Color.WHITE);
		g.drawString("Welcome to Vast! Press S to start", 50, 100);
		g.drawString("Use the arrow keys to move and explore", 50, 130);
		g.drawString("Space to drop a bomb, F to attack", 50, 160);
		g.drawString("Return to the entrance", 50, 190);
		g.drawString("    with treasures of gold!", 50, 220);
	}

	public void drawEnd()
	{
		g.setFont(new Font("Courier", Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, disX, disY);
		g.setColor(Color.WHITE);
		g.drawString("Game Over, Press G to Restart", 50, 100);
		for (int i = 1; i < world.getTotalPlayers() + 1; i++)
		{
			g.drawString("Player " + i + " Final gold: " + world.getEndGold()[i - 1], 100, 100 * i + 100);
		}
		for (int i = 1; i < world.getTotalPlayers() + 1; i++)
		{
			g.drawString(world.getEndText()[i - 1], 150, 100 * i + 150);
		}
	}

	private void drawItem(Item item)
	{
		if (6 > distance(world.getPlayers().get(0).getX(), item.getPoint().getX())
				+ distance(world.getPlayers().get(0).getY(), item.getPoint().getY()))
		{
			item.drawMe(g);
		}
	}

	private void drawPlayer(Player player2)
	{
		player2.drawMe(g);
	}

	private void drawEnemy(Enemy enemy2)
	{
		if (6 > distance(world.getPlayers().get(0).getX(), enemy2.getX())
				+ distance(world.getPlayers().get(0).getY(), enemy2.getY()))
		{
			enemy2.drawMe(g);
		}
	}

	public void drawTile(Tile dTile, int x, int y)
	{
		if (6 > distance(world.getPlayers().get(0).getX(), x) + distance(world.getPlayers().get(0).getY(), y))
		{
			dTile.drawSelf(g);
		}
	}

	public double distance(double x, double y)
	{
		return Math.abs(x - y);
	}

	public void setGameStart(boolean x)
	{
		gameStart = x;
	}

	public void setGameRun(boolean x)
	{
		gameRun = x;
	}

	public void setGameFinish(boolean x)
	{
		gameFinish = x;
	}

	public GameText getWriter()
	{
		return writer;
	}

	public void setWriter(GameText writer)
	{
		this.writer = writer;
	}
}
