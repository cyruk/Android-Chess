package com.example.shahrehman.chess48;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.graphics.Color;
import android.widget.Toast;

public class ChessActivity extends AppCompatActivity {

    private static final String TAG = "rahimMessage";
    GridView chessGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String clickedText;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_activity);
        chessGrid = (GridView) findViewById(R.id.chessBoard);
        chessGrid.setAdapter(new ImageAdapter(this));
        //chessGrid.setSelector(new ColorDrawable(Color.YELLOW));
        chessGrid.getCheckedItemPosition();
        /*chessGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String clickedText = chessGrid.getItemAtPosition(position).toString();
                //Log.i(TAG, "" + clickedText);

            }
        });*/
        final int p=0;
        chessGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(ImageAdapter.getFirst()==-1) {
                    ImageAdapter.setFirstSelectedPosition(position);
                    chessGrid.setSelector(new ColorDrawable(Color.YELLOW));
                    ImageAdapter.setSecondSelectedPosition(-1);
                    ImageAdapter.fir= -1;
                }
                else{
                    ImageAdapter.setSecondSelectedPosition(position);
                    chessGrid.setSelector(new ColorDrawable(Color.YELLOW));
                    ImageAdapter.fir=ImageAdapter.getFirst();
                    //Context con = ImageAdapter.
                    ImageAdapter.update();
                    chessGrid.setAdapter(new ImageAdapter(ImageAdapter.getContext()));
                    //ImageAdapter.update();
                    //ImageAdapter.notifyDataSetChanged();

                    ImageAdapter.setFirstSelectedPosition(-1);
                }
                Log.i(TAG,"" + "First " + ImageAdapter.getFirst() + " Fir " + ImageAdapter.fir + " Second " + ImageAdapter.getSecond());
                Toast.makeText(ChessActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();


            }

        });


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
