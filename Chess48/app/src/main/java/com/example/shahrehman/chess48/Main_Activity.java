package com.example.shahrehman.chess48;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Button newGame = (Button) findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switches scenes from this class to the other class
                Intent intent = new Intent(view.getContext(), ChessActivity.class);
                //carries over from the switching to a new scene
                startActivity(intent);
            }
        });
        Button replay = (Button) findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switches scenes from this class to the other class
                Intent intent2 = new Intent(view.getContext(), ReplayChessActivity.class);
                //carries over from the switching to a new scene
                startActivity(intent2);
            }
        });
    }
}
