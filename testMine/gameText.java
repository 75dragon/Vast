package testMine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

public class GameText
{
    private static int SPACE_BETWEEN_TEXT = 20;
    int i = 0;
    String hold;
    Iterator<String> iter;
    LinkedList<String> gameText = new LinkedList<String>();
    public GameText()
    {
    }
    
    public void addText(String text)
    {
        System.out.println( text );
        gameText.add( text );
        if (gameText.size() > 5)
        {
            gameText.removeFirst();
        }
    }
    
    public void printList(int x, int y, Graphics g)
    {
        g.setColor( Color.WHITE );
        g.setFont( new Font( "Courier", Font.PLAIN, 15 ) );
        iter = gameText.iterator();
        while( iter.hasNext() )
        {
            hold = iter.next();
            g.drawString( hold, x, y + i * SPACE_BETWEEN_TEXT );
            i++;
        }
        i = 0;
    }
    
    public void resetText()
    {
        gameText.clear();
    }
}
