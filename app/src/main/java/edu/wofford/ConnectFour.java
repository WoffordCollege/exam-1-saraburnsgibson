/*
 Sara Burns Gibson
 Exam 1 
 COSC 410
 */
package edu.wofford;

/**
 * This class provides implements a game board for Connect Four.
 * In this game, the RED player always plays the first token.
 * A Connect Four game board has 6 rows and 7 columns:
 * 
 *  | | | | | | | |
 *  | | | | | | | |
 *  | | | | | | | |
 *  | | | |B| | | |
 *  |B| |B|R| | | |
 *  |R|B|B|R| |R|R|
 *  ---------------
 * 
 * This diagram also illustrates how the board should be printed
 * to the screen (via the toString method).
 * 
 * The first player (RED or BLACK) to get four tokens in a row,
 * either horizontally, vertically, or diagonally, wins the game.
 * It is possible for neither player to win (a TIE) if the board
 * is filled with no winning condition. If the game is still
 * ongoing, the result should be NONE.
 */
 
public class ConnectFour {

    public enum Location {EMPTY, RED, BLACK};
    public enum Result {NONE, TIE, BLACKWIN, REDWIN};
    protected Location[][] board;
    protected boolean redTurn;
    
    /**
     * This is a convenience method that you might find useful.
     * It returns the specified column's entries as a string.
     * For instance, suppose that you had the following board:
     * 
     * | | | | | | | |
     * | | | | | | | |
     * | | | | | | | |
     * | |B| | |R| | |
     * | |B|R| |R|R|B|
     * |B|R|B| |R|B|R|
     * ---------------
     * 
     * Calling this function on column 1 would return "RBB". On
     * column 5 it would return "BR". (It starts from the "bottom" 
     * of the column.)
     * 
     * @param column the column to convert
     * @return a string representing that column's contents
     */
    private String getColumnAsString(int column) {
        if(column >= 0 && column < board[0].length) {
            String s = "";
            int row = board.length - 1;
            while(row >= 0 && board[row][column] != Location.EMPTY) {
                s += (board[row][column] == Location.RED)? "R" : "B";
                row--;
            }
            return s;
        }
        else {
            return "";
        }
    }
    
