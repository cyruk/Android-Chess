package com.example.shahrehman.chess48;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.Serializable;

public class Chess_Activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, Serializable {

    private static final String TAG = "rahimMessage";

    GridView chessGrid;
    private Button chessR,chessHelp,chessRedo,chessResign,chessDraw,save;
    private  TextView chHelp,chTurn;


    ImageAdapter im;
    public  boolean whiteTurn= true;
    boolean draw = false;
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
    boolean resign = false;
    public int counter = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //Board copy;
        if (getIntent() != null && getIntent().getExtras() != null)
        {
            Intent intent = getIntent();
            String message = intent.getStringExtra(Replay_Activity.playGame);
            try {
                br = readApp(message);
                whiteTurn = br.getTurn();
                checkMate = br.getCheckMate();
                resign = br.getResign();
                check = br.getCheck();
                firstMove = br.getFirstMove();
                if (!firstMove && resign)
                {
                    whiteTurn = changeTurn(whiteTurn);
                }
                draw = br.getDraw();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_activity);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(br.getSaved()==true){
                    try {
                        writeApp2(br, br.getName().toString());
                        Toast.makeText(Chess_Activity.this, "File saved as: " + br.getName() ,
                                Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    save(view);
                }
            }
        });
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

                confirmReturnDialog(ImageAdapter.getContext());
            }
        });

        chessResign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkMate){
                    Toast.makeText(Chess_Activity.this, "CheckMate! "+ convertBoolean(changeTurn(whiteTurn)) + "Wins" ,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(resign){
                    Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " Resigned!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(draw==true){
                    Toast.makeText(Chess_Activity.this, "Game is at a Draw!!!!!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                confirmResignDialog(ImageAdapter.getContext());
            }
        });
        chessHelp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkMate){
                    Toast.makeText(Chess_Activity.this, "CheckMate! "+ convertBoolean(changeTurn(whiteTurn)) + "Wins" ,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(resign){
                    Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " Resigned!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(draw==true){
                    Toast.makeText(Chess_Activity.this, "Game is at a Draw!!!!!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
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
                if(checkMate){
                    Toast.makeText(Chess_Activity.this, "CheckMate! "+ convertBoolean(changeTurn(whiteTurn)) + "Wins" ,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(resign){
                    Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " Resigned!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(draw==true){
                    Toast.makeText(Chess_Activity.this, "Game is at a Draw!!!!!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(counter == 0){
                    counter++;
                    return;
                }
                if(redone==false &&firstMove) {
                    br = new Board(redo);
                    whiteTurn= changeTurn(whiteTurn);
                    chTurn.setText(convertBoolean(whiteTurn));
                    br.setTurn(whiteTurn);
                    ImageAdapter.update(br,fir, secondSelectedPosition, moveDetails);
                    chessGrid.setAdapter(new ImageAdapter(ImageAdapter.getContext()));
                    redone= true;
                }
            }
        });
        chessDraw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkMate){
                    Toast.makeText(Chess_Activity.this, "CheckMate! "+ convertBoolean(changeTurn(whiteTurn)) + "Wins" ,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(resign){
                    Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " Resigned!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Draw");
                builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        draw = true;
                        br.setDraw(true);
                        save(findViewById(android.R.id.content));
                        Toast.makeText(Chess_Activity.this, "Draw!!" ,
                                Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });

        chHelp.setText("");
        chessGrid = (GridView) findViewById(R.id.chessBoard);

        ImageAdapter.update(br,fir, secondSelectedPosition, moveDetails);
        chessGrid.setAdapter(new ImageAdapter(ImageAdapter.getContext()));


        chTurn.setText(convertBoolean(whiteTurn));
        chessGrid.getCheckedItemPosition();
        chessGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(draw==true){
                    Toast.makeText(Chess_Activity.this, "Game is at a Draw!!!!!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(checkMate){
                    Toast.makeText(Chess_Activity.this, "CheckMate! "+ convertBoolean(changeTurn(whiteTurn)) + "Wins" ,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(resign){
                    Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " Resigned!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

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
                        if(!whiteTurn&&!firstMove){
                            whiteTurn = changeTurn(whiteTurn);
                            br.setTurn(whiteTurn);
                            chTurn.setText(""+convertBoolean(whiteTurn));
                            Toast.makeText(Chess_Activity.this, "Awkward..White tried to draw on first move, but white always goes first.. Giving White another chance",
                                    Toast.LENGTH_LONG).show();

                            return;
                        }
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
                                Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " King is in check!",
                                        Toast.LENGTH_SHORT).show();
                                moving = false;
                            }
                            else {
                                Log.i(TAG,"Original is");
                                br.toString();
                                Log.i(TAG,"***********************************");
                                Log.i(TAG,"Copy is");
                                copy.toString();
                                    redo = new Board(br);
                                    moving = game.move(br, coordinate, whiteTurn, spec);
                                    moveDetails = game.moveDetails;
                                    firstMove = true;
                                    br.setFirstMove(true);
                                    redone=false;
                                    writeApp(br);
                                //}
                            }
                        }

                        if(moving!=true) {
                            if(secondSelectedPosition!=-1)
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
                                Toast.makeText(Chess_Activity.this, convertBoolean(changeTurn(whiteTurn)) + " is in CheckMate!",
                                        Toast.LENGTH_SHORT).show();
                                checkMate = true;
                                br.setCheckMate(true);
                                ImageAdapter.update(br,fir, secondSelectedPosition, moveDetails);
                                whiteTurn=changeTurn(whiteTurn);
                                br.setTurn(whiteTurn);
                                writeApp(br);
                                save(findViewById(android.R.id.content));
                            }
                            else if(game.friendCheck(copy,coordinate,changeTurn(whiteTurn)).equals("friendCheck")){
                                Log.i(TAG,"Friendly check in there");
                                Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " Kinng is in check fool!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if (game.enemyCheck(br, whiteTurn).equals("enemyCheck")) {
                                ImageAdapter.update(br,fir, secondSelectedPosition, "enemyCheck");
                                Toast.makeText(Chess_Activity.this, convertBoolean(changeTurn(whiteTurn)) + " is in Check!",
                                        Toast.LENGTH_SHORT).show();
                                check = true;
                                br.setCheck(true);
                                whiteTurn=changeTurn(whiteTurn);
                                br.setTurn(whiteTurn);
                                writeApp(br);
                                }
                            else {
                                ImageAdapter.update(br,fir, secondSelectedPosition, moveDetails);
                                whiteTurn=changeTurn(whiteTurn);
                                br.setTurn(whiteTurn);
                                writeApp(br);
                            }
                            resetEpos(br,changeTurn(whiteTurn));
                            writeApp(br);
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
                    try {
                        writeApp(br);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

    }

    private void confirmReturnDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder
                .setMessage("Are you sure you want to return??")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        launchMain();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }
    private void confirmResignDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder
                .setMessage(convertBoolean(whiteTurn) + " are you sure you want to Resign?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        resign = true;
                        br.setResign(true);
                        save(findViewById(android.R.id.content));
                        Toast.makeText(Chess_Activity.this, convertBoolean(whiteTurn) + " Resigned!",
                                Toast.LENGTH_SHORT).show();
                        try {
                            writeApp(br);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
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
        String FILE_NAME = "autosave.txt";
        FileOutputStream fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(boardApp);
        fos.close();
        oos.close();
    }

    public void writeApp2(Board boardApp, String name) throws IOException {
        String FILE_NAME = name;
        FileOutputStream fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(boardApp);
        fos.close();
        oos.close();
    }

    public Board readApp(String fileName) throws IOException, ClassNotFoundException {
        String FILE_NAME = fileName;
        FileInputStream fis = openFileInput(FILE_NAME);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Board readBoard = (Board)ois.readObject();
        fis.close();
        ois.close();
        return readBoard;
    }



    public void save(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Save");
        final EditText input = new EditText(view.getContext());
        builder.setView(input);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                br.setSaved(true,input.getText().toString());
                try {
                    writeApp2(br, input.getText().toString());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


}
