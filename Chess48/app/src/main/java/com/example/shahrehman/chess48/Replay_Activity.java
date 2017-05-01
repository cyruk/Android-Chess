package com.example.shahrehman.chess48;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import static com.example.shahrehman.chess48.R.string.newG;

/**
 * Created by Shah Rehman on 4/29/2017.
 */

public class Replay_Activity extends AppCompatActivity{
    public static String playGame;
    private Button returnM;
    private Button sortName;
    private Button sortDate;
    private Button play;
    private String message;
    String[] gameName;
    //private TextView text = (TextView)findViewById(R.id.text);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay_activity);
        try {
            populateListView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        registerClick();
        returnM = (Button)findViewById(R.id.return_main);

        returnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMain();
            }
        });

        play = (Button)findViewById(R.id.play);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Chess_Activity.class).putExtra(playGame, message);
                startActivity(intent);
            }
        });

        sortName = (Button) findViewById(R.id.sortName);
        sortName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortByName();
            }
        });
        sortDate = (Button) findViewById(R.id.sortDate);
        sortDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sortByDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sortByName()
    {
        File file = getFilesDir();
        File[] fileList = file.listFiles();
        String[] name = new String[fileList.length];
        for (int i = 0; i < fileList.length; i ++)
        {
            name[i] = fileList[i].getName();
        }
        for (int i = 1; i < name.length; i++)
        {
            String temp = name[i];
            int j = i;
            while (j > 0 && name[j - 1].compareTo(temp) > 0)
            {
                name[j]  = name[j-1];
                j--;
            }
            name[j] = temp;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.games, name);
        //listview
        ListView list = (ListView)findViewById(R.id.listView);
        list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);

    }

    private void sortByDate() throws ParseException {
        File file = getFilesDir();
        File[] fileList = file.listFiles();
        String[] name = new String[fileList.length];
        String[] dates = new String[fileList.length];
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
        for (int i = 0; i < fileList.length; i ++)
        {
            dates[i] = sdf.format(fileList[i].lastModified());
            name[i] = fileList[i].getName();
        }
        for (int i = 1; i < name.length; i++)
        {
            String temp = name[i];
            String tempDate = dates[i];
            int j = i;
            while (j > 0 && new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS").parse(dates[j-1]).after(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS").parse(dates[i])))
            {
                name[j]  = name[j-1];
                dates[j] = dates[j - 1];
                j--;
            }
            name[j] = temp;
            dates[j] = tempDate;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.games, name);
        ListView list = (ListView)findViewById(R.id.listView);
        list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    private void populateListView() throws ParseException {
        //create list of items
        //find all the files inside files
        File file = getFilesDir();
        File[] fileList = file.listFiles();
        gameName = new String[fileList.length];
        for (int i = 0; i < fileList.length; i++)
        {
            gameName[i] = fileList[i].getName();
        }
        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.games, gameName);
        //listview
        ListView list = (ListView)findViewById(R.id.listView);
        list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    private void registerClick()
    {
        ListView list = (ListView)findViewById(R.id.listView);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setSelector(android.R.color.darker_gray);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView text2 = (TextView)viewClicked;
                message = text2.getText().toString();
            }
        });

    }

    private void launchMain(){
        Intent intent = new Intent(this,Main_Activity.class);
        startActivity(intent);
    }


}
