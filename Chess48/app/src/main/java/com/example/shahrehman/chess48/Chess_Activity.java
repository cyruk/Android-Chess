package com.example.shahrehman.chess48;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.graphics.Color;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.PopupMenu;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Chess_Activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private static final String TAG = "rahimMessage";

    GridView chessGrid;
    private Button chessR,chessHelp,chessRedo,chessResign,chessDraw;
    private  TextView chHelp,chTurn;


    ImageAdapter im;
    public  boolean whiteTurn= true;
    public int firstSelectedPosition = -1;
    public int fir = -1;
    public int secondSelectedPosition = -1;
    public boolean checkMate=false;
    public boolean check = false;
    public boolean moving;
    Boolean redone = false;
    Board redo;
    Board br = new Board();
    Game game = new Game();
    Board copy;
    String spec = "";
    String coordinate = "";
    String moveDetails = "";
    String help = "";
    boolean firstMove = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //Board copy;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_activity);

        chessHelp = (Button)findViewById(R.id.chess_help);
        chessRedo = (Button)findViewById(R.id.chess_redo);
        chessResign = (Button)findViewById(R.id.chess_res);
        chessDraw = (Button)findViewById(R.id.chess_draw);
        chHelp = (TextView)findViewById(R.id.ch_hel);
        chTurn = (TextView)findViewById(R.id.ch_turn);

        im = new ImageAdapter(this);
        chessR = (Button) findViewById(R.id.chessR);
        chessR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                launchMain();
            }
        });
        chessHelp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                copy = new Board(br);
                try {
                    help =game.help(br,whiteTurn," ");
                    chHelp.setText(help);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        chessRedo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(redone==false &&firstMove) {
                    br = new Board(redo);
                    whiteTurn= changeTurn(whiteTurn);
                    chTurn.setText(convertBoolean(whiteTurn));
                    ImageAdapter.update(br,fir, secondSelectedPosition, moveDetails);
                    chessGrid.setAdapter(new ImageAdapter(ImageAdapter.getContext()));
                    redone= true;
                }
            }
        });
        chessDraw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        chHelp.setText("");
        chessGrid = (GridView) findViewById(R.id.chessBoard);
        chessGrid.setAdapter(im);
        chTurn.setText(convertBoolean(whiteTurn));
        chessGrid.getCheckedItemPosition();
        chessGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //String coordinate = "";
               // String moveDetails = "";
                if(firstSelectedPosition==-1) {
                    firstSelectedPosition=position;
                    chessGrid.setSelector(new ColorDrawable(Color.YELLOW));
                    secondSelectedPosition=-1;
                    fir= -1;
                }
                else{
                    secondSelectedPosition = position;
                    chessGrid.setSelector(new ColorDrawable(Color.YELLOW));
                    //chessGrid.setSelection(8);
                    //chessGrid.setSelector(new ColorDrawable(Color.RED));
                    fir=firstSelectedPosition;
                    coordinate = convert(fir) +" " +  convert(secondSelectedPosition);
                    Log.i(TAG, coordinate);

                    try{

                        if(checkMate){
                            Toast.makeText(Chess_Activity.this, "CheckMate! "+ convertBoolean(changeTurn(whiteTurn)) + "Wins" ,
                                    Toast.LENGTH_SHORT).show();
                            return;

                        }
                        else if(check){
                            Log.i(TAG,"Check is truee" + " turn: " + whiteTurn);
                            copy = new Board(br);
                            if(game.friendCheck(copy,coordinate,whiteTurn).equals("friendCheck")){
                                Log.i(TAG,"Friendcheck stilll there");
                                Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " is still in Check!",
                                        Toast.LENGTH_SHORT).show();
                                moving = false;
                            }
                            else{
                                check = false;
                            }
                        }

                        if(!check && !checkMate){

                            copy = new Board(br);
                            if(game.friendCheck(copy,coordinate,whiteTurn).equals("friendCheck")) {
                                Log.i(TAG, "Friendly check in there");
                                Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " Kinng is in check fool!!",
                                        Toast.LENGTH_SHORT).show();
                                //chessGrid.setSelector(new ColorDrawable(Color.RED));
                                moving = false;
                            }
                            else {
                                Log.i(TAG,"Original is");
                                br.toString();
                                Log.i(TAG,"***********************************");
                                Log.i(TAG,"Copy is");
                                copy.toString();
                                /*copy = new Board(br);
                                Log.i(TAG,"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                                moving = game.move(copy,coordinate,whiteTurn, "Rook");
                                moveDetails = game.moveDetails;
                                Log.i(TAG,moveDetails);
                                if(moveDetails.equals("KP")|| moveDetails.equals("Pro")){
                                    //showPopUp(v);
                                    Log.i(TAG,spec);
                                    Toast.makeText(Chess_Activity.this, spec,
                                            Toast.LENGTH_SHORT).show();
                                }
                                //else {//Log.i(TAG,spec);*/
                                    redo = new Board(br);
                                    moving = game.move(br, coordinate, whiteTurn, spec);
                                    moveDetails = game.moveDetails;
                                    firstMove = true;
                                    redone=false;
                                //}
                            }
                        }

                        if(moving!=true) {
                            if(secondSelectedPosition!=-1)
                                //chessGrid.setSelector(new ColorDrawable(Color.RED));
                            Toast.makeText(Chess_Activity.this, "Move is invalid",
                                    Toast.LENGTH_SHORT).show();


                        }
                        else {
                            copy = new Board(br);
                            //String help = game.help(copy,whiteTurn,coordinate);
                            boolean checkm = game.checkMate(copy, whiteTurn);
                            copy = new Board(copy);
                            String help = game.help(copy,changeTurn(whiteTurn),coordinate);
                            if (checkm == true&&help.equals("No Help")){
//                                copy = new Board(copy);
  //                              String help = game.help(copy,changeTurn(whiteTurn),coordinate);

                                Toast.makeText(Chess_Activity.this, convertBoolean(changeTurn(whiteTurn)) + " is in CheckMate!",
                                        Toast.LENGTH_SHORT).show();
                                checkMate = true;
                                ImageAdapter.update(br,fir, secondSelectedPosition, moveDetails);
                                whiteTurn=changeTurn(whiteTurn);
                            }
                            else if(game.friendCheck(copy,coordinate,changeTurn(whiteTurn)).equals("friendCheck")){
                                //chessGrid.setSelector(new ColorDrawable(Color.RED));
                                Log.i(TAG,"Friendly check in there");
                                Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " Kinng is in check fool!!",
                                        Toast.LENGTH_SHORT).show();

                            }
                            else if (game.enemyCheck(br, whiteTurn).equals("enemyCheck")) {
                                ImageAdapter.update(br,fir, secondSelectedPosition, "enemyCheck");
                                Toast.makeText(Chess_Activity.this, convertBoolean(changeTurn(whiteTurn)) + " is in Check!",
                                        Toast.LENGTH_SHORT).show();
                                check = true;
                                whiteTurn=changeTurn(whiteTurn);
                                }
                            else {

                                ImageAdapter.update(br,fir, secondSelectedPosition, moveDetails);
                                whiteTurn=changeTurn(whiteTurn);
                            }
                            resetEpos(br,changeTurn(whiteTurn));
                            Log.i(TAG,"turn: " + whiteTurn);
                            br.toString();
                        }
                        Log.i(TAG,"validMove: "+moving + " check: " + check);
                        Log.i(TAG,"");
                    }
                    catch (IOException e){}
                    chessGrid.setAdapter(new ImageAdapter(ImageAdapter.getContext()));
                    chTurn.setText(convertBoolean(whiteTurn));
                    chHelp.setText("");
                    firstSelectedPosition=-1;
                }
            }

        });

    }

    public boolean onMenuItemClick(MenuItem item) {

        if(item.getItemId()==R.id.b_ishop){
            spec = "Bishop";
        }
        else if(item.getItemId()==R.id.q_ueen){
            spec = "Queen";
        }
        else if(item.getItemId()==R.id.k_night){
            spec = "Knight";
        }
        else if(item.getItemId()==R.id.r_ook){
            spec = "Rook";
        }
        Log.i(TAG,"FINAL COPY IS");
        copy.toString();
        return true;
    }

    public void showPopUp(View v){
        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(Chess_Activity.this);
        MenuInflater inflator = popup.getMenuInflater();
        inflator.inflate(R.menu.popup_menu,popup.getMenu());
        popup.show();
    }

    public void reset(){
        for(int i = 0; i<64;i++) {
            if (i < 8) {
                if (i == 0 || i == 7) {
                    ImageAdapter.chessPiece[i] = R.drawable.blrook;
                } else if (i == 1 || i == 6) {
                    ImageAdapter.chessPiece[i] = R.drawable.blknight;
                } else if (i == 2 || i == 5) {
                    ImageAdapter.chessPiece[i] = R.drawable.bishop;
                } else if (i == 3) {
                    ImageAdapter.chessPiece[i] = R.drawable.blqueen;
                } else if (i == 4) {
                    ImageAdapter.chessPiece[i] = R.drawable.blking;
                }
            } else if (i > 7 && i < 16) {
                ImageAdapter.chessPiece[i] = R.drawable.blpawn;
            } else if (i > 15 && i < 48) {
                ImageAdapter.chessPiece[i] = ImageAdapter.chessboardIds[i];
            } else if (i > 47 && i < 56) {
                ImageAdapter.chessPiece[i] = R.drawable.whpawn;
            } else if (i > 55) {
                if (i == 56 || i == 63) {
                    ImageAdapter.chessPiece[i] = R.drawable.whrook;
                } else if (i == 57 || i == 62) {
                    ImageAdapter.chessPiece[i] = R.drawable.whknight;
                } else if (i == 58 || i == 61) {
                    ImageAdapter.chessPiece[i] = R.drawable.wishop;
                } else if (i == 59) {
                    ImageAdapter.chessPiece[i] = R.drawable.whqueen;
                } else if (i == 60) {
                    ImageAdapter.chessPiece[i] = R.drawable.whking;
                }
            }
        }

    }

    private void launchMain(){
        reset();
        Intent intent = new Intent(this, Main_Activity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        reset();
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

    public void writeApp(Board boardApp) throws IOException {
        String FILE_NAME = "test.dat";
        FileOutputStream fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(boardApp);
        System.out.println("WriteApp has been calledbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        fos.close();
        oos.close();
    }

    public Board readApp() throws IOException, ClassNotFoundException {
        String FILE_NAME = "test.dat";
        FileInputStream fis = openFileInput(FILE_NAME);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Board readBoard = (Board)ois.readObject();
        System.out.println("ReadApp has been called");
        fis.close();
        ois.close();
        return readBoard;
    }




}
