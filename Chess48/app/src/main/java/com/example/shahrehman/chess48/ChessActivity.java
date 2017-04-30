package com.example.shahrehman.chess48;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;

public class ChessActivity extends AppCompatActivity {

    private static final String TAG = "rahimMessage";
    GridView chessGrid;
/*9999999999999999999999999999999999999999*/
    public  boolean whiteTurn= true;
    //private int whiteTurn;
    public int firstSelectedPosition = -1;
    public int fir = -1;
    public int secondSelectedPosition = -1;
    public boolean checkMate=false;
    public boolean check = false;
    public boolean moving;
    TextView turn;
    Board br = new Board();
    Game game = new Game();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //boolean white = true;
     /*   turn = new TextView(this);
        turn.setPadding(10, 10, 10, 10);
        msg.setGravity(Gravity.);*/
        //turn.setBackgroundResource(R.drawable.rectangle);
        //msg.setText(message);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_activity);
        //Log.i(TAG,"" + "First " + ImageAdapter.getFirst() + " Fir " + ImageAdapter.fir + " Second " + ImageAdapter.getSecond());
        //final Board br = new Board();
        //final Game game = new Game();
        ImageAdapter im = new ImageAdapter(this);
        //im.createBoard(br);
        chessGrid = (GridView) findViewById(R.id.chessBoard);
        chessGrid.setAdapter(im);
        chessGrid.getCheckedItemPosition();
        chessGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String coordinate = "";
                String moveDetails = "";
                if(firstSelectedPosition==-1) {
                    firstSelectedPosition=position;
                    chessGrid.setSelector(new ColorDrawable(Color.YELLOW));
                    secondSelectedPosition=-1;
                    fir= -1;
                }
                else{
                    secondSelectedPosition = position;
                    chessGrid.setSelector(new ColorDrawable(Color.YELLOW));
                    fir=firstSelectedPosition;
                    coordinate = convert(fir) +" " +  convert(secondSelectedPosition);
                    Log.i(TAG, coordinate);

                    try{
                        //prin(br);
                        if(checkMate){
                            Toast.makeText(ChessActivity.this, "CheckMate! "+ convertBoolean(changeTurn(whiteTurn)) + "Wins" ,
                                    Toast.LENGTH_SHORT).show();
                            return;

                        }
                        else if(check){
                            //moving = game.move(br,coordinate,white);
                            Log.i(TAG,"Check is truee" + " turn: " + whiteTurn);
                            if(game.enemyTestCheck(br,coordinate,changeTurn(whiteTurn)).equals("enemyCheck")){
                                Log.i(TAG,"Friendcheck stilll there");
                                Toast.makeText(ChessActivity.this, convertBoolean(whiteTurn) + " is still in Check!",
                                        Toast.LENGTH_SHORT).show();
                                moving = false;
                            }
                            else{
                                check = false;
                            }
                        }

                        if(!check && !checkMate){
                            if(game.enemyTestCheck(br,coordinate,changeTurn(whiteTurn)).equals("enemyCheck")) {
                                Log.i(TAG, "Friendly check in there");
                                Toast.makeText(ChessActivity.this, convertBoolean(whiteTurn) + " Kinng is in check fool!!",
                                        Toast.LENGTH_SHORT).show();
                                moving = false;
                            }
                            else {
                                moving = game.move(br, coordinate, whiteTurn);
                                moveDetails = game.moveDetails;
                            }
                        }

                        if(moving!=true) {
                            Toast.makeText(ChessActivity.this, "Move is invalid",
                                    Toast.LENGTH_SHORT).show();

                        }
                        else {

                            if (game.checkMate(br, whiteTurn) == true) {
                                Toast.makeText(ChessActivity.this, convertBoolean(changeTurn(whiteTurn)) + " is in CheckMate!",
                                        Toast.LENGTH_SHORT).show();
                                checkMate = true;
                                ImageAdapter.update(br,fir, secondSelectedPosition, moveDetails);
                                whiteTurn=changeTurn(whiteTurn);
                                br.writeApp(br);
                            }
                            else if(game.enemyTestCheck(br,coordinate,changeTurn(whiteTurn)).equals("enemyCheck")){
                                Log.i(TAG,"Friendly check in there");
                                Toast.makeText(ChessActivity.this, convertBoolean(whiteTurn) + " Kinng is in check fool!!",
                                        Toast.LENGTH_SHORT).show();

                            }
                            else if (game.enemyCheck(br, whiteTurn).equals("enemyCheck")) {
                                ImageAdapter.update(br,fir, secondSelectedPosition, "enemyCheck");
                                Toast.makeText(ChessActivity.this, convertBoolean(changeTurn(whiteTurn)) + " is in Check!",
                                        Toast.LENGTH_SHORT).show();
                                check = true;
                                whiteTurn=changeTurn(whiteTurn);
                                br.writeApp(br);
                            }
                            else {

                                ImageAdapter.update(br,fir, secondSelectedPosition, moveDetails);
                                whiteTurn=changeTurn(whiteTurn);
                                br.writeApp(br);
                            }
                            resetEpos(br,changeTurn(whiteTurn));
                            Log.i(TAG,"turn: " + whiteTurn);
                            br.toString();
                            //white=changeTurn(white);

                        }
                        Log.i(TAG,"validMove: "+moving + " check: " + check);
                        Log.i(TAG,"");
                    }
                    catch (IOException e){}
                    chessGrid.setAdapter(new ImageAdapter(ImageAdapter.getContext()));
                    firstSelectedPosition=-1;
                }
            }

        });

    }
    @Override
    public void onBackPressed() {
        //br.writeApp(br);
        for(int i = 0; i<64;i++){
            if(i<8){
                if(i ==0 ||i == 7){
                    ImageAdapter.chessPiece[i] = R.drawable.blrook;
                }
                else if(i ==1 ||i ==6){
                    ImageAdapter.chessPiece[i] = R.drawable.blknight;
                }
                else if(i ==2 ||i ==5){
                    ImageAdapter.chessPiece[i] = R.drawable.bishop;
                }
                else if(i ==3){
                    ImageAdapter.chessPiece[i] = R.drawable.blqueen;
                }
                else if(i==4){
                    ImageAdapter.chessPiece[i] = R.drawable.blking;
                }
            }
            else if(i>7&&i<16) {
                ImageAdapter.chessPiece[i] = R.drawable.blpawn;
            }
            else if(i>15 && i<48){
                ImageAdapter.chessPiece[i] = ImageAdapter.chessboardIds[i];
            }
            else if(i>47 && i<56){
                ImageAdapter.chessPiece[i] = R.drawable.whpawn;
            }
            else if(i>55){
                if(i ==56 ||i == 63){
                    ImageAdapter.chessPiece[i] = R.drawable.whrook;
                }
                else if(i ==57 ||i ==62){
                    ImageAdapter.chessPiece[i] = R.drawable.whknight;
                }
                else if(i ==58 ||i ==61){
                    ImageAdapter.chessPiece[i] = R.drawable.wishop;
                }
                else if(i ==59){
                    ImageAdapter.chessPiece[i] = R.drawable.whqueen;
                }
                else if(i==60){
                    ImageAdapter.chessPiece[i] = R.drawable.whking;
                }
            }

        }
        Intent intent = new Intent(this, Main_Activity.class);
        startActivity(intent);
    }

    public boolean isHash(int row, int col){
        if (row == 8 || col == 8){
            return false;
        }
        if (row % 2 == 0){
            if (col % 2 != 0){
                return true;
            }
        }
        else if (row % 2 != 0){
            if (col % 2 == 0){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a string is a number or letter
     * @param str the string which is examined
     * @return true if the str is a letter or number otherwise false
     */
    public boolean isNumOrLetter(String str){
        if(str.isEmpty()){
            return false;
        }
        char c = str.charAt(0);
        if(Character.isLetter(c)|| Character.isDigit(c)){
            return true;
        }
        return false;
    }

    public void analayzeMove(){

    }

    public String convert(int position){
        String[] letter = {"a","b","c","d","e","f","g","h"};
        String coordinate = "";
        if(position>-1&& position <8){
            coordinate = letter[position] + ""+ 8;
        }
        else if(position >7 &&position<16){
            coordinate = letter[(position)-8] + ""+ 7;
        }
        else if(position >15 &&position<24){
            coordinate = letter[position-16] + ""+ 6;
        }
        else if(position >23 &&position<32){
            coordinate = letter[position-24] + ""+ 5;
        }
        else if(position >31 &&position<40){
            coordinate = letter[position-32] + ""+ 4;
        }
        else if(position >39 &&position<48){
            coordinate = letter[position-40] + ""+ 3;
        }
        else if(position >47 &&position<56){
            coordinate = letter[position-48] + ""+ 2;
        }
        else if(position >55 &&position<64){
            coordinate = letter[position-56] + ""+ 1;
        }
        return coordinate;
    }


    public boolean changeTurn(boolean whiteTurn){
        if(whiteTurn==true){
            return false;
        }
        return true;
    }

    public void resetEpos(Board br, boolean whiteTurn){
        if(whiteTurn){
            for(int i = 0;i<8;i++){
                if(br.board[3][i].getClass().isInstance(new Pawn())){
                    br.board[3][i].ePos = false;
                }
            }
        }
        else if(!whiteTurn){
            for(int i = 0;i<8;i++){
                if(br.board[4][i].getClass().isInstance(new Pawn())){
                    br.board[4][i].ePos = false;
                }
            }
        }
    }

    public String convertBoolean(boolean whiteTurn){

        if(whiteTurn==true){
            return "White";
        }
        return "Black";
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }




}
