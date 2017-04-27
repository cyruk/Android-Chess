package samp;

import java.io.IOException;

/**
 * The pawn class is inheriting piece and added to the polymorphic 2d array of type Piece
 * @author Shah Rahim, John Chen
 *
 */
public class Pawn extends Piece {
    public String color;
    public boolean ePos;
    public String name;
    public int id;
    
    /**
     * 
     * @param color
     * @param name
     * @param id
     */
    public Pawn(String color, String name, int id){
        this.color = color;
        this.name = name;
        ePos = false;
        this.id = id;
    }
    
    /**
     * The no arg constructor for pawn
     */
    public Pawn(){

    }

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
    public String isValid(int row1, int col1, int row2, int col2, Board br)throws IOException{
        if(br.board[row1][col1].getColor().equals("White")){
            //going backwards?
            if(row2 >=row1){
                return "No";
            }
            //moving more than one spot to the left or right?
            else if(col1-col2>1||col2-col1>1){
                return "No";
            }
            //made atleast one move and moving two spots forward?
            else if(row1<6&& row1-row2>1){
                return "No";
            }
            //has not moved and moving more than two spots forward in one turn?
            else if(row1==6&& row1-row2>2 ){
                return "No";
            }
            //moving two spots as first move, and not moving straight
            else if(row1==6 && (row1-row2>=2 && col1 !=col2)){
                return "No";
            }
            String cl = br.board[row2][col2].getColor();
            
            //desired spot not empty
            if(!(br.board[row2][col2].getClass().isInstance(new Empty()))){
                //if moving forward and friendly piece in the way
                if((cl.equals("White") && col2 == col1)||(cl.equals("Black") && col2 == col1)) {
                    return "No";
                }
                else if(cl.equals("White") && col2 != col1) {
                    return "No";
                }
                else if(cl.equals("Black") && col2 != col1 && row2 == 0){
                    return "KP";
                }
                //black piece there for killing
                return "Kill";
            }
            //moving onto an empty spot
            else if(br.board[row2][col2].getClass().isInstance(new Empty())){
                if(col2==col1){
                    //promotion
                    if(row2==8){
                        return "Pro";
                    }
                    //moving two spots but piece in the way
                    else if(row1==6 && row1-row2==2 && !br.board[row2+1][col2].getClass().isInstance(new Empty())){
                        return "No";
                    }
                    else if(row2 ==4 && row1 ==2){
                    	return "setEpos";
                    }
                    return "FreeMove";
                }
                else if(row1 ==3&& br.board[row2+1][col2].ePos==true){
                    return "Epos";
                }
                return "No";
            }
        }
        else if(br.board[row1][col1].getColor().equals("Black")){
            //going backwards?
            if(row2 <=row1){
                return "No";
            }
            //moving more than one spot to the left or right?
            else if(col2-col1>1||col1-col2>1){
                return "No";
            }
            //made atleast one move and moving two spots forward?
            else if(row1>1&& row2-row1>1){
                return "No";
            }
            //has not moved and moving more than two spots forward in one turn?
            else if(row1==1&& row2-row1>2 ){
                return "No";
            }

            //moving two spots as first move, and not moving straight
            else if(row1==1 && (row2-row1>=2 && col1 !=col2)){
                return "No";
            }
            String cl = br.board[row2][col2].getColor();
            //desired spot not empty
            if(!(br.board[row2][col2].getClass().isInstance(new Empty()))){
                if((cl.equals("White") && col2 == col1)||(cl.equals("Black") && col2 == col1)) {
                    return "No";
                }
                else if(cl.equals("Black") && col2 != col1) {
                    return "No";
                }
                else if(cl.equals("White") && col2 != col1 && row2 == 7){
                    return "KP";
                }

                //black piece there for killing
                return "Kill";
            }
            //moving onto an empty spot
            else if(br.board[row2][col2].getClass().isInstance(new Empty())){
                if(col2==col1){
                    //promotion
                    if(row2==7){
                        return "Pro";
                    }
                    else if(row1 ==1 && row2-row1 ==2&& !br.board[row2-1][col2].getClass().isInstance(new Empty())){
                        return "No";
                    }
                    else if(row2 ==3 && row1 ==1){
                    	return "setEpos";
                    }
                    return "FreeMove";
                }
                else if(row1 ==4&& br.board[row2-1][col2].ePos==true){
                    return "Epos";
                }
                return "No";
            }
        }
        return "FreeMove";
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
     * This is used to get the id number of the pawn piece
     * @return an int for the pawns id
     */
    public int getId(){
        return id;
    }
}
