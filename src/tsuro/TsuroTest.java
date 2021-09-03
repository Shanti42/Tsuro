package tsuro;

//imports TestCase
import junit.framework.TestCase;
import java.awt.Color;

//test class for Tsuro
//author: Shanti Polara
public class TsuroTest extends TestCase{

    private Tsuro gameRegular = new Tsuro();
    private Tsuro gameRC = new Tsuro(3, 7);
    private Tsuro gameRCH = new Tsuro(4, 5, 2);
    private Tsuro gamePlayers = new Tsuro(8);
    private Tsuro gamePRCH = new Tsuro(3, 5, 6, 1);


    //tests that the row size has been initilized correctly and that the get method returns the correct value
    public void testGetRow(){
        /*tests that constructor for a game of tsuro with an empty input initilizes the rows correctly,
         * and tests the get method for row*/
        assertEquals("rows of regular game", gameRegular.getRow(), 6);
        /*tests that constructor for game of tsuro with input for rows and columns initilizes the rows correctly,
         * and tests the get method for row*/
        assertEquals("rows of RC game", gameRC.getRow(), 3);
        /*tests that constructor for game of tsuro with input for rows, columns and player handsize
         * initilizes the rows correctly, and tests the get method for row*/
        assertEquals("rows of RCH game", gameRCH.getRow(), 4);
        /*tests that constructor for game of tsuro with input for player, rows, columns and player handsize
         * initilizes the rows correctly, and tests the get method for row*/
        assertEquals("rows of PRCH game", gamePRCH.getRow(), 5);
        /*tests that constructor for game of tsuro with input for player
         * initilizes the rows correctly, and tests the get method for row*/
        assertEquals("rows of players game", gamePlayers.getRow(), 6);
    }

    //tests that the column size has been initilized correctly and that the get method returns the correct value
    public void testGetColumn(){
        /*tests that constructor for a game of tsuro with an empty input initilizes the columns correctly,
         * and tests the get method for column*/
        assertEquals("columns of regular game", gameRegular.getColumn(), 6);
        /*tests that constructor for game of tsuro with input for rows and columns initilizes the columns correctly,
         * and tests the get method for column*/
        assertEquals("columns of RC game", gameRC.getColumn(), 7);
        /*tests that constructor for game of tsuro with input for rows, columns and player handsize
         * initilizes the columns correctly, and tests the get method for column*/
        assertEquals("columns of RCH game", gameRCH.getColumn(), 5);
        /*tests that constructor for game of tsuro with input for player, rows, columns and player handsize
         * initilizes the columns correctly, and tests the get method for column*/
        assertEquals("columns of PRCH game", gamePRCH.getColumn(), 6);
        /*tests that constructor for game of tsuro with input for player
         * initilizes the columns correctly, and tests the get method for column*/
        assertEquals("columns of players game", gamePlayers.getColumn(), 6);

    }

    //tests that the number of players have been initilized correctly, and that get method returns the correct value
    public void testGetNumberPlayers(){
        /*tests that constructor for a game of tsuro with an empty input initilizes the number of players correctly,
         * and tests the get method for numberPlayers*/
        assertEquals("number players of regular game", gameRegular.getNumberPlayers(), 2);
        /*tests that constructor for game of tsuro with input for rows and columns initilizes the number of players correctly,
         * and tests the get method for numberPlayers*/
        assertEquals("number players of RC game", gameRC.getNumberPlayers(), 2);
        /*tests that constructor for game of tsuro with input for rows, columns and player handsize
         * initilizes the number of players correctly, and tests the get method for numberPlayers*/
        assertEquals("number players of RCH game", gameRCH.getNumberPlayers(), 2);
        /*tests that constructor for game of tsuro with input for player, rows, columns and player handsize
         * initilizes the number of players correctly, and tests the get method for numberPlayers*/
        assertEquals("number players of PRCH game", gamePRCH.getNumberPlayers(), 3);
        /*tests that constructor for game of tsuro with input for player
         * initilizes the number of players correctly, and tests the get method for numberPlayers*/
        assertEquals("number players of players game", gamePlayers.getNumberPlayers(), 8);

    }

