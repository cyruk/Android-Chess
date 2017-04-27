package samp;

/**
 * The Empty class is inheriting piece and added to the polymorphic 2d array of type Piece as an empty spot
 * @author Shah Rahim, John Chen
 *
 */
public class Empty extends Piece {
    public String name;
    
    /**
     * This is the constructor for an empty piece
     * @param name the name is used for hashing
     */
    public Empty(String name){
        this.name = name;
    }
    
    /**
     * The no arg constructor
     */
    public Empty(){
    }
    
    /**
     * Used to get the name of the piece
     * @return returns a string for the name of the given piece
     */
    public String getName(){
        return name;
    }
    
    /**
     * This is used to set the name of a certain piece
     * @param name sets the name of a given piece
     */
    public void setName(String name){
        this.name = name;
    }
}
