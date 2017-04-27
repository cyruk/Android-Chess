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

import static android.R.attr.button;
import static com.example.shahrehman.chess48.ImageAdapter.firstSelectedPosition;
import static com.example.shahrehman.chess48.ImageAdapter.secondSelectedPosition;
import static com.example.shahrehman.chess48.R.id.piece;


public class ImageAdapter extends BaseAdapter {

    private static Context mContext;
    private LayoutInflater mInflater;

    FrameLayout flcp;
    ImageView imgvcp = null;

    private int whiteTurn;
    public static int firstSelectedPosition = -1;
    public static int fir = -1;
    public static int secondSelectedPosition = -1;
    //final Object data = getLastNonConfigurationInstance();
    // CHESSBOARD

    private static Integer[] chessboardIds = {
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

    public static void update() {
        if (getSecond() != -1 && ImageAdapter.fir != -1 && getSecond()!=ImageAdapter.fir && ImageAdapter.chessPiece[fir]!= R.drawable.brown &&
                ImageAdapter.chessPiece[fir]!= R.drawable.white &&
                (ImageAdapter.chessPiece[getSecond()]==R.drawable.brown||ImageAdapter.chessPiece[getSecond()]==R.drawable.white)){
            int tmp;
            //tmp = ImageAdapter.chessPiece[getSecond()];
            ImageAdapter.chessPiece[getSecond()] = ImageAdapter.chessPiece[fir];
            ImageAdapter.chessPiece[fir] = ImageAdapter.chessboardIds[fir];


        }

    }


    //android:background="#FFFFFF"
    //android:background="#00000000"
    static class ViewHolder {
        public ImageView square;
        public ImageView piece;
    }

    public static  Context getContext(){
        return mContext;
    }

    public ImageAdapter(Context c/*, Piece[] listPiece, int turn*/) {
        mContext = c;
        Context context = c.getApplicationContext();
        mInflater = LayoutInflater.from(context);
    }

    public static void setFirstSelectedPosition(int position) {
        firstSelectedPosition = position;
    }

    public static void setSecondSelectedPosition(int position) {
        secondSelectedPosition = position;
    }

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

    public static int getFirst(){
        return firstSelectedPosition;
    }

    public static int getSecond(){
        return secondSelectedPosition;
    }

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

        /*View.OnLongClickListener cl = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData cd = ClipData.newPlainText("","");
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                v.startDrag(cd,shadow,v,0);
                return false;
            }
        };

        View.OnDragListener oragListener = new View.OnDragListener(){
            @Override
            public  boolean onDrag(View v, DragEvent event){
                int dragEvent = event.getAction();

                switch(dragEvent){
                    case DragEvent.ACTION_DRAG_ENTERED:
                        final View view = (View) event.getLocalState();
                        if(view.getId()== R.id.piece){

                        }
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        break;
                }
                return true;
            }
        };*/


        return rowView;
    }
}