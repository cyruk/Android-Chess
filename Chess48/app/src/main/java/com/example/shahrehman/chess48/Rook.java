package samp;

import java.io.IOException;


/**
 * The rook class is inheriting piece and added to the polymorphic 2d array of type Piece
 * @author Shah Rahim, John Chen
 *
 */
public class Rook extends Piece {
    public String color;
    public String name;
    public  boolean moved;
    public int id;
    
    /**
     * This is the constructor for rook class
     * @param color sets the color of the rook
     * @param name sets the name of the rook used for printing the piece
     * @param id sets the indexing id of the rook used for setting its coordinates
     */
    public Rook(String color, String name, int id){
        this.color = color;
        this.name = name;
        moved = false;
        this.id = id;
    }
    /**
     * No arg constructor
     */
    public Rook(){}
    
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
        String dir = direction(row1,col1,row2,col2);
        String cl = br.board[row1][col1].getColor();
        int i;
        if(dir.equals("invalid")){

            return "No";
        }
        else if(dir.equals("nor")){
            for(i = row1-1;i>row2;i--){
                if(!(br.board[i][col2].getClass().isInstance(new Empty()))){
                    return "No";
                }
            }
        }
        else if(dir.equals("sou")){
            for(i = row1+1;i<row2;i++){
                if(!(br.board[i][col2].getClass().isInstance(new Empty()))){
                    return "No";
                }
            }
        }
        else if(dir.equals("ea")){
            for(i = col1+1;i<col2;i++){
                if(!(br.board[row2][i].getClass().isInstance(new Empty()))){
                    return "No";
                }
            }
        }
        else if(dir.equals("we")){
            for(i = col1-1;i>col2;i--){
                if(!(br.board[row2][i].getClass().isInstance(new Empty()))){
                    return "No";
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
     * This method is used to calculate the directiona the rook is going
     * @param row1 the starting row of the rook
     * @param col1 the starting column of the rook
     * @param row2 the desired row of the rook
     * @param col2 the desired column of the rook
     * @return returns a string of either invalid, 's', 'n', 'e', 'w'
     */
    public String direction(int row1,int col1, int row2, int col2){
        if(col1-col2 !=0 && row1-row2!=0){
            return "invalid";
        }
        if(col1-col2!=0){
            if(col1-col2 <0){
                return "ea";
            }
            else if(col1-col2>0){
                return "we";
            }
        }
        else if(row1-row2 != 0){
            if(row1-row2 >0){
                return "nor";
            }
            else if(row1-row2<0){
                return "sou";
            }
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
