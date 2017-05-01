package com.example.shahrehman.chess48;

import java.io.IOException;
import java.io.Serializable;

/**
 * The Bishop class is inheriting piece and added to the polymorphic 2d array of type Piece
 * @author Shah Rahim, John Chen
 *
 */
public class Bishop extends Piece implements Serializable{

    public String color;
    public String name;
    public int id;
    
    /**
     * The bishop constructor
     * @param color sets the color of the bishop piece
     * @param name sets the name of the bishop piece
     * @param id sets the id number for the bishop piece
     */
    public Bishop(String color, String name, int id){
        this.color = color;
        this.name = name;
        this.id = id;

    }
    public Bishop(Piece b){
        this.color = b.getColor();
        this.name = b.getName();
        this.id = b.getId();

    }



    /**
     * No arg constructor
     */
    public Bishop(){}
    
    /**
     * This method is used to check if a move is valid for the Bishop
     * @param row1 this is the starting row of a piece
     * @param col1 this is the starting column of a piece
     * @param row2 this is the desired row a piece wants to move
     * @param col2 this is the desired column a piece wants to move
     * @param br this is the board being passed in for checking
     * @return returns a string based on the pieces possible moves: FreeMove, Kill or their special respective move or no for invalid
     * @throws IOException
     */
    public String isValid(int row1, int col1, int row2, int col2, Board br) throws IOException {
        String dir = direction(row1,col1,row2,col2);
        int i,j;
        String cl = br.board[row1][col1].getColor();
        if(dir.equals("invalid")){
            return "No";
        }

        else if(dir.equals("ne")){
            for(i=row1-1;i>row2;i--){
                for(j = col1+1;j<col2;j++){
                    if(!(br.board[i][j].getClass().isInstance(new Empty()))){
                        return "No";
                    }
                    i--;
                }
            }
        }
        else if(dir.equals("nw")){
            for(i=row1-1;i>row2;i--){
                for(j = col1-1;j>col2;j--){
                    if(!(br.board[i][j].getClass().isInstance(new Empty()))){
                        return "No";
                    }
                    i--;
                }
            }
        }
        else if(dir.equals("se")){
            for(i=row1+1;i<row2;i++){
                for(j = col1+1;j<col2;j++){
                    if(!(br.board[i][j].getClass().isInstance(new Empty()))){
                        return "No";
                    }
                    i++;
                }
            }
        }
        else if(dir.equals("sw")){
            for(i=row1+1;i<row2;i++){
                for(j = col1-1;j>col2;j--){
                    if(!(br.board[i][j].getClass().isInstance(new Empty()))){
                        return "No";
                    }
                    i++;
                }
            }
        }
        if((br.board[row2][col2].getClass().isInstance(new Empty()))) {
            return "FreeMove";
        }
        else if(br.board[row2][col2].getColor().equals(cl)){
            return "No";
        }
        return "Kill";

    }
    
    /**
     * This method calculates what direction the bishop is going
     * @param row1 this is the starting row of a piece
     * @param col1 this is the starting column of a piece
     * @param row2 this is the desired row a piece wants to move
     * @param col2 this is the desired column a piece wants to move
     * @return returns a string either; invalid, or the the direction the bishop is going
     */
    public String direction(int row1,int col1, int row2, int col2){
        double dX = col2-col1;
        double dY = row2-row1;
        double slope = (dY/dX);
        if(slope != 1.0 && slope != -1.0){
            return "invalid";
        }
        if(dY>0&& dX<0){
            return "sw";
        }
        else if(dY<0 &&dX<0){
            return "nw";
        }
        else if(dY>0&&dX>0){
            return "se";
        }
        else if(dY<0&&dX>0){
            return "ne";
        }
        return "invalid";
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
