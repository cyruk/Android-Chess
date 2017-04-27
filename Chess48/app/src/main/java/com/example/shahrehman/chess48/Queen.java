package samp;

import java.io.IOException;

/**
 * The Queen class is inheriting piece and added to the polymorphic 2d array of type Piece
 * @author Shah Rahim, John Chen
 *
 */
public class Queen extends Piece {

    public String color;
    public String name;
    public int id;
    public Rook rook = new Rook();
    public Bishop bishop = new Bishop();
    
    /**
     * The queens constructor
     * @param color sets the color of the queen piece
     * @param name sets the name of the queen piece
     * @param id sets the id number for the queen piece
     */
    public Queen(String color, String name, int id){
        this.color = color;
        this.name = name;
        this.id = id;
    }
    
    /**
     * No arg constructor
     */
    public Queen(){}
    
    /**
     * 
     * @param row1 this is the starting row of a piece
     * @param col1 this is the starting column of a piece
     * @param row2 this is the desired row a piece wants to move
     * @param col2 this is the desired column a piece wants to move
     * @param br this is the board being passed in for checking
     * @return returns a string based on the pieces possible moves: FreeMove, Kill or their special respective move or no for invalid
     * @throws IOException
     */
    public String isValid(int row1, int col1, int row2, int col2, Board br) throws IOException {
        
        if(rook.isValid(row1,col1,row2,col2,br).equals("No") && bishop.isValid(row1,col1,row2,col2,br).equals("No")){
            return "No";
        }
        else if(rook.isValid(row1,col1,row2,col2,br).equals("FreeMove") || bishop.isValid(row1,col1,row2,col2,br).equals("FreeMove")){
            return "FreeMove";
        }
        else if(rook.isValid(row1,col1,row2,col2,br).equals("Kill") || bishop.isValid(row1,col1,row2,col2,br).equals("Kill")){
            return "Kill";
        }
        return "No";
    }

    /**
     * This method is used to get the color of a piece
     * @return returns a string for the color of a certain piece
     */
    public String getColor(){
        return color;
    }
    /**
     * Used to get the name of the piece
     * @return returns a string for the name of the given piece
     */
    public String getName(){
        return name;
    }
    /**
     * This is used to get the id number of the queen piece
     * @return an int for the queen id
     */
    public int getId(){
        return id;
    }
}
