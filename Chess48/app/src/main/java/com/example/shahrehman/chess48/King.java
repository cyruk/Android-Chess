package com.example.shahrehman.chess48;

import java.io.IOException;
import java.io.Serializable;

/**
 * The King class is inheriting piece and added to the polymorphic 2d array of type Piece
 * @author Shah Rahim, John Chen
 *
 */
public class King extends Piece implements Serializable {
    public String color;
    public String name;
    public boolean moved;
    public int id;
    
    /**
     * This is the kings constructor
	 * @color this is the color field used to distinguish black and white pieces
	 * @name this is used to distinguash different pieces when displaying them on the board
	 * @moved this is used to signal if a certain piece has moved or not
	 * @id this field is used for identifying the index of a given piece in the boards coordinate arrays
	 */
    public King(String color, String name, int id) {
        this.color = color;
        this.name = name;
        moved = false;
        this.id = id;
    }

    public King(Piece k) {
        this.color = k.getColor();
        this.name = k.getName();
        this.moved = k.getMoved();
        this.id = k.getId();
    }
    /**
     * No arg constructor
     */
    public King(){}
    
    /**
     * This method is used to check if a move is valid for the king
     * @param row1 this is the starting row of a piece
     * @param col1 this is the starting column of a piece
     * @param row2 this is the desired row a piece wants to move
     * @param col2 this is the desired column a piece wants to move
     * @param br this is the board being passed in for checking
     * @return returns a string based on the pieces possible moves: FreeMove, Kill or their special respective move or no for invalid
     * @throws IOException
     */
    public String isValid(int row1, int col1, int row2, int col2, Board br) throws IOException {
        String cl = br.board[row1][col1].getColor();
        //moving more than one spot down or up
        if (row1-row2>1||row2-row1>1){
            return "No";
        }
        //moving more than two spots to the left or to the right
        else if(col1-col2>2 || col2 -col1 >2){
            return "No";
        }
        //moving two spots to the left or to the right diagonally
        else if ((col1-col2==2 && row1!=row2)||(col2-col1==2 && row1!=row2)){
            return "No";
        }
        ////moving two spots to the left or right
        else if(col1-col2 == 2){
            //king has already moved
            if(br.board[row1][col1].getMoved()==true){
                return "No";
            }
            //moving towards the left and pieces in the way?
            else if((!br.board[row1][col1-1].getClass().isInstance(new Empty()))
                    ||(!br.board[row1][col1-2].getClass().isInstance(new Empty()))
                    ||(!br.board[row1][col1-3].getClass().isInstance(new Empty()))){
                return "No";
            }

            // moving to the left and path is clear... is there a rook for castling?
            else if(br.board[row1][col1-4].getClass().isInstance(new Rook())){
                //has the rook already moved??
                if(br.board[row1][col1-4].getMoved()==true) {
                    return "No";
                }
                //valid left castling
                return "lc";
            }
            return "No";
        }
        //king is moving to the right
        else if(col2-col1 ==2){
            //king has already moved
        	//System.out.println(br.board[row1][col1].moved);
            if(br.board[row1][col1].getMoved()==true){
                return "No";
            }
            //moving towards the right and pieces in the way?
            else if((!br.board[row1][col1+1].getClass().isInstance(new Empty()))
                    ||(!br.board[row1][col1+2].getClass().isInstance(new Empty()))){
                return "No";
            }
            else if(br.board[row1][col1+3].getClass().isInstance(new Rook())){
                //has the rook already moved??
                if(br.board[row1][col1+3].getMoved()==true) {
                    return "No";
                }
                //valid right castling
                return "rc";
            }
            return "No";
        }
        //Spot is within 1 and is empty
        else if(br.board[row2][col2].getClass().isInstance(new Empty())){
            return "FreeMove";
        }
        //Spot is is within 1 and is a friendly piece
        else if(!br.board[row2][col2].getClass().isInstance(new Empty()) && br.board[row2][col2].getColor().equals(cl)){
            return "No";
        }
        //Enemy piece for killing
        return "Kill";
    }
    
    /**
     * This method is used to calculate all legal moves a king can make
     * @param kRow the row at which the king is located
     * @param kCol the column at which the king is located
     * @return returns an array of strings containing all legal moves of a king
     */
    public String[] possibleMoves(int kRow, int kCol){
    	int[] row = new int[8];
    	int[] col = new int[8];
        String[] validCoordinates = new String[8];
    	row[0] = kRow-1;
        row[1] = kRow-1;
        row[2] = kRow-1;
        row[3] = kRow+1;
        row[4] = kRow+1;
        row[5] = kRow+1;
        row[6] = kRow;
        row[7] = kRow;
        col[0] = kCol;
        col[1] = kCol-1;		
        col[2] = kCol+1;		
        col[3] = kCol;
        col[4] = kCol-1;
        col[5] = kCol+1;
        col[6] = kCol-1;
        col[7] = kCol+1;
        
        for(int i=0;i<8;i++){
        	if(row[i]<0 || row[i] >7||col[i]<0||col[i]>7 ){
        		validCoordinates[i] = "";
        	}
        	else{
        		validCoordinates[i] = "" + row[i] + col[i];
        	}
        }
        return validCoordinates;
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


    public boolean getMoved(){
        return moved;
    }

    public void setMoved(boolean hasMoved){
        moved = hasMoved;
    }

}
