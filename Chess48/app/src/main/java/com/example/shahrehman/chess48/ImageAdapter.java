package com.example.shahrehman.chess48;

/**
 * Created by Shah Rehman on 4/26/2017.
 */

import android.content.ClipData;
import android.content.Context;
import android.provider.Settings;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.util.Log;

import static android.R.attr.button;
//import static com.example.shahrehman.chess48.ImageAdapter.firstSelectedPosition;
//import static com.example.shahrehman.chess48.ImageAdapter.secondSelectedPosition;
import static com.example.shahrehman.chess48.R.id.piece;


public class ImageAdapter extends BaseAdapter {

    private static Context mContext;
    private LayoutInflater mInflater;

    FrameLayout flcp;
    ImageView imgvcp = null;
    /*public static boolean white= true; 888888888888888888888888888888888888888
    private int whiteTurn;
    public static int firstSelectedPosition = -1;
    public static int fir = -1;
    public static int secondSelectedPosition = -1;*/

    //final Object data = getLastNonConfigurationInstance();
    // CHESSBOARD

    private static final String TAG = "rahim";

    public static Integer[] chessboardIds = {
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
    };
    public static Integer[] chessPiece = {
            R.drawable.blrook, R.drawable.blknight, R.drawable.bishop, R.drawable.blqueen,
            R.drawable.blking, R.drawable.bishop, R.drawable.blknight, R.drawable.blrook,
            R.drawable.blpawn, R.drawable.blpawn, R.drawable.blpawn, R.drawable.blpawn,
            R.drawable.blpawn, R.drawable.blpawn, R.drawable.blpawn, R.drawable.blpawn,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.white, R.drawable.brown, R.drawable.white, R.drawable.brown,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.brown, R.drawable.white, R.drawable.brown, R.drawable.white,
            R.drawable.whpawn,  R.drawable.whpawn,  R.drawable.whpawn,  R.drawable.whpawn,
            R.drawable.whpawn,  R.drawable.whpawn,  R.drawable.whpawn,  R.drawable.whpawn,
            R.drawable.whrook,  R.drawable.whknight,  R.drawable.wishop,  R.drawable.whqueen,
            R.drawable.whking, R.drawable.wishop, R.drawable.whknight, R.drawable.whrook,

    };
    //public Integer[] chessPiece = new Integer[64];

    public ImageAdapter(Context c) {
        mContext = c;
        Context context = c.getApplicationContext();
        mInflater = LayoutInflater.from(context);
        Log.i(TAG,"Got into the construcvctor");
        //createBoard(br);
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

    public static void update(Board br, int first, int second, String specs) {

        Log.i(TAG,"starrting to update");
     /*      if(specs.equals("Epos")){
                if(first-second>0){
                    ImageAdapter.chessPiece[second+8] = ImageAdapter.chessboardIds[second+8];
                }
                else{
                    ImageAdapter.chessPiece[second-8] = ImageAdapter.chessboardIds[second-8];
                }
            }
            ImageAdapter.chessPiece[second] = chessPiece[first];
            //ImageAdapter.chessPiece[second] = R.drawable.blpawn;
            ImageAdapter.chessPiece[first] = ImageAdapter.chessboardIds[first];*/



        int x=0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //x = (i*8)+j;
                if (br.board[i][j].getClass().isInstance(new Pawn())) {
                    if (br.board[i][j].getColor().equals("White")) {
                        ImageAdapter.chessPiece[x] = R.drawable.whpawn;
                        //ImageAdapter.chessPiece[x] = ImageAdapter.chessPiece;
                    }
                    else {
                        ImageAdapter.chessPiece[x] = R.drawable.blpawn;
                    }
                }
                else if (br.board[i][j].getClass().isInstance(new Rook())) {
                    if (br.board[i][j].getColor().equals("White")) {
                        ImageAdapter.chessPiece[x] = R.drawable.whrook;
                    }
                    else {
                        ImageAdapter.chessPiece[x] = R.drawable.blrook;
                    }
                }
                else if (br.board[i][j].getClass().isInstance(new Knight())) {
                    if (br.board[i][j].getColor().equals("White")) {
                        ImageAdapter.chessPiece[x] = R.drawable.whknight;
                    }
                    else {
                        ImageAdapter.chessPiece[x] = R.drawable.blknight;
                    }
                }
                else if (br.board[i][j].getClass().isInstance(new Bishop())) {
                    if (br.board[i][j].getColor().equals("White")) {
                        ImageAdapter.chessPiece[x] = R.drawable.wishop;
                    }
                    else {
                        ImageAdapter.chessPiece[x] = R.drawable.bishop;
                    }
                }
                else if (br.board[i][j].getClass().isInstance(new King())) {
                    if (br.board[i][j].getColor().equals("White")) {
                        ImageAdapter.chessPiece[x] = R.drawable.whking;
                    }
                    else {
                        ImageAdapter.chessPiece[x] = R.drawable.blking;
                    }
                }
                else if (br.board[i][j].getClass().isInstance(new Queen())) {
                    if (br.board[i][j].getColor().equals("White")) {
                        ImageAdapter.chessPiece[x] = R.drawable.whqueen;
                    }
                    else {
                        ImageAdapter.chessPiece[x] = R.drawable.blqueen;
                    }
                }
                else {
                    ImageAdapter.chessPiece[x] = ImageAdapter.chessboardIds[x];
                }
                x++;
            }
        }
    }






    /*public static boolean isBlack(){ 8888888888888888888888888888888888888
        if(chessPiece[fir]==R.drawable.blqueen||chessPiece[fir]==R.drawable.blrook||chessPiece[fir]==R.drawable.blknight||
                chessPiece[fir]==R.drawable.bishop||chessPiece[fir]==R.drawable.blking||chessPiece[fir]==R.drawable.blpawn){
            return true;
        }
        return false;
    }*/

    static class ViewHolder {
        public ImageView square;
        public ImageView piece;
    }

    public static  Context getContext(){
        return mContext;
    }


    /*public static void setFirstSelectedPosition(int position) { 888888888888888888888888888
        firstSelectedPosition = position;
    }

    public static void setSecondSelectedPosition(int position) {
        secondSelectedPosition = position;
    }*/

    @Override
    public int getCount() {
        return chessboardIds.length;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

   /* public static int getFirst(){ 888888888888888888888888888888888888888888
        return firstSelectedPosition;
    }

    public static int getSecond(){
        return secondSelectedPosition;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        Button button;
        //update();
        if (rowView == null) {  // if it's not recycled, initialize some attributes

            rowView = mInflater.inflate(R.layout.square, null);


            ViewHolder viewHolder = new ViewHolder();
            viewHolder.square = (ImageView) rowView.findViewById(R.id.square_background);
            viewHolder.square.setImageResource(chessboardIds[position]);
            viewHolder.piece = (ImageView) rowView.findViewById(piece);
            viewHolder.piece.setImageResource(chessPiece[position]);

            rowView.setTag(viewHolder);

        }
        return rowView;
    }
}