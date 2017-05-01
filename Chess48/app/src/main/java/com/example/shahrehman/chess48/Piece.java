package com.example.shahrehman.chess48;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.Serializable;

/**
 * The Piece Class is the superclass which will be inherited by the chess game pieces
 * @author Shah Rahim, John Chen
 *
 */
public class Piece implements Serializable {
	/**
	 * @color this is the superclass color field used to distinguish black and white pieces
	 * @epos this is the Empassant field used by pawn for persorming empassant
	 * @name this is used to distinguash different pieces when displaying them on the board
	 * @moved this is used to signal if a certain piece has moved or not
	 * @id this field is used for identifying the index of a given piece in the boards coordinate arrays
	 */
    public String color;
    public boolean ePos;
    public String name;
    public boolean moved;
    public int id;
    
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
    public String isValid(int row1, int col1, int row2, int col2, Board br) throws IOException{return "";}
    
    /**
     * Used to get the name of the piece
     * @return returns a string for the name of the given piece
     */
    public String getName(){
        return name;
    }

    //public Piece(Piece p){}
    public Piece(){}
    /**
     * This method is used to get the color of a piece
     * @return returns a string for the color of a certain piece
     */
    public String getColor(){return color;}
    
    /**
     * This is used to set the name of a certain piece
     * @param name sets the name of a given piece
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * This is used to get the id number of the certain piece
     * @return an int for the pieces id
     */
    public int getId(){
        return id;
    }
    
    /**
     * This is used for getting the possible valid set of moves a piece can make
     * @return returns an array of strings for all the possible moves which will be converted into ints
     */
    public String[] possibleMoves(){
    	String[] arr = new String[0];
    	return arr;
    }

    public void setMoved(boolean hasMoved){}

    public boolean getMoved(){
        return moved;
    }

    public boolean ePos(){
        return ePos;
    }
}
