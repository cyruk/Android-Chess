package samp;

import java.io.IOException;

/**
 * The Knight class is inheriting piece and added to the polymorphic 2d array of type Piece
 * @author Shah Rahim, John Chen
 *
 */
public class Knight extends Piece {
    public String color;
    public String name;
    public int id;
    
    
    /**
     * The knights constructor
     * @param color sets the color of the knight piece
     * @param name sets the name of the knight piece
     * @param id sets the id number for the knight piece
     */
    public Knight(String color, String name,int id){
        this.color = color;
        this.name = name;
        this.id = id;
    }
    
    /**
     * No arg constructor
     */
    public Knight(){}
    
    /**
     * This method is used to check if a move is valid for the knight
     * @param row1 this is the starting row of a piece
     * @param col1 this is the starting column of a piece
     * @param row2 this is the desired row a piece wants to move
     * @param col2 this is the desired column a piece wants to move
     * @param br this is the board being passed in for checking
     * @return returns a string based on the pieces possible moves: FreeMove, Kill or their special respective move or no for invalid
     * @throws IOException
     */
    public String isValid(int row1, int col1, int row2, int col2, Board br) throws IOException {
        boolean properMove = properMove(row1,col1,row2,col2);
        String cl = br.board[row1][col1].getColor();
        if(properMove == false){
            return "No";
        }
        else{
            if(br.board[row2][col2].getClass().isInstance(new Empty())){
                return "FreeMove";
            }
            else if(br.board[row2][col2].getColor().equals(cl)){
                return "No";
            }
        }
        return "Kill";
    }
    
    /**
     * This method simply checks all possible moves a knight can make and checks if they are valid
     * @param row1 this is the starting row of a piece
     * @param col1 col1 this is the starting column of a piece
     * @param row2 this is the desired row a piece wants to move
     * @param col2 this is the desired column a piece wants to move
     * @return returns true or false whether a move is proper or not
     */
    public boolean properMove(int row1, int col1, int row2, int col2){
        if(row2 ==row1-2 && col2 == col1-1){
            return true;
        }
        else if(row2 ==row1-1 && col2 == col1-2){
            return true;
        }
        else if(row2 ==row1-2 && col2 == col1+1){
            return true;
        }
        else if(row2 ==row1-1 && col2 == col1+2){
            return true;
        }
        else if(row2 ==row1+1 && col2 == col1+2){
            return true;
        }
        else if(row2 ==row1+2 && col2 == col1+1){
            return true;
        }
        else if(row2 ==row1+2 && col2 == col1-1){
            return true;
        }
        else if(row2 ==row1+1 && col2 == col1-2){
            return true;
        }
        return false;
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
     * This is used to get the id number of the rook piece
     * @return an int for the rook id
     */
    public int getId(){
        return id;
    }
}
