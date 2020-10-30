package testMine;

import java.util.Random;


public class Generate
{
    private String[][] world;

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
        setWorld( new String[row][col] );
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                getWorld()[i][j] = " ";
            }
        }
    }


    void makeTestWorld2()
    {
        setWorld( new String[row][col] );
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                getWorld()[i][j] = " ";
                if ( i == 2 && j == 2 )
                {
                    getWorld()[i][j] = "#";
                }
            }
        }
    }


    void makeWorld()
    {
        setWorld( new String[row][col] );
        for ( int i = 0; i < row; i++ )
        {
            for ( int j = 0; j < col; j++ )
            {
                if ( rand.nextInt( 99 ) + 1 <= 45 )
                {
                    getWorld()[i][j] = "#";
                }
                else
                {
                    getWorld()[i][j] = " ";
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
                System.out.print( getWorld()[i][j] );
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
                    getWorld()[i][j] = "#";
                }
                else
                {
                    getWorld()[i][j] = " ";
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
                if ( getWorld()[i][j].equals( "#" ) )
                {
                    if ( rand.nextInt( 20 ) == 0 )
                    {
                        getWorld()[i][j] = "G";
                    }
                    else if ( rand.nextInt( 10 ) == 0 )
                    {
                        getWorld()[i][j] = "S";
                    }
                }
                else if ( getWorld()[i][j].equals( " " ) )
                {
                    if ( rand.nextInt( 20 ) == 0 )
                    {
                        getWorld()[i][j] = "g";
                    }
                    else if ( rand.nextInt( 50 ) == 0 )
                    {
                        getWorld()[i][j] = "T";
                    }
                    else if ( rand.nextInt( 40 ) == 0 )
                    {
                        getWorld()[i][j] = "t";
                    }
                    else if ( rand.nextInt( 100 ) == 0 )
                    {
                        getWorld()[i][j] = "B";
                    }
                    else if ( rand.nextInt( 200 ) == 0 )
                    {
                        getWorld()[i][j] = "H";
                    }
                    else if ( rand.nextInt( 5 ) == 0 )
                    {
                        getWorld()[i][j] = "W";
                    }
                }
            }
        }
    }


    void oldentrance()
    {
        iE = 0;
        jE = 0;
        while ( !getWorld()[iE][jE].equals( " " ) )
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
            getWorld()[iE][jE] = "E";
            iE--;
        }
        iE++;
    }


    void entrance()
    {
        iE = rand.nextInt( row );
        jE = rand.nextInt( col );
        while ( !getWorld()[iE][jE].equals( " " ) )
        {
            iE = rand.nextInt( row );
            jE = rand.nextInt( col );
        }
        System.out.println( "E at" + jE + ", " + iE );
        getWorld()[iE][jE] = "E";

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
                    getWorld()[i][j] = " ";
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
        if ( getWorld()[y][x].equals( "#" ) )
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
                if ( getWorld()[i][j].equals( "G" ) )
                {

                }
            }
        }
    }


    public int distance( int x, int y )
    {
        return Math.abs( x - y );
    }


    public String[][] getWorld()
    {
        return world;
    }


    public void setWorld( String[][] world )
    {
        this.world = world;
    }
}