    //tests that the player's handsize has been initialized correctly and can be retrived by the get method
    public void testGetHandsize(){
        //asserts that Donald Trump is insecure about the size of certain body parts, and we make fun of him for it
        assertTrue("trump has small hands", true);
        /*tests that constructor for a game of tsuro with an empty input initilizes the player hand size correctly,
         * and tests the get method for handsize*/
        assertEquals("handsize of regular game", gameRegular.getHandsize(), 3);
        /*tests that constructor for game of tsuro with input for rows and columns initilizes the player hand size correctly,
         * and tests the get method for handsize*/
        assertEquals("handsize of RC game", gameRC.getHandsize(), 3);
        /*tests that constructor for game of tsuro with input for rows, columns and player handsize
         * initilizes the player hand size correctly, and tests the get method for handsize*/
        assertEquals("handsize of RCH game", gameRCH.getHandsize(), 2);
        /*tests that constructor for game of tsuro with input for player, rows, columns and player handsize
         * initilizes the player hand size correctly, and tests the get method for handsize*/
        assertEquals("handsize of PRCH game", gamePRCH.getHandsize(), 1);
        /*tests that constructor for game of tsuro with input for player
         * initilizes the player hand size correctly, and tests the get method for handsize*/
        assertEquals("handsize of players game", gamePlayers.getHandsize(), 3);
    }



    //tests that the player's stone location has been correctly initilized and tests the get method for stone location
    public void testGetStoneLocation(){
        //tests the starting stone location of player 1
        assertEquals("player 1", gamePlayers.getPlayers()[0].getStoneLocation(), 2);
        //tests the starting stone location of player 2
        assertEquals("player 2", gamePlayers.getPlayers()[1].getStoneLocation(), 6);
        //tests the starting stone location of player 3
        assertEquals("player 3", gamePlayers.getPlayers()[2].getStoneLocation(), 5);
        //tests the starting stone location of player 4
        assertEquals("player 4", gamePlayers.getPlayers()[3].getStoneLocation(), 1);
        //tests the starting stone location of player 5
        assertEquals("player 5", gamePlayers.getPlayers()[4].getStoneLocation(), 2);
        //tests the starting stone location of player 6
        assertEquals("player 6", gamePlayers.getPlayers()[5].getStoneLocation(), 6);
        //tests the starting stone location of player 7
        assertEquals("player 7", gamePlayers.getPlayers()[6].getStoneLocation(), 5);
        //tests the starting stone location of player 8
        assertEquals("player 8", gamePlayers.getPlayers()[7].getStoneLocation(), 1);

    }


    //tests getter and setter methods for player's stone button location
    public void testSetAndGetStoneLocation(){
        gamePlayers.getPlayers()[0].setStoneButtonLocation(gamePlayers.getGrid()[1][2]);
        /*tests that the method getStoneButtonLocation is able to retrive the button the player's stone is on,
         * and that the method setStoneButtonLocation  is able to correctly set the location of the player's stone*/
        assertTrue(gamePlayers.getPlayers()[0].getStoneButtonLocation() == gamePlayers.getGrid()[1][2]);
    }

    //tests getter method, and if player numbers have been initilized correctly
    public void testGetPlayerNumber(){
        //tests that player 1's number has been initilized correctly and can be retrived (test first)
        assertEquals("player 1 number", gamePlayers.getPlayers()[0].getPlayerNumber(), 1);
        //tests that player 4's number has been initilized correctly and can be retrived (test middle)
        assertEquals("player 4 number ", gamePlayers.getPlayers()[3].getPlayerNumber(), 4);
        //tests that player 8's number has been initilized correctly and can be retrived (test last)
        assertEquals("player 8 number ", gamePlayers.getPlayers()[7].getPlayerNumber(), 8);

    }

    //tests if constructor initilized the color of stone for each player correctly and tests that it returns the color of the stones
    public void testGetStoneColor(){
        //tests if constructor initilized player 1's stone color correctly, and that the get method is able to return it
        assertTrue("player 1 color", gamePlayers.getPlayers()[0].getStoneColor().equals(Color.BLUE));
        //tests if constructor initilized player 2's stone color correctly, and that the get method is able to return it
        assertTrue("player 2 color", gamePlayers.getPlayers()[1].getStoneColor().equals(Color.RED));
        //tests if constructor initilized player 3's stone color correctly, and that the get method is able to return it
        assertTrue("player 3 color", gamePlayers.getPlayers()[2].getStoneColor().equals(Color.GREEN));
        //tests if constructor initilized player 4's stone color correctly, and that the get method is able to return it
        assertTrue("player 4 color", gamePlayers.getPlayers()[3].getStoneColor().equals(Color.ORANGE));
        //tests if constructor initilized player 5's stone color correctly, and that the get method is able to return it
        assertTrue("player 5 color", gamePlayers.getPlayers()[4].getStoneColor().equals(Color.YELLOW));
        //tests if constructor initilized player 6's stone color correctly, and that the get method is able to return it
        assertTrue("player 6 color", gamePlayers.getPlayers()[5].getStoneColor().equals(Color.MAGENTA));
        //tests if constructor initilized player 7's stone color correctly, and that the get method is able to return it
        assertTrue("player 7 color", gamePlayers.getPlayers()[6].getStoneColor().equals(Color.CYAN));
        //tests if constructor initilized player 8's stone color correctly, and that the get method is able to return it
        assertTrue("player 8 color", gamePlayers.getPlayers()[7].getStoneColor().equals(Color.GRAY));

    }

