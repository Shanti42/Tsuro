package tsuro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Creates and allows users to play a game of Tsuro
 * @author Shanti Polara
 */
public class Tsuro extends JFrame implements ActionListener{

    //holds the number of rows of the board grid
    private int row;
    //holds the number of columns of the board grid
    private int column;
    //holds the number of tiles in each player's hands
    private int handsize;
    //holds the number of players playing the game
    private int numberPlayer;
    //holds the set of players playing the game
    private Tsuro.Player[] players;
    //the JPanel that holds the formated board
    private JPanel gameBoard;
    //holds the button that was clicked on the Tsuro board
    private TsuroButton currentBoardButton = null;
    //holds the button that is currently highlighted in the player's hand
    private TsuroButton currentPlayerButton = null;
    //holds the array of TsuroButtons that make up the board grid
    private TsuroButton[][] grid;
    //holds the Player whoose turn it is
    private Tsuro.Player currentPlayer = null;

    /**
     * Creates a game with a 6 X 6 game board, two players, each with a hand of 3 tiles
     */
    public Tsuro(){
        this(2, 6, 6, 3);
    }

    /**
     * Creates a game with a row X column game board, two players, each with a hand of 3 tiles
     * @param row the number of rows the game board will have
     * @param column the number of columns the game board will have
     */
    public Tsuro(int row, int column){
        this(2, row, column, 3);
    }

    /**
     * Creates a game with a row X column game board, two players, each with a hand of handsize tiles
     * @param row the number of rows the game board will have
     * @param column the number of columns the game board will have
     * @param handsize the number of tiles in each player's hand
     */
    public Tsuro(int row, int column, int handsize){
        this(2, row, column, handsize);
    }

    /**
     * Creates a game with a 6 X 6 game board, numberPlayer players, each with a hand of 3 tiles
     * @param numberPlayer the number of players who are playing the game
     */
    public Tsuro(int numberPlayer){
        this(numberPlayer, 6, 6, 3);
    }

