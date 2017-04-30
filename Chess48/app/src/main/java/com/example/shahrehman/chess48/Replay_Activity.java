package com.example.shahrehman.chess48;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//import static com.example.shahrehman.chess48.R.string.newG;

/**
 * Created by Shah Rehman on 4/29/2017.
 */

public class Replay_Activity extends AppCompatActivity{
    private Button returnM;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay_activity);

        returnM = (Button)findViewById(R.id.return_main);

        returnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchMain();
            }
        });



    }

    private void launchMain(){
        Intent intent = new Intent(this,Main_Activity.class);
        startActivity(intent);
    }


}