    //tests the getPlayers method
    public void testGetPlayers(){
        //tests that the array of players has been correctly formed, and can be retrived by the get method
        assertTrue(gamePlayers.getPlayers().length ==  8);
    }

    //tests rotating TsuroButtons
    public void testRotate(){
        TsuroButton b = new TsuroButton();
        int[] connections = {7, 2, 1, 4, 3, 6, 5, 0, 3};
        int[] expected = {2, 7, 0, 5, 6, 3, 4, 1};
        b.setConnections(connections);
        gamePlayers.rotate(b);
        //tests if rotate method correctly rotates the button
        assertTrue("test connections after rotate the same", compareArrays(expected, b.getConnections()));
    }

    //compares two int arrays
    public boolean compareArrays(int[] a1, int[] a2){
        boolean indicator = true;
        //checks if the array's length matches
        if(a1.length == a2.length){
            //loops through the arrays and checks that their contents are equal
            for(int i = 0; i < a1.length; i++){
                if(a1[i] != a2[i])
                    indicator = false;
            }
        }
        else
            indicator = false;
        return indicator;
    }

    //tests when the stone moves along a path of more than one tile
    public void testStoneMovingMany(){
        Tsuro boardMany = new Tsuro(2, 3);
        int[] a = {4, 5, 6, 7, 0, 1, 2, 3};
        int[] a1 = {3, 2, 1, 0, 7, 6, 5, 4};
        int[] a2 = {3, 2, 1, 0, 7, 6, 5, 4};
        boardMany.getGrid()[1][1].setConnections(a);
        boardMany.getGrid()[1][2].setConnections(a1);
        boardMany.getGrid()[2][2].setConnections(a2);
        boardMany.getPlayers()[0].setStoneButtonLocation(boardMany.getGrid()[1][1]);
        boardMany.moveStone();
        //tests that the players stone has moved to the correct new location on the board
        assertEquals( boardMany.getGrid()[2][2], boardMany.getCurrentPlayer().getStoneButtonLocation());
        //tests that the players stone has moved to the correct endpoint on the board
        assertEquals( 2, boardMany.getCurrentPlayer().getStoneLocation());
    }

    //test when the stone moves along a path of one tile
    public void testStoneMovingOne(){
        Tsuro boardOne = new Tsuro(2, 3);
        int[] a = {4, 5, 6, 7, 0, 1, 2, 3};
        int[] a1 = {3, 2, 1, 0, 7, 6, 5, 4};
        boardOne.getGrid()[1][1].setConnections(a);
        boardOne.getGrid()[1][2].setConnections(a1);
        boardOne.getPlayers()[0].setStoneButtonLocation(boardOne.getGrid()[1][1]);
        boardOne.moveStone();
        //tests that the players stone has moved to the correct new location on the board
        assertEquals( boardOne.getGrid()[1][2], boardOne.getCurrentPlayer().getStoneButtonLocation());
        //tests that the players stone has moved to the correct endpoint on the board
        assertEquals( 5, boardOne.getCurrentPlayer().getStoneLocation());
    }

    //test when the stone is put down and should not move
    public void testStoneMovingZero(){
        Tsuro boardZero = new Tsuro(2, 3);
        int[] a = {4, 5, 6, 7, 0, 1, 2, 3};
        boardZero.getGrid()[1][1].setConnections(a);
        boardZero.getPlayers()[0].setStoneButtonLocation(boardZero.getGrid()[1][1]);
        boardZero.moveStone();
        //tests that the tile location of the players stone has not moved
        assertEquals(boardZero.getGrid()[1][1], boardZero.getCurrentPlayer().getStoneButtonLocation());
        //tests that the players stone has not moved from its endpoint on the board
        assertEquals( 2, boardZero.getCurrentPlayer().getStoneLocation());

    }

    //test when the stone runns into a wall
    public void testStoneWallCrash(){
        Tsuro boardCrash = new Tsuro(2, 2);
        int[] a = {4, 5, 6, 7, 0, 1, 2, 3};
        boardCrash.getGrid()[1][1].setConnections(a);
        boardCrash.getGrid()[1][2].setConnections(a);
        boardCrash.getPlayers()[0].setStoneButtonLocation(boardCrash.getGrid()[1][1]);
        boardCrash.moveStone();
        //tests that the players stone has moved to the correct new location on the board
        assertEquals( boardCrash.getGrid()[1][2], boardCrash.getCurrentPlayer().getStoneButtonLocation());
        //tests that the players stone has moved to the correct endpoint on the board
        assertEquals( 2, boardCrash.getCurrentPlayer().getStoneLocation());
        //checks that player has been set to be not in the game anymore
        assertTrue(boardCrash.getPlayers()[0].isDead());
    }



}

