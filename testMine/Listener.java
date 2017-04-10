package testMine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Listener implements KeyListener
{
    World world;

    boolean gameStart = false;

    boolean gameRun = false;

    boolean gameFinish = false;


    public Listener()
    {

    }


    public void addWorld( World a )
    {
        world = a;
    }


    @Override
    /**
     * creates
     */
    public void keyPressed( KeyEvent e )
    {
        switch ( e.getKeyCode() )
        {
            case KeyEvent.VK_LEFT:
                world.thePlayers.get( 0 ).setVX( -1 );
                break;
            case KeyEvent.VK_RIGHT:
                world.thePlayers.get( 0 ).setVX( 1 );
                break;
            case KeyEvent.VK_UP:
                world.thePlayers.get( 0 ).setVY( -1 );
                break;
            case KeyEvent.VK_DOWN:
                world.thePlayers.get( 0 ).setVY( 1 );
                break;
            case KeyEvent.VK_SPACE:
                world.thePlayers.get( 0 ).bomb();
                break;
            case KeyEvent.VK_F:
                world.thePlayers.get( 0 ).useWeapon();
                break;
        }
    }


    @Override
    public void keyReleased( KeyEvent e )
    {
        switch ( e.getKeyCode() )
        {
            case KeyEvent.VK_LEFT:
                world.thePlayers.get( 0 ).setVX( 0 );
                break;
            case KeyEvent.VK_RIGHT:
                world.thePlayers.get( 0 ).setVX( 0 );
                break;
            case KeyEvent.VK_UP:
                world.thePlayers.get( 0 ).setVY( 0 );
                break;
            case KeyEvent.VK_DOWN:
                world.thePlayers.get( 0 ).setVY( 0 );
                break;
            case KeyEvent.VK_F:
                world.thePlayers.get( 0 ).stopUsingWeapon();
                break;
        }
    }


    @Override
    public void keyTyped( KeyEvent e )
    {
        // TODO Auto-generated method stub

    }


    public void gameStart( boolean x )
    {
        gameStart = x;
    }


    public void gameRun( boolean x )
    {
        gameRun = x;
    }


    public void gameFinish( boolean x )
    {
        gameFinish = x;
    }
}
