
package com.example.shahrehman.chess48;
import java.io.IOException;
/**
 * The Game class handles all games of chess
 * @author Shah Rahim, John Chen
 *
 */
public class Game {
	
	/**
	 * No arg constructor
	 */
    public Game() {
    }


	public String moveDetails = "";
    /**
     * This class checks if certaain pieces move is valid and then performs the move
     * @param br is the board object to be examined
     * @param cr is the coordinate the user inputs
     * @param whiteTurn is true if its whites turn, false otherwise
     * @return returns true if the move is successful, false otherwise
     * @throws IOException
     * @throws NullPointerException
     */
    public boolean move(Board br, String cr, boolean whiteTurn, String pro) throws IOException, NullPointerException {
        int[] cor;
        int row1, col1, row2, col2;
        cor = convert(cr);
        Piece[] tmp = new Piece[1];
        row1 = cor[0];
        col1 = cor[1];
        row2 = cor[2];
        col2 = cor[3];

        if(row1==row2 && col1 ==col2){
        	return false;
        }
        else if (br.board[row1][col1].getClass().isInstance(new Empty())){
        	return false;
        }
        String color = br.board[row1][col1].getColor();
        if ((whiteTurn == true && color.equals("Black"))||(whiteTurn == false && color.equals("White"))){
        	return false;
        }
        moveDetails = br.board[row1][col1].isValid(row1, col1, row2, col2, br);
        if (moveDetails.equals("No")){
        	return false;
        }
        if (br.board[row1][col1].getClass().isInstance(new Pawn())) {
            if (moveDetails.equals("FreeMove")) {
                br = freeMove(br, row1, col1, row2, col2, tmp, color);
            }
            else if(moveDetails.equals("setEpos")){
            	br = freeMove(br, row1, col1, row2, col2, tmp, color);
            	br.board[row2][col2].ePos = true;
            }
            else if (moveDetails.equals("Epos")){
                Epos(br,row1,col1,row2,col2,color,tmp);
            }
            else if (moveDetails.equals("Kill")) {
                br = Kill(br, row1,col1,row2,col2,tmp,color);
            }
            else if (moveDetails.equals("KP") || moveDetails.equals("Pro")){
                br = promotion(br, row1,col1,row2,col2,moveDetails,color,cr,pro);
            }
        }
        else if (br.board[row1][col1].getClass().isInstance(new Rook())) {
            if (moveDetails.equals("FreeMove")) {
                br = freeMove(br, row1, col1, row2, col2, tmp, color);
                br.board[row2][col2].moved = true;
            }
            else if (moveDetails.equals("Kill")) {
                br = Kill(br, row1,col1,row2,col2,tmp,color);
                br.board[row2][col2].moved = true;
            }
        }
        else if (br.board[row1][col1].getClass().isInstance(new Bishop())) {
            if (moveDetails.equals("FreeMove")) {
                br = freeMove(br, row1, col1, row2, col2, tmp, color);
            }
            else if (moveDetails.equals("Kill")) {
            	 br = Kill(br, row1,col1,row2,col2,tmp,color);
            }
        }
        else if (br.board[row1][col1].getClass().isInstance(new Knight())) {
            if (moveDetails.equals("FreeMove")) {
                br = freeMove(br, row1, col1, row2, col2, tmp, color);
            }
            else if (moveDetails.equals("Kill")) {
            	br = Kill(br, row1,col1,row2,col2,tmp,color);
            }
        }
        else if (br.board[row1][col1].getClass().isInstance(new Queen())) {
            if (moveDetails.equals("FreeMove")) {
                br = freeMove(br, row1, col1, row2, col2, tmp, color);
            }
            else if (moveDetails.equals("Kill")) {
            	br = Kill(br, row1,col1,row2,col2,tmp,color);
            }
        }
        else if (br.board[row1][col1].getClass().isInstance(new King())) {
            if (moveDetails.equals("FreeMove")) {
                br = freeMove(br, row1, col1, row2, col2, tmp, color);
                br.board[row2][col2].moved= true;
            }
            else if (moveDetails.equals("Kill")) {
                br = Kill(br, row1,col1,row2,col2,tmp,color);
                br.board[row2][col2].moved= true;
            }
            else if (moveDetails.equals("lc") ||moveDetails.equals("rc")) {
                castling(br,row1,col1,row2,col2,moveDetails,color,tmp);
            }
        }

        return true;
    }

