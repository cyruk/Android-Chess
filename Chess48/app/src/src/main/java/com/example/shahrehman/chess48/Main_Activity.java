package com.example.shahrehman.chess48;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_Activity extends AppCompatActivity {
    private Button newG;
    private  Button viewG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        newG = (Button) findViewById(R.id.new_name);

        newG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchGame();
            }
        });

        viewG = (Button) findViewById(R.id.view_game);
        viewG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchReplay();
            }
        });
    }


    private void launchGame() {

        Intent intent = new Intent(this, Chess_Activity.class);
        startActivity(intent);
    }

    private void launchReplay(){
        Intent intentt = new Intent(this,Replay_Activity.class);
        startActivity(intentt);
    }
}
