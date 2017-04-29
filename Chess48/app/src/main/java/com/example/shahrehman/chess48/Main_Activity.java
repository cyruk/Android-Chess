package com.example.shahrehman.chess48;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_Activity extends AppCompatActivity {
    private Button newG;
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

    }


    private void launchGame() {

        Intent intent = new Intent(this, ChessActivity.class);
        startActivity(intent);
    }
}