    public String help(Board br, boolean whiteTurn,String crd) throws IOException{
        //boolean loop= true;
        int i;
        int[] cor = new int[2];
        Board copy;
        String[] moves = new String[16];
        String moveDe = "";
        if(whiteTurn==true){
            for(i=0;i<16;i++) {
                if (!br.White[i].isEmpty()) {

                    cor = getCoordinate(br.White[i]);
                    if (i < 8) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new Pawn().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    } else if (i == 8 || i == 9) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new Rook().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    } else if (i == 10 || i == 11) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new Knight().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    } else if (i == 12 || i == 13) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new Bishop().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    } else if (i == 14) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new Queen().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    } else if (i == 15) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new King().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    }
                }
            }
            if(i>15){
                return "No Help";
            }
        }
        else if(whiteTurn==false) {
            for (i = 0; i < 16; i++) {
                if (!br.Black[i].isEmpty()) {

                    cor = getCoordinate(br.Black[i]);
                    if (i < 8) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new Pawn().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    } else if (i == 8 || i == 9) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new Rook().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    } else if (i == 10 || i == 11) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new Knight().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    } else if (i == 12 || i == 13) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new Bishop().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    } else if (i == 14) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new Queen().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    } else if (i == 15) {
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                moveDe = new King().isValid(cor[0], cor[1], y, x, br);
                                if (moveDe.equals("No")) {
                                    continue;
                                } else {
                                    copy = new Board(br);
                                    if(friendCheck(copy,convertBack("" + cor[0] + cor[1]) + " " + convertBack("" + y + x),whiteTurn).equals("friendCheck"))
                                        continue;
                                    else
                                        return convertBack("" + cor[0] + cor[1]) + "-->" + convertBack("" + y + x);
                                }
                            }
                        }
                    }
                }
            }
            if(i>15){
                return "No Help";
            }
        }
        return "No Help";
    }

     /**
     * This method checks if the king is in checkmate
     * @param br the board to be examined
     * @param turn which piece just went
     * @return returns true if the enemy king is in checkmate, false otherwise
     * @throws IOException
     */
    public boolean checkMate(Board br, boolean turn) throws IOException{
    	//base position of king is in line of sight for more than one enemy and king cant move out of check
    	Board copy = copyBoard(br);
    	int kRow,kCol;
    	King kObject = new King();
    	@SuppressWarnings("unused")
		String testCdt= "";
    	String[] validCoordinates = new String[8];
    	String kCoordinate = "";
    	int checkCounter = 0;
    	int kMoveCounter=0;
    	String[] pieceKInCheck = new String[1];
    	String counterKillInput = ""; 
    	int cantSaveKing = 0;
    	int killerIndex = 0;
    	String blockCoordinate = "";
		boolean block;

		//if piece that moves is white, seeing if black king is in check
        if(turn==true){
        	//getting coordinates of of the black king
        	kRow = Character.getNumericValue(br.Black[15].charAt(0));
        	kCol = Character.getNumericValue(br.Black[15].charAt(1));
        	
        	//getting all possible moves for king based in its location
        	validCoordinates = kObject.possibleMoves(kRow, kCol);
        	
        	for(int i = 0;i <copy.White.length;i++){
        		//checking every white piece to see if it has king in line of sight
        		testCdt = convertBack(copy.White[i]) + " " +convertBack(copy.Black[15]);
        		//if the coordinate is illegal for the king
        		if( convertBack(copy.White[i]).isEmpty()){
        			continue;
        		}
        		//if a piece has the king in its line of sight
        		else if(pieceEnemyCheck(copy,true,i)==true){
        			//save that piece's info
        			pieceKInCheck[0] = convertBack(copy.White[i]);
        			killerIndex = i;
        			checkCounter++;
        		}
        		//if more than one piece has king in check in base position break out of loop
        		if(checkCounter>1){
        			break;
        		}
        	}	
        }
        //Same as above if statement
        else if(turn ==false){
        	kRow = Character.getNumericValue(br.White[15].charAt(0));
        	kCol = Character.getNumericValue(br.White[15].charAt(1));
        	validCoordinates = kObject.possibleMoves(kRow, kCol);
        	for(int i = 0;i <copy.Black.length;i++){
        		testCdt = convertBack(copy.Black[i]) + " " +  convertBack(copy.White[15]);
        		if( convertBack(copy.Black[i]).isEmpty()){
        			continue;
        		}
        		else if(pieceEnemyCheck(copy,false,i)==true){
        			pieceKInCheck[0] = convertBack(copy.Black[i]);
        			killerIndex = i;
        			checkCounter++;
        		}
        		if(checkCounter>1){
        			break;
        		}
        	}	
        }
        //get a fresh board based on the original game board passed in by chess class
        copy = copyBoard(br);
        //Scenario #1--> more than one piece has king in check, king has to move
        if(checkCounter >1 && turn==true){
        	for(int i = 0;i<8;i++){
        		//if the king can move to this spot on the board
        		if(!validCoordinates[i].isEmpty()){
        			kCoordinate = convertBack(copy.Black[15]) + " " + convertBack(validCoordinates[i]);
        			//is there a spot the king can move that gets it out of check
        			if(friendlyCheck(copy, kCoordinate,false).equals("notInFriendlyCheck")){
        				return false;
        			}
        			else{
        				kMoveCounter++;
        			}
        		}
        	}
        	if(kMoveCounter==8){
        		return true;
        	}
        }
        //more than one black piece has the white king in check
        else if(checkCounter >1 && turn==false){
        	for(int i = 0;i<8;i++){
        		//if the king can move to this spot on the board
        		if(!validCoordinates[i].isEmpty()){
        			kCoordinate = convertBack(copy.White[15]) + " " + convertBack(validCoordinates[i]);
        			//if theres a piece in the way or the king will be in check if it moves, increment
        			if(friendlyCheck(copy, kCoordinate,true).equals("notInFriendlyCheck")){
        				return false;
        			}
        			else{
        				kMoveCounter++;
        			}
        		}
        		else{
        			kMoveCounter++;
        		}
        	}
        	if(kMoveCounter == 8){
        		return true;
        	}
        }
        //Only one piece has black king in check
        else if(checkCounter ==1 && turn==true){
        	for(int i = 0;i<8;i++){
        		if(!validCoordinates[i].isEmpty()){
        			kCoordinate = convertBack(copy.Black[15]) + " " + convertBack(validCoordinates[i]);
        			//if theres a friendly piece in the way or the king will be in check if it moves, increment
        			if(friendlyCheck(copy, kCoordinate,false).equals("notInFriendlyCheck")){
        				return false;
        			}
        			else{
        				kMoveCounter++;
        			}
        		}
        		else{
        			kMoveCounter++;
        		}
        	}
        	copy = copyBoard(br);
        	for(int i = 0;i<15;i++){
        		counterKillInput = convertBack(copy.Black[i]) + " " + pieceKInCheck[0];
        		if(convertBack(copy.Black[i]).isEmpty()){
        			cantSaveKing++;
        			continue;
        		}
        		else{
        			
        			if(friendlyCheck(copy, counterKillInput,false).equals("notInFriendlyCheck")){
        				return false;
        			}
        			else{
        				cantSaveKing++;
        			}
        		}
        	}
        	blockCoordinate = pieceKInCheck[0] + " " + convertBack(br.Black[15]);
        	block = blockLineOfSight(br,blockCoordinate,killerIndex,"Black" );
			if(cantSaveKing == 15&& kMoveCounter == 8&& block==false){
        		return true;
        	}
        }
        
        //Only one piece has white king in check
        else if(checkCounter ==1 && turn==false){
        	for(int i = 0;i<8;i++){
        		if(!validCoordinates[i].isEmpty()){
        			kCoordinate = convertBack(copy.White[15]) + " " + convertBack(validCoordinates[i]);
        			//if theres a friendly piece in the way or the king will be in check if it moves, increment
        			if(friendlyCheck(copy, kCoordinate,true).equals("notInFriendlyCheck")){
        				return false;
        			}
        			else{
        				kMoveCounter++;
        			}
        		}
        		else{
        			kMoveCounter++;
        		}
        	}
        	copy = copyBoard(br);
        	for(int i = 0;i<15;i++){
        		counterKillInput = convertBack(copy.White[i]) + " " + pieceKInCheck[0];
        		if(convertBack(copy.White[i]).isEmpty()){
        			cantSaveKing++;
        			continue;
        		}
        		else{
        			
        			if(friendlyCheck(copy, counterKillInput,true).equals("notInFriendlyCheck")){
        				return false;
        			}
        			else{
        				cantSaveKing++;
        			}
        		}
        	}
        	blockCoordinate = pieceKInCheck[0] + " " + convertBack(br.White[15]);
        	block = blockLineOfSight(br,blockCoordinate,killerIndex,"White" );
			if(cantSaveKing == 15&& kMoveCounter == 8&& blockLineOfSight(br,blockCoordinate,killerIndex,"White" )==false){
        		return true;
        	}
        }
    
        
    	return false;
    }
    
    /**
     * This method checks if a certain piece has the enemy king in check
     * @param br the board to be examined
     * @param whiteTurn which colors turn is it
     * @param i the index of a piece within the array of coordinates
     * @return returns true if a piece has the enemy king in check, false otherwise
     * @throws IOException
     */
    public boolean pieceEnemyCheck(Board br, boolean whiteTurn, int i) throws IOException{
    	Board copy = copyBoard(br);
    	String testCdt = "";
    	
        if(whiteTurn==true){
       		testCdt = convertBack(copy.White[i]) + " " +convertBack(copy.Black[15]);
       		if( convertBack(copy.White[i]).isEmpty() ||convertBack(copy.Black[15]).isEmpty()){
       			return false;
       		}
       		else if(move(copy,testCdt,true, "")==true){
       			return true;
       		}
       		
        }
        else if(whiteTurn==false){
        	testCdt = convertBack(copy.Black[i]) + " " +  convertBack(copy.White[15]);
        	if( convertBack(copy.Black[i]).isEmpty() ||convertBack(copy.White[15]).isEmpty()){
        		return false;
        	}
        	else if(move(copy,testCdt,false,"")==true){
        		return true;
        	}
        		
        }
    	return false;
    	
    }
    /**
     * This method checks if a kings frieldy pieces can block off their king from being in check
     * @param br the board to be examined
     * @param cdt the coordinate of the piece that has king in check and the king thats in check respectivley
     * @param killerIndex the pieces index in the array of coordinates that has king in check
     * @param kColor the color of the king thats attempting to avoid check
     * @return returns true if a kings friendly piece can block the enemy piece, false otherwise
     * @throws IOException
     */
    public boolean blockLineOfSight(Board br, String cdt, int killerIndex,String kColor) throws IOException{
    	Board copy = copyBoard(br);		
    	int[] cor = convert(cdt);		
    	int kRow,kCol,pRow,pCol;		
    	pRow = cor[0];					
    	pCol = cor[1];					
    	kRow = cor[2];					
    	kCol = cor[3];					
    	String direction = "";			
    	String blockCoordinate = "";     	
    	String blockDestination = "";    	
    	Queen piece = new Queen();	   	
    	if(killerIndex==10|| killerIndex==11){
    		return false;			  	
    	}							   
    	direction = piece.rook.direction(pRow, pCol, kRow, kCol);
		if(direction.equals("invalid")){
			direction = piece.bishop.direction(pRow, pCol,kRow, kCol);
		}							
    	if(kColor.equals("White")){
			if(direction.equals("nor")){
				for(int i = pRow-1;i<kRow;i--){
					for(int j = 0;j<15;j++){
						blockDestination = "" + i + pCol;
						blockCoordinate = convertBack(copy.White[j]) + " " + convertBack(blockDestination);
						if(convertBack(copy.White[j]).isEmpty()){
							continue;
						}
						else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
							return true;
						}
					}
				}
				return false;
			}
			else if(direction.equals("sou")){
				for(int i = pRow+1;i>kRow;i++){
					for(int j = 0;j<15;j++){
						blockDestination = "" + i + pCol;
						blockCoordinate = convertBack(copy.White[j]) + " " + convertBack(blockDestination);
						if(convertBack(copy.White[j]).isEmpty()){
							continue;
						}
						else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
							return true;
						}
					}
				}
				return false;
			}
			else if(direction.equals("ea")){
				for(int i = pCol+1;i<kCol;i++){
					for(int j = 0;j<15;j++){
						blockDestination = "" + pRow + i;
						blockCoordinate = convertBack(copy.White[j]) + " " + convertBack(blockDestination);
						if(convertBack(copy.White[j]).isEmpty()){
							continue;
						}
						else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
							return true;
						}
					}
				}
				return false;
			}
			else if(direction.equals("we")){
				for(int i = pCol-1;i>kCol;i--){
					for(int j = 0;j<15;j++){
						blockDestination = "" + pRow + i;
						blockCoordinate = convertBack(copy.White[j]) + " " + convertBack(blockDestination);
						if(convertBack(copy.White[j]).isEmpty()){
							continue;
						}
						else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
							return true;
						}
					}
				}
				return false;
			}
			else if(direction.equals("ne")){
				for(int i=pRow-1;i>kRow;i--){
	                for(int j = pCol+1;j<kCol;j++){
	                	for(int k = 0;k<15;k++){
	                		blockDestination = "" + i + j;
							blockCoordinate = convertBack(copy.White[k]) + " " + convertBack(blockDestination);
							if(convertBack(copy.White[k]).isEmpty()){
								continue;
							}
							else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
								return true;
							}
						}
	                    i--;
	                }
	            }
				return false;
			}
			else if(direction.equals("nw")){
				for(int i=pRow-1;i>kRow;i--){
	                for(int j = pCol-1;j>kCol;j--){
	                	for(int k = 0;k<15;k++){
	                		blockDestination = "" + i + j;
							blockCoordinate = convertBack(copy.White[k]) + " " + convertBack(blockDestination);
							if(convertBack(copy.White[k]).isEmpty()){
								continue;
							}
							else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
								return true;
							}
						}
	                    i--;
	                }
	            }
				return false;
			}
			else if(direction.equals("se")){
				for(int i=pRow+1;i<kRow;i++){
	                for(int j = pCol+1;j<kCol;j++){
	                	for(int k = 0;k<15;k++){
	                		blockDestination = "" + i + j;
							blockCoordinate = convertBack(copy.White[k]) + " " + convertBack(blockDestination);
							if(convertBack(copy.White[k]).isEmpty()){
								continue;
							}
							else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
								return true;
							}
						}
	                    i++;
	                }
	            }
				return false;
			}
			else if(direction.equals("sw")){
				for(int i=pRow+1;i<kRow;i++){
	                for(int j = pCol-1;j>kCol;j--){
	                	for(int k = 0;k<15;k++){
	                		blockDestination = "" + i + j;
							blockCoordinate = convertBack(copy.White[k]) + " " + convertBack(blockDestination);
							if(convertBack(copy.White[k]).isEmpty()){
								continue;
							}
							else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
								return true;
							}
						}
	                    i++;
	                }
	            }
				return false;
			}
		}
		else if(kColor.equals("Black")){
			if(direction.equals("nor")){
				for(int i = pRow-1;i<kRow;i--){
					for(int j = 0;j<15;j++){
						blockDestination = "" + i + pCol;
						blockCoordinate = convertBack(copy.Black[j]) + " " + convertBack(blockDestination);
						if(convertBack(copy.Black[j]).isEmpty()){
							continue;
						}
						else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
							return true;
						}
					}
				}
				return false;
			}
			else if(direction.equals("sou")){
				for(int i = pRow+1;i>kRow;i++){
					for(int j = 0;j<15;j++){
						blockDestination = "" + i + pCol;
						blockCoordinate = convertBack(copy.Black[j]) + " " + convertBack(blockDestination);
						if(convertBack(copy.Black[j]).isEmpty()){
							continue;
						}
						else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
							return true;
						}
					}
				}
				return false;
			}
			else if(direction.equals("ea")){
				for(int i = pCol+1;i<kCol;i++){
					for(int j = 0;j<15;j++){
						blockDestination = "" + pRow + i;
						blockCoordinate = convertBack(copy.Black[j]) + " " + convertBack(blockDestination);
						if(convertBack(copy.Black[j]).isEmpty()){
							continue;
						}
						else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
							return true;
						}
					}
				}
				return false;
			}
			else if(direction.equals("we")){
				for(int i = pCol-1;i>kCol;i--){
					for(int j = 0;j<15;j++){
						blockDestination = "" + pRow + i;
						blockCoordinate = convertBack(copy.Black[j]) + " " + convertBack(blockDestination);
						if(convertBack(copy.Black[j]).isEmpty()){
							continue;
						}
						else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
							return true;
						}
					}
				}
				return false;
			}
			else if(direction.equals("ne")){
				for(int i=pRow-1;i>kRow;i--){
	                for(int j = pCol+1;j<kCol;j++){
	                	for(int k = 0;k<15;k++){
	                		blockDestination = "" + i + j;
							blockCoordinate = convertBack(copy.Black[k]) + " " + convertBack(blockDestination);
							if(convertBack(copy.Black[k]).isEmpty()){
								continue;
							}
							else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
								return true;
							}
						}
	                    i--;
	                }
	            }
				return false;
			}
			else if(direction.equals("nw")){
				for(int i=pRow-1;i>kRow;i--){
	                for(int j = pCol-1;j>kCol;j--){
	                	for(int k = 0;k<15;k++){
	                		blockDestination = "" + i + j;
							blockCoordinate = convertBack(copy.Black[k]) + " " + convertBack(blockDestination);
							if(convertBack(copy.Black[k]).isEmpty()){
								continue;
							}
							else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
								return true;
							}
						}
	                    i--;
	                }
	            }
				return false;
			}
			else if(direction.equals("se")){
				for(int i=pRow+1;i<kRow;i++){
	                for(int j = pCol+1;j<kCol;j++){
	                	for(int k = 0;k<15;k++){
	                		blockDestination = "" + i + j;
							blockCoordinate = convertBack(copy.Black[k]) + " " + convertBack(blockDestination);
							if(convertBack(copy.Black[k]).isEmpty()){
								continue;
							}
							else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
								return true;
							}
						}
	                    i++;
	                }
	            }
				return false;
			}
			else if(direction.equals("sw")){
				for(int i=pRow+1;i<kRow;i++){
	                for(int j = pCol-1;j>kCol;j--){
	                	for(int k = 0;k<15;k++){
	                		blockDestination = "" + i + j;
							blockCoordinate = convertBack(copy.Black[k]) + " " + convertBack(blockDestination);
							if(convertBack(copy.Black[k]).isEmpty()){
								continue;
							}
							else if(friendlyCheck(copy, blockCoordinate, true).equals("notInFriendlyCheck")){
								return true;
							}
						}
	                    i++;
	                }
	            }
				return false;
			}
		}
    	return false;
    }
    
    /**
     * This method checks if a piece after moving is putting friendly king in check
     * @param br the board to be examined
     * @param cdt the coordinates of the move
     * @param whiteTurn indicates whos turn it is
     * @return returns a string indicating whether a pieces move is invalid, puts friendly king in check or not
     * @throws IOException
     */
    public String friendlyCheck(Board br, String cdt, boolean whiteTurn) throws IOException {
    	Board copy = copyBoard(br);
    	String testCdt = "";
    	boolean result;
    	result = move(copy, cdt, whiteTurn,"");
        if (result == false){
        	return "invalid";
        }
        else{
        	if(whiteTurn ==true){
        		for(int i = 0;i <copy.Black.length;i++){
        			testCdt = convertBack(copy.Black[i]) + " " +convertBack(copy.White[15]);
        			if( convertBack(copy.Black[i]).isEmpty() ||convertBack(copy.White[15]).isEmpty()){
        				continue;
        			}
        			else if(move(copy,testCdt,false,"")==true){
        				return "inFriendlyCheck";
        			}
        		}	
        	}
        	else if(whiteTurn==false){
        		for(int i = 0;i <copy.White.length;i++){
        			testCdt = convertBack(copy.White[i]) + " " +  convertBack(copy.Black[15]);
        			if( convertBack(copy.White[i]).isEmpty() ||convertBack(copy.Black[15]).isEmpty()){
        				continue;
        			}
        			else if(move(copy,testCdt,true,"")==true){
        				return "inFriendlyCheck";
        			}
        		}	
        	}
        }
    	return "notInFriendlyCheck";
    }


    
    /**
     * This method checks all pieces if they have enemy king in check
     * @param br the board to be examined
     * @param whiteTurn indicates whos turn it is
     * @return returns a string indicating is an enemy king is in check or not
     * @throws IOException
     */
    public String enemyCheck(Board br, boolean whiteTurn) throws IOException {
    	Board copy = copyBoard(br);
    	String testCdt = "";
        if(whiteTurn==true){
        	for(int i = 0;i <copy.White.length;i++){
        		testCdt = convertBack(copy.White[i]) + " " +convertBack(copy.Black[15]);
        		if( convertBack(copy.White[i]).isEmpty() ||convertBack(copy.Black[15]).isEmpty()){
        			continue;
        		}
        		else if(move(copy,testCdt,true,"")==true){
        			return "enemyCheck";
        		}
        	}	
        }
        else if(whiteTurn==false){
        	for(int i = 0;i <copy.Black.length;i++){
        		testCdt = convertBack(copy.Black[i]) + " " +  convertBack(copy.White[15]);
        		if( convertBack(copy.Black[i]).isEmpty() ||convertBack(copy.White[15]).isEmpty()){
        			continue;
        		}
        		else if(move(copy,testCdt,false,"")==true){
        			return "enemyCheck";
        		}
        	}	
        }
    	return "enemyNotInCheck";
    }

	public String friendCheck(Board br, String coordinate, boolean whiteTurn) throws IOException {
		Board copy = br;
		String testCdt = "";
		boolean boolMove;




		boolean moving = move(copy,coordinate,whiteTurn,"");
		if(!moving){
			return "Invalid";
		}
		if(whiteTurn==false){
			for(int i = 0;i <copy.White.length;i++){
				testCdt = convertBack(copy.White[i]) + " " +convertBack(copy.Black[15]);
				if( convertBack(copy.White[i]).isEmpty()){
					continue;
				}
				else if(move(copy,testCdt,true,"")==true){
					return "friendCheck";
				}
			}
		}
		else if(whiteTurn==true){
			for(int i = 0;i <copy.Black.length;i++){
				testCdt = convertBack(copy.Black[i]) + " " +  convertBack(copy.White[15]);
				if( convertBack(copy.Black[i]).isEmpty() ||convertBack(copy.White[15]).isEmpty()){
					continue;
				}
				else if(move(copy,testCdt,false,"")==true){
					return "friendCheck";
				}
			}
		}

		return "notInFriendCheck";
	}


	/**
     * This method changes the coordinates in the array of strings to match the location of a piece
     * @param br the board to be examined
     * @param color the color of the piece
     * @param id the id number of the piece(index) in the array of strings
     * @param row2 the desired row to which the piece moves
     * @param col2 the desired column to which the piece moves
     * @return returns a board board object with the coordinates changed
     */
    public Board changePosition(Board br, String color, int id, int row2, int col2) {
        if (color.equals("White")) {
            if (row2 == -1 || col2 == -1) {
                br.White[id] = "";
            }
            else {
                br.White[id] = String.valueOf(row2) + String.valueOf(col2);
            }
        }
        else{
            if (row2 == -1 || col2 == -1) {
                br.Black[id] = "";
            }
            else {
                br.Black[id] = String.valueOf(row2) + String.valueOf(col2);
            }
        }
        return br;
    }
    
    /**
     * If a isValid method indicates a freemove, this methos performs that move
     * @param br the board to be examined
     * @param row1 the starting row
     * @param col1 the starting column
     * @param row2 the ending row
     * @param col2 the ending column
     * @param temp the tempoary piece which will be used for swapping
     * @param color the color pf the piece 
     * @return returns a board object after performing a valid move to a free spot on the board
     */
    public Board freeMove(Board br, int row1, int col1, int row2, int col2, Piece[] temp, String color){
        int id = br.board[row1][col1].getId();
        temp[0] = br.board[row2][col2];
        br.board[row2][col2] = br.board[row1][col1];
        br.board[row1][col1] = temp[0];
        br = changePosition(br, color, id, row2, col2);
        return br;
    }
    
    /**
     * This method is for promoting a pawns piece when applicable
     * @param br the board to be examined
     * @param row1 the starting row
     * @param col1 the starting column
     * @param row2 the row where the pawn is going for promotion
     * @param col2 the column the column is going for promotion
     * @param moveDetails the details of what type of promotion
     * @param color the color of the pawn
     * @param userInput the users input during promotion
     * @return returns board object after promoting a pawn
     */
    public Board promotion(Board br, int row1, int col1, int row2, int col2, String moveDetails, String color, String userInput,String pro){
        int pawnID = br.board[row1][col1].getId();
        char colorPiece = color.toLowerCase().charAt(0);
        char promo;
        if (moveDetails.equals("KP")){
            String killedColor = br.board[row2][col2].getColor();
            int killedID = br.board[row2][col2].getId();
            if (pro.equals("Queen")){
                br = changePosition(br, killedColor, killedID, -1, -1);
                br.board[row2][col2] = new Queen(color, colorPiece + "Q" , pawnID);
                br.board[row1][col1] = new Empty("##");
                br = changePosition(br, color, pawnID, row2, col2);
            }
            else{
                //promo = userInput.toUpperCase().charAt(6);
                if (pro.equals("Knight")){
                    br.board[row2][col2] = new Knight(color, colorPiece + "N", pawnID);
                    br.board[row1][col1] = new Empty("##");
                    br = changePosition(br, color, pawnID, row2, col2);
                }
                else if (pro.equals("Bishop")){
                    br.board[row2][col2] = new Bishop(color, colorPiece + "B", pawnID);
                    br.board[row1][col1] = new Empty("##");
                    br = changePosition(br, color, pawnID, row2, col2);
                }
                else if (pro.equals("Rook")){
                    br.board[row2][col2] = new Rook(color, colorPiece + "R", pawnID);
                    br.board[row1][col1] = new Empty("##");
                    br = changePosition(br, color, pawnID, row2, col2);
                }
                else{
					br = changePosition(br, killedColor, killedID, -1, -1);
					br.board[row2][col2] = new Queen(color, colorPiece + "Q" , pawnID);
					br.board[row1][col1] = new Empty("##");
					br = changePosition(br, color, pawnID, row2, col2);
				}

            }
        }
        else if (moveDetails.equals("Pro")){
            if (pro.equals("Queen")){
                br.board[row2][col2] = new Queen(color, colorPiece + "Q" , pawnID);
                br.board[row1][col1] = new Empty("##");
                br = changePosition(br, color, pawnID, row2, col2);
            }
            else{
                promo = userInput.toUpperCase().charAt(6);
                if (pro.equals("Knight")){
                    br.board[row2][col2] = new Knight(color, colorPiece + "N", pawnID);
                    br.board[row1][col1] = new Empty("##");
                    br = changePosition(br, color, pawnID, row2, col2);
                }
                else if (pro.equals("Bishop")){
                    br.board[row2][col2] = new Bishop(color, colorPiece + "B", pawnID);
                    br.board[row1][col1] = new Empty("##");
                    br = changePosition(br, color, pawnID, row2, col2);
                }
                else if (pro.equals("Rook")){
                    br.board[row2][col2] = new Rook(color, colorPiece + "R", pawnID);
                    br.board[row1][col1] = new Empty("##");
                    br = changePosition(br, color, pawnID, row2, col2);
                }
                else{
					br.board[row2][col2] = new Queen(color, colorPiece + "Q" , pawnID);
					br.board[row1][col1] = new Empty("##");
					br = changePosition(br, color, pawnID, row2, col2);
				}

            }
        }
        return br;
    }
    
    /**
     * This method performs a capturing of an enemy piece when it is applicable
     * @param br the board to be examined
     * @param row1 the starting row
     * @param col1 the starting column
     * @param row2 the row at which the enemy piece is to be captured
     * @param col2 the column at which the enemy piece is to be captured
     * @param temp the temp piece used for swapping object on the board
     * @param colorKiller the color of the piece that is performing the capture
     * @return returns a board object after performing the capturing of the enemy piecde
     */
    public Board Kill(Board br, int row1, int col1, int row2, int col2, Piece[] temp, String colorKiller){
        int IDKiller = br.board[row1][col1].getId();
        int IDKilled = br.board[row2][col2].getId();
        String colorKilled = br.board[row2][col2].getColor();
        br.board[row2][col2] = br.board[row1][col1];
        br.board[row1][col1] = new Empty("##");
        br = changePosition(br, colorKiller, IDKiller, row2, col2);
        br = changePosition(br, colorKilled, IDKilled, -1, -1);
        return br;
    }
    
    /**
     * This method performs the Empassant move when applicable
     * @param br the board to be examined
     * @param row1 the starting row
     * @param col1 the starting column
     * @param row2 the ending row
     * @param col2 the ending column
     * @param killerColor the color of the pawn that is performing empassant
     * @param tmp the tmp piece used for swapping pieces
     * @return returns a board object after permforming the empassant
     */
    public Board Epos(Board br, int row1,int col1,int row2,int col2,String killerColor,Piece[] tmp){
        int IDKiller = br.board[row1][col1].getId();
        int IDKilled;
        String killedColor;
        if(killerColor.equals("White")){
            IDKilled = br.board[row2+1][col2].getId();
            killedColor = br.board[row2+1][col2].getColor();
            tmp[0] = br.board[row2][col2];
            br.board[row2][col2] = br.board[row1][col1];
            br = changePosition(br,killerColor,IDKiller,row2,col2);
            br.board[row1][col1] = tmp[0];
            br.board[row2 + 1][col2] = new Empty("##");
            changePosition(br,killedColor,IDKilled,-1,-1);
        }
        else if(killerColor.equals("Black")){
            IDKilled = br.board[row2-1][col2].getId();
            killedColor = br.board[row2-1][col2].getColor();
            tmp[0] = br.board[row2][col2];
            br.board[row2][col2] = br.board[row1][col1];
            br = changePosition(br,killerColor,IDKiller,row2,col2);
            br.board[row1][col1] = tmp[0];
            br.board[row2 - 1][col2] = new Empty("##");
            changePosition(br,killedColor,IDKilled,-1,-1);
        }
        return br;
    }
    
    /**
     * This method performs castling when applicable
     * @param br the board object to be examined
     * @param row1 the starting row of the king
     * @param col1 the starting column of the king
     * @param row2 the ending row of the king
     * @param col2 the ending column of the king
     * @param moveDetails the details of what type of castling is taking place
     * @param kingColor the color of the king performing castling
     * @param tmp the tmp piece variable used for swapping
     * @return returns a new board object after perfoming castling
     */
    public Board castling(Board br, int row1,int col1,int row2, int col2, String moveDetails, String kingColor, Piece[] tmp){
        int kingID = br.board[row1][col1].getId();
        int rookID;
        if(moveDetails.equals("lc")){
            rookID = br.board[row2][col2-2].getId();
            tmp[0] = br.board[row2][col2];
            br.board[row2][col2] = br.board[row1][col1];
            br.board[row1][col1] = tmp[0];
            br = changePosition(br,kingColor,kingID,row2,col2);
            tmp[0] = br.board[row2][col2+1];
            br.board[row2][col2+1] =br.board[row2][col2-2];
            br.board[row2][col2-2]= tmp[0];
            br = changePosition(br,kingColor,rookID,row2,col2+1);
            br.board[row2][col2+1].setMoved(true);
        }
        else if(moveDetails.equals("rc")){
            rookID = br.board[row2][col2+1].getId();
            tmp[0] = br.board[row2][col2];
            br.board[row2][col2] = br.board[row1][col1];
            br.board[row1][col1] = tmp[0];
            br = changePosition(br,kingColor,kingID,row2,col2);
            tmp[0] = br.board[row2][col2-1];
            br.board[row2][col2-1] =br.board[row2][col2+1];
            br.board[row2][col2+1]= tmp[0];
            br = changePosition(br,kingColor,rookID,row2,col2-1);
            br.board[row2][col2-1].setMoved(true);
        }
        br.board[row2][col2].setMoved(true);
        return br;
    }
	public int[] getCoordinate(String cdt){
		int row = Character.getNumericValue(cdt.charAt(0));
		int col = Character.getNumericValue(cdt.charAt(1));
		int[] cor = {row,col};
		return cor;
	}
    /**
     * This method converts a string of numbers to chess coordinates
     * @param cdt the string of number coordinates
     * @return this method returns a string containging the chess coordinate
     */
    public String convertBack(String cdt){
    	if(cdt.equals("")){
    		return "";
    	}
		String finCdt = "";
		int[] rowArray = {8,7,6,5,4,3,2,1};
		int row = Character.getNumericValue(cdt.charAt(0));
    	int corNum = rowArray[row];
    	int col = Character.getNumericValue(cdt.charAt(1));
		int i = col+1;
		finCdt = "" + (char)(i+'a'-1) + corNum;
		return finCdt;
    }
    
    /**
     * This method copies a given board object into a new copied version of a board
     * @param br the board object to be copied
     * @return returns a new copied version of the board provided
     */
    public Board copyBoard(Board br){
    	Board copy = new Board();
    	int i,j;
    	for(i =0;i<8;i++){
    		for(j = 0;j<8;j++){
    			copy.board[i][j] = br.board[i][j];
    		}
    	}
    	
    	for(i = 0;i<16;i++){
    		copy.Black[i] = br.Black[i];
    		copy.White[i] = br.White[i];
    	}
    	return copy;
    }
    
    /**
     * This method converts chess coordinates into int rows and columns
     * @param cdt the chess string coordinate provided by the player
     * @return returns an array of ints containg rows and columns of the pieces location, and destination
     */
    public int[] convert(String cdt) {
        int[] cor = new int[4];
        int[] rows = { 7, 6, 5, 4, 3, 2, 1, 0 };
        int row = Character.getNumericValue(cdt.charAt(1));
        cor[0] = rows[row - 1];
        switch (cdt.charAt(0)) {
            case 'a':
                cor[1] = 0;
                break;
            case 'b':
                cor[1] = 1;
                break;
            case 'c':
                cor[1] = 2;
                break;
            case 'd':
                cor[1] = 3;
                break;
            case 'e':
                cor[1] = 4;
                break;
            case 'f':
                cor[1] = 5;
                break;
            case 'g':
                cor[1] = 6;
                break;
            case 'h':
                cor[1] = 7;
                break;
        }
        int row2 = Character.getNumericValue(cdt.charAt(4));
        cor[2] = rows[row2 - 1];

        switch (cdt.charAt(3)) {
            case 'a':
                cor[3] = 0;
                break;
            case 'b':
                cor[3] = 1;
                break;
            case 'c':
                cor[3] = 2;
                break;
            case 'd':
                cor[3] = 3;
                break;
            case 'e':
                cor[3] = 4;
                break;
            case 'f':
                cor[3] = 5;
                break;
            case 'g':
                cor[3] = 6;
                break;
            case 'h':
                cor[3] = 7;
                break;
        }
        return cor;
    }
    
    /**
     * This method is used for swapping pieces for testing
     * @param br the board to be examined
     * @param row1 the starting row
     * @param col1 the starting column
     * @param row2 the ending row
     * @param col2 the ending column
     * @return returns a board object with the swapped pieces
     * @throws IOException
     */
    public Board swap(Board br, int row1, int col1, int row2, int col2) throws IOException {
        Piece[] tmp = new Piece[1];
        tmp[0] = br.board[row2][col2];
        br.board[row2][col2] = br.board[row1][col1];
        br.board[row1][col1] = tmp[0];
        br.board.toString();
        return br;
    }
}