    /**
     * This constructor creates a completely empty board and
     * sets the current player to red.
     */
    public ConnectFour() {
        board = new Location[6][7];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Location.EMPTY;
            }
        }
        redTurn = true;
    }

    // These two are only needed by the unit tests for verification purposes.
    // If you change them, you will fail the tests, so
    // DO NOT CHANGE THEM.
    public Location getLocation(int row, int col) {
        return board[row][col];
    }
    
    public void setLocation(int row, int col, Location loc) {
        board[row][col] = loc;
    }
    




    

    //===========================================================
    //                          TO DO
    //===========================================================


    /**
     * This method returns the token type on the top of the 
     * specified column. If the column is empty, it should
     * return EMPTY. Otherwise, it should return either
     * RED or BLACK.
     * 
     * @param column the column to check
     * @return the top of the column
     */
    public Location getTopOfColumn(int column) {
        /*
        //Question 1
        I need to return the top token type for a given column. If it's empty, return EMPTY
        Loop from the top to bottom. If I find a token- return type
        If the loop ends without finding a token, return empt
        */
            for (int row = 0; row < board.length; row++) {
                if (board[row][column] != Location.EMPTY) {
                    return board[row][column];
                }
            }
            return Location.EMPTY; //keep!!!
        }
    
    /**
     * This method returns the height of the specified column.
     * If the column is empty, this returns a 0. The maximum
     * value that this function can return is 6.
     * 
     * @param column the column to check
     * @return the height of the column
     */
    public int getHeightOfColumn(int column) {
        /**
        //Question 2
        Calculate  height of tokens in a spec column
        loop through each row in the column and count non-empty slots
        could start from the top, but either direction works bc just counting?
        keep track of the count and return it after loop!
        */
        int height = 0;
        for (int row = 0; row < board.length; row++) {
            if (board[row][column] != Location.EMPTY) {
                height++;
            }
        }
        return height;
    }
    
    /**
     * This method drops a token into the specified column and 
     * changes the current player (from RED to BLACK and vice versa).
     * If the column is invalid (i.e., not in the range [0, 6]), then
     * the drop should be ignored (so that it would remain the previous
     * player's turn). If the column is full, it should throw a
     * ColumnFullException (but the player should also not lose their turn).
     * 
     * @param column the column for the token
     */
    public void dropToken(int column) {
        /**
        //Question 3
        Drop a token into a specified column and change player after
        if column index is out of range- ignore it. If full- throw ColunFullException
        check if the column is valid first. Then start from the bottom row and look for empty spot
        If find empty spot- place the token based on player's turn. If not- throw  exception.
        After dropping- switch turn  
        */
        if (column < 0 || column >= board[0].length) {
            return;
        }

        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][column] == Location.EMPTY) {
                board[row][column] = redTurn ? Location.RED : Location.BLACK;
                redTurn = !redTurn;
                return;
            }

        }

        throw new ColumnFullException(); // execpt
    }
    
    /**
     * This method returns the current result of the game. If the
     * game board is entirely full, this result should be TIE. If
     * RED or BLACK have four-in-a-row, the result should be REDWIN
     * or BLACKWIN, respectively. Otherwise, the result should be
     * NONE (meaning the game is not over).
     * 
     * @return the current result of the game
     */
    public Result getResult() {
        // Question 4
        // You don't have to handle all possible winning conditions.
        // You only need to handle whether a player has won vertically
        // (as either RED or BLACK). In all other cases, this should 
        // return Result.NONE.
        // 
        // Note: You may find it convenient to use another method of
        //       this class to determine whether someone has won 
        //       along a column.

        /*
        check for: 4 tokens in a row v is winner, is tie, or still going
        loop through each column and check for 4 consecutive tokens vert
        use a counter to keep track of consecutive tokens and reset when new token type starts
        If I get 4 in a row- check if RED or BLACK and return the result
        If no 4-in-a-row and board isn't full- return NONE.
         */
        
        for (int colNum = 0; colNum < board[0].length; colNum++) {
            int count = 0;
            Location lastLoc = Location.EMPTY;

            for (int rowNum = 0; rowNum < board.length; rowNum++) {
                if (board[rowNum][colNum] == lastLoc && lastLoc != Location.EMPTY) {
                    count++;
                    if (count == 4) {
                        if (lastLoc == Location.RED) {
                            return Result.REDWIN;
                        } else {
                            return Result.BLACKWIN;
                        }
                    }
                } else {
                    count = 1;
                    lastLoc = board[rowNum][colNum];
                }
            }
        }
        return Result.NONE; //keep!
    }
    
    /**
     * This method returns the representation of the board as a string.
     * The following is an example:
     * 
     *  | | | | | | | |
     *  | | | | | | | |
     *  | | | | | | | |
     *  | | | |B| | | |
     *  |B| |B|R| | | |
     *  |R|B|B|R| |R|R|
     *  ---------------
     * 
     * Here, "B" and "R" mark the black and red tokens in each column.
     * 
     * @return a string representing the board
     */
    public String toString() {
        // Question 5
        /**
        make board as a string. String builder!
        loop over each row and column and build  string showing tokens and empty spaces
        rows have tokens separated by |
        nested loops to build this string rows and columbs
        R B or " "
        ---------------
        */
        StringBuilder sb = new StringBuilder();
        for (int rowNum = 0; rowNum < board.length; rowNum++) {
            for (int colNum = 0; colNum < board[0].length; colNum++) {
                sb.append("|");
                if (board[rowNum][colNum] == Location.RED) {
                    sb.append("R");
                } else if (board[rowNum][colNum] == Location.BLACK) {
                    sb.append("B");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("|\n");
        }
        sb.append("---------------");
        return sb.toString();
    }
}