    /**
     * Creates a game with a row X column game board, numberPlayer players, each with a hand of handsize tiles
     * @param numberPlayer the number of players who are playing the game
     * @param row the number of rows the game board will have
     * @param column the number of columns the game board will have
     * @param handsize the number of tiles in each player's hand
     */
    public Tsuro(int numberPlayer, int row, int column, int handsize){
        //if input is greater than zero, sets the input as row, otherwise, sets row to 6
        if (row > 0)
            this.row = row;
        else{
            row = 6;
            this.row = row;
        }
        //if input is greater than zero, set the input as column, otherwise, sets column as 6
        if (column > 0)
            this.column = column;
        else{
            column = 6;
            this.column = column;
        }
        //if handsize is greater than zero, sets the input as handsize, otherwise, sets handsize to 1
        if(handsize > 0)
            this.handsize = handsize;
        else{
            handsize = 1;
            this.handsize = handsize;
        }
        //if input for number of players is greater than 8, sets number of players to 8
        if (numberPlayer > 8)
            this.numberPlayer = 8;
        else{
            this.numberPlayer = numberPlayer;
            this.players = new Player[numberPlayer];
        }
        //initilizes the game board with a layout of row X column
        gameBoard = new JPanel(new GridLayout(row, column));
        //creates a grid of TsuroButtons
        grid = new TsuroButton[row + 1][column + 1];

        Container c = this.getContentPane();
        c.add(gameBoard, "Center");
        //sizes the frame holding the game board by the row X column, scaled by 110 pixels
        this.setSize(column * 110, row * 110);
        this.setLocation(600, 100);


        for (int num = 0; num < (this.getNumberPlayers()); num++){
            players[num] = new Player(num + 1);
        }
        setCurrentPlayer(players[0]);

        /* initilizes the array of TsuroButtons
         * Goal: create a board of blank TsuroButtons, with this as their action listener
         */
        for (int i = 1; i < row + 1; i++){
            //iterates through the TsuroButtons in each row of the array and intitilizes them
            for(int j = 1; j < column + 1 ; j++){
                grid[i][j] = new TsuroButton();
                grid[i][j].setConnections(null);
                grid[i][j].addActionListener(this);
                gameBoard.add(grid[i][j]);
            }
        }
        this.setTitle("Tsuro");
        this.setVisible(true);

        //disables the look-and-feel for apple computers
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e){
        }
    }

    /**
     * Invoked when action on a TsuroButton in the game board occurs
     * @param e holds information about the TsuroButton on the board that was clicked
     */
    @Override
    public void actionPerformed(ActionEvent e){
        TsuroButton b = (TsuroButton)e.getSource();
        setCurrentBoardButton(b);
        //checks if the current board button is legal, and that the current player's button is not null
        if (this.isLegal() && getCurrentPlayer().getCurrentPlayerButton() != null){
            //sets the connections of the current board button to the connections of the desired button in the players hand
            b.setConnections(getCurrentPlayer().getCurrentPlayerButton().getConnections());
            getCurrentPlayer().getCurrentPlayerButton().setBackground(Color.WHITE);
            getCurrentPlayer().getCurrentPlayerButton().setConnections(TsuroButton.makeRandomConnectArray());
            //true if it is not the current player's first move
            if (getCurrentPlayer().getMove() == 0){
                //sets the stone button location to the button on the board
                getCurrentPlayer().setStoneButtonLocation(b);
                b.addStone(getCurrentPlayer().getStoneColor(), getCurrentPlayer().getStoneLocation());
                getCurrentPlayer().removeStoneFromHand();
                getCurrentPlayer().addStoneToHand();
                //increments the number of moves the player has done
                getCurrentPlayer().setMove(getCurrentPlayer().getMove() + 1);
            }
            else{
                //increments the number of moves the player has done
                getCurrentPlayer().setMove(getCurrentPlayer().getMove() + 1);
                //removes stone from it's previous location
                getCurrentPlayer().getStoneButtonLocation().removeStone(getCurrentPlayer().getStoneLocation());
                //removes the stones from the players hand, so that they can be moved to the new correct spot
                getCurrentPlayer().removeStoneFromHand();
                //moves the players stone to the correct location
                moveStone();
                //adds back the stones on the player's hand in the correct location
                getCurrentPlayer().addStoneToHand();
                //moves the other player's stones which are affected by the player placing a stone
                moveOtherStones();
            }
            //adds a stone to the button in the correct space
            //b.addStone(getCurrentPlayer().getStoneColor(), getCurrentPlayer().getStoneLocation());
            //increments to the next player
            if (getCurrentPlayer().getPlayerNumber() < getNumberPlayers()){
                adjustPlayers();
                //setCurrentPlayer(getPlayers()[getCurrentPlayer().getPlayerNumber()]);
                setCurrentPlayer(adjustPlayers());
            }
            else
                setCurrentPlayer(getPlayers()[0]);
        }
    }

    /**
     * determines if there are no more moves in the game
     * @return true if there are no more moves in the game
     */
    public boolean isMoreMoves() {
        int counter = 0;
        //increments counter for each player that is not in the game
        for (Player player: this.getPlayers()){
            if(player.isDead()){
                counter++;
            }
        }
        return (counter != getNumberPlayers());
    }

    /**
     * determines if the players have crashed
     */
    public void determineCrash(){
        //iterates through the player
        for(Tsuro.Player player: getPlayers())
            //checks if the players have crashed
            if (isCrash(player))
                //sets the player that crashed to dead
                player.setDead(true);
    }

    /**
     * checks if a player has crashed into any of the other players in the game
     * @param playerCheck the player which is being checked if it has crashed
     * @return true if the player has crashed into another player
     */
    public boolean isCrash(Tsuro.Player playerCheck){
        //gets the endpoint the player's stone is on
        int stoneLocation = playerCheck.getStoneLocation();
        int playerStoneLocation = 10;
        //gets the board tile the player's stone is currently on
        TsuroButton button = playerCheck.getStoneButtonLocation();
        Tsuro.Player playerCrash = null;
        //gets the I value of the board tile the player's stone is currently on
        int playerCheckI = getIValue(playerCheck.getStoneButtonLocation());
        //gets the J value of the board tile the player's stone is currently on
        int playerCheckJ = getJValue(playerCheck.getStoneButtonLocation());
        int playerI = 10;
        int playerJ = 10;
        //iterates through the players playing the game
        for(Tsuro.Player player: getPlayers()){
            //gets the endpoint location on the tile of the player's stone
            playerStoneLocation = player.getStoneLocation();
            //gets the i value of board tile the player's stone is on
            playerI = getIValue(player.getStoneButtonLocation());
            //gets the j value of the board tile the player's stone is on
            playerJ = getJValue(player.getStoneButtonLocation());
            //determines if the player is crashed based on the endpoint it is located on, and the location of the other player;s
            switch(stoneLocation){
                case 0:
                    if(playerStoneLocation == 4 && playerCheckI == (playerI - 1) && playerCheckJ == playerJ){
                        playerCrash = player;
                    }
                    break;
                case 1:
                    if(playerStoneLocation == 5 && playerCheckI == (playerI - 1) && playerCheckJ == playerJ){
                        playerCrash = player;
                    }
                    break;
                case 2:
                    if(playerStoneLocation == 6 && playerCheckI == playerI && playerCheckJ == playerJ + 1){
                        playerCrash = player;
                    }
                    break;
                case 3:
                    if(playerStoneLocation == 7 && playerCheckI == playerI && playerCheckJ == playerJ + 1){
                        playerCrash = player;
                    }
                    break;
                case 4:
                    if(playerStoneLocation == 0 && playerCheckI == playerI + 1 && playerCheckJ == playerJ){
                        playerCrash = player;
                    }
                    break;
                case 5:
                    if(playerStoneLocation == 1 && playerCheckI == playerI + 1 && playerCheckJ == playerJ){
                        playerCrash = player;
                    }
                    break;
                case 6:
                    if(playerStoneLocation == 2 && playerCheckI == playerI && playerCheckJ == playerJ - 1){
                        playerCrash = player;
                    }
                    break;
                case 7:
                    if(playerStoneLocation == 3 && playerCheckI == playerI && playerCheckJ == playerJ - 1){
                        playerCrash = player;
                    }
                    break;
            }
        }
        return (playerCrash != null);

    }

    /**
     * iterates to the next player who is not out of the game
     * @return the player whose turn it is
     */
    public Tsuro.Player adjustPlayers(){
        int i = getCurrentPlayer().getPlayerNumber();
        Tsuro.Player nextPlayer = null;
        //finds the next player who is not out of the game
        while(nextPlayer == null && isMoreMoves()){
            if (!getPlayers()[i].isDead())
                nextPlayer = getPlayers()[i];
            i++;
        }
        return nextPlayer;
    }


    /**
     * finds the column the given TsuroButton is located in the game board grid
     * @param button the TsuroButton whose row in the grid is being found
     * @return the column location of the button in the game board grid
     */
    public int getJValue(TsuroButton button){
        //holds the row location of the button
        int holdJ = 0;
        if (button != null){
            //iterates through the columns of the game board
            for (int i = 1; i < getRow() + 1; i++){
                //iterates through the rows of the game board
                for(int j = 1; j < getColumn() + 1 ; j++){
                    //sets the row value when the button's row and column values are found in the grid
                    if (getGrid()[i][j] == button){
                        holdJ = j;
                    }
                }
            }
        }
        return holdJ;
    }

    /**
     * @param button the TsuroButton whose row location in the board grid is being found
     * @return int the row number the button is located on the board grid
     */
    public int getIValue(TsuroButton button){
        //holds the column location of the button
        int holdI = 0;
        if (button != null){
            //iterates through the columns of the board grid
            for (int i = 1; i < this.getRow() + 1; i++){
                //iterates through the rows of the board grid
                for(int j = 1; j < this.getColumn() + 1 ; j++){
                    //sets the column number when the button's rows and column values are found in the grid
                    if (getGrid()[i][j] == button){
                        holdI = i;
                    }
                }
            }
        }
        return holdI;
    }

    /**
     * determines if a spot on the game board is legal for a player to place a tile on
     * @return true if the desired space on the game board is legal to place the player's tile
     */
    public boolean isLegal(){
        //holds the column value of the desired spot on the board
        int holdI = getIValue(getCurrentBoardButton());
        //holds the row value of the desired spot on the board
        int holdJ = getJValue(getCurrentBoardButton());
        //holds if the action is legal
        boolean indicator = false;
        //checks if the desired spot on the board is empty
        if (getCurrentBoardButton().getConnections() == null && getCurrentPlayer() != null ){
            //checks if it is the player's first move
            if ( getCurrentPlayer().getMove() == 0 ){
                //determines if the desired spot on the board is legal for that player based on their number
                switch (getCurrentPlayer().getPlayerNumber()){
                    case 1:
                    case 5:
                        indicator = (holdJ == 1);
                        break;
                    case 2:
                    case 6:
                        indicator = (holdJ == this.getColumn());
                        break;
                    case 3:
                    case 7:
                        indicator = (holdI == 1);
                        break;
                    case 4:
                    case 8:
                        indicator = (holdI == this.getRow());
                        break;
                }
            }

            //if the desired spot on the board is empty and it is not the player's first move
            else{
                //holds the column value of the button the player's stone is currently on
                int iStone = getIValue(getCurrentPlayer().getStoneButtonLocation());
                //holds the row value of the button the player's stone is currently on
                int jStone = getJValue(getCurrentPlayer().getStoneButtonLocation());
                //determines if the desired spot on the board is legal based on where the player's stone is located on it's tile
                switch(getCurrentPlayer().getStoneLocation()){
                    case 0:
                    case 1:
                        indicator = (jStone == holdJ && iStone - 1 == holdI);
                        break;
                    case 2:
                    case 3:
                        indicator = (jStone + 1== holdJ && iStone == holdI);
                        break;
                    case 4:
                    case 5:
                        indicator  = (iStone + 1 == holdI && jStone == holdJ);
                        break;
                    case 6:
                    case 7:
                        indicator = (iStone == holdI && jStone - 1 == holdJ);
                        break;
                }
            }
        }
        return indicator;
    }

    /**
     * determines if a player's stone has crashed into a wall
     * @param player the player whose stone is being checked to see if it is against a wall
     * @return boolean true if the player's stone is against a wall
     */
    public boolean isWallCrash(Player player){
        int iButton = getIValue(player.getStoneButtonLocation());
        int jButton = getJValue(player.getStoneButtonLocation());
        //determines if the player's stone has crashed into a wall based on the location of the player's stone on the tile
        switch (player.getStoneLocation()){
            case 0:
            case 1:
                getCurrentPlayer().setDead(iButton == 1);
                break;
            case 2:
            case 3:
                getCurrentPlayer().setDead(jButton == getColumn());
                break;
            case 4:
            case 5:
                getCurrentPlayer().setDead(iButton == getRow());
                break;
            case 6:
            case 7:
                getCurrentPlayer().setDead(jButton == 1);
                break;
        }
        return getCurrentPlayer().isDead();
    }


    /**
     * helper method for rotate, which rotates the connections of the new endpoints
     * @param the connection which must be rotated
     * @return the rotated connection
     */
    private Integer connection(int connect){
        Integer newConnect = null;
        //finds the new connection based on the value of the input connection
        switch (connect){
            case 0:
                newConnect = 2;
                break;
            case 1:
                newConnect = 3;
                break;
            case 2:
                newConnect = 5;
                break;
            case 3:
                newConnect = 4;
                break;
            case 4:
                newConnect = 6;
                break;
            case 5:
                newConnect = 7;
                break;
            case 6:
                newConnect = 1;
                break;
            case 7:
                newConnect = 0;
                break;
        }
        return newConnect;
    }

    /**
     * rotates the TsuroButton to the right
     * @param button the TsuroButton being rotated
     */
    public void rotate(TsuroButton button){
        int[] connections = button.getConnections();
        int[] newConnections = new int[8];
        /*the following lines rotate the connections of the old array and set them as
         *connections of the new array*/
        newConnections[0] = connection(connections[7]);
        newConnections[1] = connection(connections[6]);
        newConnections[2] = connection(connections[0]);
        newConnections[3] = connection(connections[1]);
        newConnections[4] = connection(connections[3]);
        newConnections[5] = connection(connections[2]);
        newConnections[6] = connection(connections[4]);
        newConnections[7] = connection(connections[5]);
        //clears the old connections of the button
        button.setConnections(null);
        //adds the new rotated connections to the button
        button.setConnections(newConnections);
    }

    /**
     * moves the player's stone along the connected path
     */
    public void moveStone(){
        //while the next tile is not null, and the player has not hit a wall, moves the player's stone
        while(isNext()){
            moveNext();
        }
    }

    /**
     * moves the other player's stones which are affected by the player placing a tile
     */
    public void moveOtherStones(){
        Tsuro.Player currentPlayer = getCurrentPlayer();
        //iterates through the array of players, and moves their stones if they need to be moved
        for(Tsuro.Player player: getPlayers()){
            setCurrentPlayer(player);
            player.removeStoneFromHand();
            moveStone();
            player.addStoneToHand();
        }
        setCurrentPlayer(currentPlayer);
    }

    /**
     * determines if the player can move to the next tile on the board
     * @return true if the player can move to the next tile on the board
     */
    public boolean isNext(){
        TsuroButton buttonCurrent = getCurrentPlayer().getStoneButtonLocation();
        //gets the i value of the button the player's stone is on
        int iCurrent = getIValue(buttonCurrent);
        //gets the j value of the button the player's stone is on
        int jCurrent = getJValue(buttonCurrent);
        TsuroButton nextButton = null;
        //checks if the player has crashed into he wall
        if (isWallCrash(getCurrentPlayer()) == false){
            switch(getCurrentPlayer().getStoneLocation()){
                case 0:
                case 1:
                    nextButton = getGrid()[iCurrent - 1][jCurrent];
                    break;
                case 2:
                case 3:
                    nextButton = getGrid()[iCurrent][jCurrent + 1];
                    break;
                case 4:
                case 5:
                    nextButton = getGrid()[iCurrent + 1][jCurrent];
                    break;
                case 6:
                case 7:
                    nextButton = getGrid()[iCurrent][jCurrent - 1];
                    break;
            }
            return (!(nextButton.getConnections() == null));
        }
        else return false;
    }


    /**
     * moves the current player's stone along the connections of a button on the board grid
     */
    public void moveNext(){
        int iCurrent = getIValue(getCurrentPlayer().getStoneButtonLocation());
        int jCurrent = getJValue(getCurrentPlayer().getStoneButtonLocation());
        //finds the next button for the player's stone to move along
        TsuroButton nextButton = findNextButton();
        //checks if there is no more buttons for the player's stone to move along
        if (nextButton != null){
            int iNext = getIValue(nextButton);
            int jNext = getJValue(nextButton);
            //gets the endpoint location of the player's stone on the board
            int location = getCurrentPlayer().getStoneLocation();
            //checks if is not the player's first move
            if (getCurrentPlayer().getMove() > 0){
                //gets the connections of the tile the current player's stone is on
                int[] connections = getCurrentPlayer().getStoneButtonLocation().getConnections();
                //moves the player's stone along the adjacent button depending on the endpoint it is currently on
                switch(location){
                    case 0:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[4]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[4]));
                        break;
                    case 1:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[5]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[5]));
                        break;
                    case 2:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[6]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[6]));
                        break;
                    case 3:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[7]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[7]));
                        break;
                    case 4:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[0]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[0]));
                        break;
                    case 5:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[1]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[1]));
                        break;
                    case 6:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[2]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[2]));
                        break;
                    case 7:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[3]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[3]));
                        break;
                }
            }
            //if it is the player's first move
            else {
                //sets the location of the player's button depending on thier endpoint location
                switch(getCurrentPlayer().getStoneLocation()){
                    case 0:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[4]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[4]));
                        break;
                    case 1:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[5]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[5]));
                        break;
                    case 2:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[6]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[6]));
                        break;
                    case 3:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[7]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[7]));
                        break;
                    case 4:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[0]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[0]));
                        break;
                    case 5:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[1]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[1]));
                        break;
                    case 6:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[2]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[2]));
                        break;
                    case 7:
                        nextButton.addStone(getCurrentPlayer().getStoneColor(), nextButton.getConnections()[3]);
                        getCurrentPlayer().setStoneLocation((nextButton.getConnections()[3]));
                        break;
                }
            }
            //sets the new stone button location
            getCurrentPlayer().setStoneButtonLocation(nextButton);
            determineCrash();
        }
    }


    /**
     * finds the next button the players stone should move to
     * @return button the player's stone should move to
     */
    public TsuroButton findNextButton(){
        //gets the endpoint location of the current player's stone
        int stoneLocation = getCurrentPlayer().getStoneLocation();
        //gets the button the player's stone is on
        TsuroButton buttonCurrent = getCurrentPlayer().getStoneButtonLocation();
        //gets the i value of the button the player's stone is on
        int iCurrent = getIValue(buttonCurrent);
        //gets the j value of the button the player's stone is on
        int jCurrent = getJValue(buttonCurrent);
        TsuroButton nextButton = null;
        //checks if the player has not crashed into the edge of the board
        if (isWallCrash(getCurrentPlayer()) == false){
            buttonCurrent.removeStone(stoneLocation);
            //find the next button the player's stone should move to
            switch(stoneLocation){
                case 0:
                case 1:
                    nextButton = getGrid()[iCurrent - 1][jCurrent];
                    break;
                case 2:
                case 3:
                    nextButton = getGrid()[iCurrent][jCurrent + 1];
                    break;
                case 4:
                case 5:
                    nextButton = getGrid()[iCurrent + 1][jCurrent];
                    break;
                case 6:
                case 7:
                    nextButton = getGrid()[iCurrent][jCurrent - 1];
                    break;
            }
        }
        return nextButton;
    }



    /**
     * returns the array of players playing the game
     * @return the array of players playing the game
     */
    public Player[] getPlayers(){
        return players;
    }

    /**
     * sets a new array of players playing the game
     * @param players the new array of players playing the game
     */
    public void setPlayers(Tsuro.Player[] players){
        this.players = players;
    }

    /**
     * returns the Player whose turn it is
     * @return the Player whose turn it is to play
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * sets the player whose turn it is to play the game
     * @param player the player whose turn it is to play the game
     */
    public void setCurrentPlayer(Tsuro.Player player){
        this.currentPlayer = player;
    }

    /**
     * retrives the JPanel which aranges buttons of the board
     * @return the JPanel which aranges the buttons of the board
     */
    public JPanel getGameBoard(){
        return gameBoard;
    }

    /**
     * retrives the grid of TsuroButtons which make up the game board
     * @return the grid of TsuroButtons which the the game board is made of
     */
    public TsuroButton[][] getGrid(){
        return grid;
    }

    /**
     * places the tile the player has clicked onto the place on the board they click on
     * @param player the player who is setting the tile on the board
     */
    private void PlaceButton(Player player){
        this.setCurrentBoardButton(player.getCurrentPlayerButton());
    }

    /**
     * places a tile from the player's hand onto a selected spot on the board
     * @ param playerTile the player's tile being placed
     * @ param boardTile the location on the board the player's tile is being placed on
     */
    private void placeTile(TsuroButton playerTile, TsuroButton boardTile){
        boardTile.setConnections(playerTile.getConnections());
    }

    /**
     * retrives the button which has been most recently clicked on the game board
     * @return the TsuroButton on the game board which has been most recently clicked
     */
    public TsuroButton getCurrentBoardButton(){
        return currentBoardButton;
    }

    /**
     * sets the current button which has been most recently clicked on the board
     * @param currentClick the button which has been most recently clicked on the board
     */
    private void setCurrentBoardButton(TsuroButton currentClick){
        currentBoardButton = currentClick;
    }

    /**
     * retrives the number of rows of the game board
     * @return the number of rows of the game board
     */
    public int getRow(){
        return row;
    }

    /**
     * retrives the number of columns of the game board
     * @return the number of columns of the game board
     */
    public int getColumn(){
        return column;
    }

    /**
     * retrives the number of tiles in each player's hand
     * @return the number of tiles in each player's hand
     */
    public int getHandsize(){
        return handsize;
    }

    /**
     * retrives the number of players playing the game
     * @return the number of players playing the game
     */
    public int getNumberPlayers(){
        return numberPlayer;
    }

    /**
     * Creates a player which can play a game of Tsuro
     */
    public class Player extends JFrame implements ActionListener{
        //the JPanel holding the players hand of TsuroButtons
        private JPanel pannelPlayer;
        //holds the number the player is
        private int playerNumber;
        //holds the array of TsuroButtons that make up the player's hand
        private TsuroButton[] hand;
        //holds the location of the player's stone
        private int stoneLocation;
        //holds the color of the player's stone
        private Color stoneColor;
        private int move = 0;
        private TsuroButton stoneButtonLocation = null;
        private boolean deadIndicator = false;


        /**
         * sets up a player with a hand of TsuroButtons, intilizes the player's stone location and stone color
         * @param playerNumber the number the player is
         */
        public Player(int playerNumber){
            this.playerNumber = playerNumber;
            pannelPlayer = new JPanel(new GridLayout(1, Tsuro.this.getHandsize()));
            Container container = Player.this.getContentPane();
            container.add(pannelPlayer, "Center");
            hand = new TsuroButton[handsize + 1];
            Player.this.setSize(80 * Tsuro.this.getHandsize(), 130);
            this.setTitle("Player " + playerNumber);

            //initilizes the player's stone location and stone color
            switch (playerNumber){
                case 1:
                    stoneLocation = 2;
                    stoneColor = Color.BLUE;
                    break;
                case 2:
                    stoneLocation = 6;
                    stoneColor = Color.RED;
                    break;
                case 3:
                    stoneLocation = 5;
                    stoneColor = Color.GREEN;
                    break;
                case 4:
                    stoneLocation = 1;
                    stoneColor = Color.ORANGE;
                    break;
                case 5:
                    stoneLocation = 2;
                    stoneColor = Color.YELLOW;
                    break;
                case 6:
                    stoneLocation = 6;
                    stoneColor = Color.MAGENTA;
                    break;
                case 7:
                    stoneLocation = 5;
                    stoneColor = Color.CYAN;
                    break;
                case 8:
                    stoneLocation = 1;
                    stoneColor = Color.GRAY;
                    break;
            }

            /*
             * initilizes the TsuroButtons in the players hand
             */
            for (int i = 1; i < handsize + 1; i++){
                hand[i] = new TsuroButton();
                hand[i].setConnections(hand[i].makeRandomConnectArray());
                hand[i].addStone(stoneColor, stoneLocation);
                hand[i].addActionListener(Player.this);
                pannelPlayer.add(hand[i]);
            }

            //if the player's number is less than or equal to 4, sets the players hand to the left of the game board
            if (playerNumber <= 4)
                Player.this.setLocation(500 - (Tsuro.this.getHandsize() * 80), 100 + ((playerNumber - 1) * 140));
            //if the player's number is greater than 4, sets the players hand to the right of the game board
            if (playerNumber > 4)
                Player.this.setLocation( 630 + Tsuro.this.getColumn()* 110, 100 + ((playerNumber - 5) * 140));
            Player.this.setVisible(true);
            Player.this.pack();
        }

        /**
         * removes the stone from all of the tiles in the player's hand
         */
        private void removeStoneFromHand(){
            for (int i = 1; i < Tsuro.this.getHandsize() + 1; i++){
                for(int j = 0; j < 8; j++){
                    getHand()[i].removeStone(j);
                }
            }
        }

        /**
         * adds a stone to all of the tiles in the players hand matching the endpoint location of the player's stone on the board
         */
        private void addStoneToHand(){
            int location = 0;
            //finds the location of the player's stone on the board
            switch(getStoneLocation()){
                case 0:
                    location = 4;
                    break;
                case 1:
                    location = 5;
                    break;
                case 2:
                    location = 6;
                    break;
                case 3:
                    location = 7;
                    break;
                case 4:
                    location = 0;
                    break;
                case 5:
                    location = 1;
                    break;
                case 6:
                    location = 2;
                    break;
                case 7:
                    location = 3;
                    break;
            }
            //adds the stone in the correct location to all of the tiles in the player's hand
            for (int i = 1; i < Tsuro.this.getHandsize() + 1; i++)
                getHand()[i].addStone(getStoneColor(), location);
        }

        /**
         * Invoked when a TsuroButton in the player's hand is clicked
         * @param e holds information about a TsuroButton in the player's hand being clicked
         */
        public void actionPerformed(ActionEvent e){
            TsuroButton b = (TsuroButton)e.getSource();
            //iterates through each TsuroButton in the player's hand
            if (Player.this == Tsuro.this.getCurrentPlayer()){
                //is true if the button has not been pressed yet, and no button in the players hand has been pressed
                if (b.getBackground() == Color.WHITE && getCurrentPlayerButton() == null){
                    b.setBackground(Color.lightGray);
                    Player.this.setCurrentPlayerButton(b);
                }
                //if the button has been pressed before, rotates the button
                else if(b.getBackground() == Color.lightGray){
                    rotate(b);
                }
                //if the button has not been pressed but another button is currently highlighted
                else{
                    Player.this.removeHighlight();
                    b.setBackground(Color.lightGray);
                    Player.this.setCurrentPlayerButton(b);
                }
            }
        }

        /**
         * retrives the TsuroButton in the player's hand that has been most recently clicked
         * @return currentPlayerButton the button in the player's hand that has been most recently clicked
         */
        public TsuroButton getCurrentPlayerButton(){
            return currentPlayerButton;
        }

        /**
         * retrives the button on the game board the player's stone is on
         * @return stoneButtonLocation the button on the game board the player's button is on
         */
        public TsuroButton getStoneButtonLocation(){
            return stoneButtonLocation;
        }

        /**
         * sets the button on the board game the player's stone is on
         * @param button the current button on the board the player's stone is on
         */
        public void setStoneButtonLocation(TsuroButton button){
            Player.this.stoneButtonLocation = button;
        }

        /**
         * sets the location of the stone on the button it is on
         * @param stoneLocation the location the player's stone is on the button
         */
        public void setStoneLocation(int stoneLocation){
            Player.this.stoneLocation = stoneLocation;
        }

        /**
         * sets the TsuroButton which the player has most recently clicked
         * @param button the TsuroButton which has been most recently clicked by the player
         */
        public void setCurrentPlayerButton(TsuroButton button){
            currentPlayerButton = button;
        }


        /**
         * retrives the location of the players stone
         * @return the location of the player's stone
         */
        public int getStoneLocation(){
            return stoneLocation;
        }

        /**
         * retrives the color of the player's stone
         * @return the color of the player's stone
         */
        public Color getStoneColor(){
            return stoneColor;
        }


        /**
         * retrives the player's number
         * @return the player's number
         */
        public int getPlayerNumber(){
            return playerNumber;
        }

        /**
         * retrives the grid of TsuroButtons which make up the player's hand
         * @return the grid of TsuroButtons which make up the player's hand
         */
        public TsuroButton[] getHand(){
            return hand;
        }

        /**
         * removes the highlight the TsuroButton most recently clicked
         */
        private void removeHighlight(){
            if (Player.this.getCurrentPlayerButton() != null)
                Player.this.getCurrentPlayerButton().setBackground(Color.WHITE);
        }

        /**
         * retrives the number of moves a player has done
         * @return the number of moves a player has done
         */
        public int getMove(){
            return move;
        }

        /**
         * sets the number of moves a player has done
         * @param move number of moves a player has done
         */
        private void setMove(int move){
            Player.this.move = move;
        }

        /**
         * sets the the indicator that tells if the player is out of the game
         * @param deadIndicator the input that tells whether the player is out of the game
         */
        public void setDead(boolean deadIndicator){
            this.deadIndicator = deadIndicator;
        }


        /**
         * checks if the player has lost the game
         * @return true if player's stone has crashed into a wall or another player
         */
        public boolean isDead(){
            return deadIndicator;
        }

    }


    /**
     * the main method which runs the program
     * @param args the user input
     */
    public static void main(String[] args){
        try{
            // initilizes a game of tsuro with no input
            if(args.length == 0){
                Tsuro game = new Tsuro();
            }
            //initilizes a game of tsuro with input for the number of players
            else if(args.length == 1){
                Tsuro game = new Tsuro(Integer.parseInt(args[0]));
            }
            //initilizes a game of tsuro with input for the rows and columb sizes of the board
            else if(args.length == 2){
                Tsuro game = new Tsuro(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            }
            //initilizes a game of tsuro with input for the row and columb size of the board and hand size of the players
            else if(args.length == 3){
                Tsuro game = new Tsuro(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            }
            //initilizes a game of tsuro with input for the number of players, the row and column size of the board and handsize of the players
            else if(args.length == 4){
                Tsuro game = new Tsuro(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            }
            else
                System.out.println("Input does not match legal inputs, please try again");
        }
        catch (NumberFormatException e){
            System.out.println("Input does not match legal inputs, please try again");
        }

    }
}



