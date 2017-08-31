package testMine;

import java.util.Random;


public class Generate
{
    String[][] world;

    int[][] numbers;

    int col, row;

    Random rand;

    int iE = 0;

    int jE = 0;


    public Generate( int x, int y )
    {
        rand = new Random();
        row = y;
        col = x;
        // makeTestWorld2();
        makeWorld();
        printWorld();
        iterateWorld();
        printWorld();
        iterateWorld();
        finalTouch();
        entrance();
        clearAroundEntrance();
        printWorld();
    }


    void makeTestWorld()
    {
        world = new String[row][col];
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                world[i][j] = " ";
            }
        }
    }


    void makeTestWorld2()
    {
        world = new String[row][col];
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                world[i][j] = " ";
                if ( i == 2 && j == 2 )
                {
                    world[i][j] = "#";
                }
            }
        }
    }


    void makeWorld()
    {
        world = new String[row][col];
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                if ( rand.nextInt( 99 ) + 1 <= 45 )
                {
                    world[i][j] = "#";
                }
                else
                {
                    world[i][j] = " ";
                }
            }
        }
    }


    void printWorld()
    {
        System.out.println();
        System.out.println();
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                System.out.print( world[i][j] );
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }


    void iterateWorld()
    {
        numbers = new int[row][col];
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                numbers[i][j] = getWall( i + 1, j + 1 ) + getWall( i + 1, j ) + getWall( i + 1, j - 1 )
                    + getWall( i, j + 1 ) + getWall( i, j ) + getWall( i, j - 1 ) + getWall( i - 1, j + 1 )
                    + getWall( i - 1, j ) + getWall( i - 1, j - 1 );
            }
        }

        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                if ( numbers[i][j] > 4 )
                {
                    world[i][j] = "#";
                }
                else
                {
                    world[i][j] = " ";
                }
            }
        }
    }


    void finalTouch()
    {
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                if ( world[i][j].equals( "#" ) )
                {
                    if ( rand.nextInt( 20 ) == 0 )
                    {
                        world[i][j] = "G";
                    }
                    else if ( rand.nextInt( 10 ) == 0 )
                    {
                        world[i][j] = "S";
                    }
                }
                else if ( world[i][j].equals( " " ) )
                {
                    if ( rand.nextInt( 20 ) == 0 )
                    {
                        world[i][j] = "g";
                    }
                    else if ( rand.nextInt( 50 ) == 0 )
                    {
                        world[i][j] = "T";
                    }
                    else if ( rand.nextInt( 40 ) == 0 )
                    {
                        world[i][j] = "t";
                    }
                    else if ( rand.nextInt( 100 ) == 0 )
                    {
                        world[i][j] = "B";
                    }
                    else if ( rand.nextInt( 200 ) == 0 )
                    {
                        world[i][j] = "H";
                    }
                    else if ( rand.nextInt( 500 ) == 0 )
                    {
                        world[i][j] = "W";
                    }
                }
            }
        }
    }


    void oldentrance()
    {
        iE = 0;
        jE = 0;
        while ( !world[iE][jE].equals( " " ) )
        {
            jE++;
            if ( jE >= col )
            {
                jE = 0;
                iE++;
            }
        }
        System.out.println( "E at" + jE + ", " + iE );
        while ( iE >= 0 )
        {
            world[iE][jE] = "E";
            iE--;
        }
        iE++;
    }


    void entrance()
    {
        iE = rand.nextInt( row );
        jE = rand.nextInt( col );
        while ( !world[iE][jE].equals( " " ) )
        {
            iE = rand.nextInt( row );
            jE = rand.nextInt( col );
        }
        System.out.println( "E at" + jE + ", " + iE );
        world[iE][jE] = "E";

    }


    public void clearAroundEntrance()
    {
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                if ( i == iE && j == jE )
                {

                }
                else if ( distance( i, iE ) + distance( j, jE ) < 4 )
                {
                    world[i][j] = " ";
                }
            }
        }
    }


    int getWall( int y, int x )
    {
        if ( x >= col || x < 0 || y >= row || y < 0 )
        {
            return 1;
        }
        if ( world[y][x].equals( "#" ) )
        {
            return 1;
        }
        return 0;
    }


    void convertWorld()
    {
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                if ( world[i][j].equals( "G" ) )
                {

                }
            }
        }
    }


    public int distance( int x, int y )
    {
        return Math.abs( x - y );
    }
}
