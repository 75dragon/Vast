package testMine;

import java.awt.BorderLayout;

import javax.swing.JFrame;


public class GameStart
{
    int frameX = 800;
    int frameY = 800;
    int X = 100;
    int Y = 100;
    int defaultTilePixelSize = 40;
    int players = 1;
    boolean test = false;
    
    public GameStart()
    {
        Listener lis = new Listener();
        Displayer dis = new Displayer(frameX, frameY, defaultTilePixelSize);
        World world = new World( X, Y, players, lis, dis, defaultTilePixelSize );
        
        BorderLayout layout = new BorderLayout();
        dis.addWorld( world );
        JFrame frame = new JFrame();
        frame.setSize( frameX, frameY );
        frame.setLayout( layout );
        frame.add( dis, BorderLayout.CENTER );
        frame.addKeyListener( lis );

        frame.setVisible( true );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setResizable( false );
        
        world.runWorld();
    }
    
    public static void main( String[] args )
    {
        new GameStart();
    }
}
