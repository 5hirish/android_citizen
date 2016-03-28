package com.alleviate.citizen;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver registration;

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static final String Topic_Issue = "/topics/issue";

    View decorView;                                         //Database and GCM

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
         decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    String player,city,gender,setdata;
    int score,basescore=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //filldatabase();

        SharedPreferences citizen = getPreferences(MODE_PRIVATE);

        player = citizen.getString("Player","");
        city = citizen.getString("City","");
        gender = citizen.getString("Gender","");
        score = citizen.getInt("Score",0);
        setdata = citizen.getString("DataSet","");

        if(setdata.equals("")){
            new Thread(new InsertData(getApplicationContext())).start();

            SharedPreferences.Editor citizen_editor = citizen.edit();
            citizen_editor.putString("DataSet","Y").apply();
            citizen_editor.putInt("BaseScore",basescore).apply();
        }

        /*if(player.equals("") || city.equals("")){

            setContentView(R.layout.activity_first);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.next);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(MainActivity.this,HelpActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(in);
                }
            });

        }else {*/

            setContentView(R.layout.activity_main);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.next);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(MainActivity.this,IssueActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(in);
                }
            });

        //}

        ImageView about = (ImageView)findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(in);
            }
        });


    }

    private void filldatabase() {

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbw = db.getWritableDatabase();

        try {
            Scanner in = new Scanner(new File("assets/issue.txt"));
            String status = "N";

            in.nextLine();		//Skip Header

            String regex = "\\|";

            while (in.hasNext()) {
                String[] row = in.nextLine().split(regex);

                int id = Integer.parseInt(row[0]);
                String issue = row[1];
                String optA = row[2];
                String optB = row[3];
                String optC = row[4];
                String answer = row[5];

                ContentValues insert_data = new ContentValues();
                insert_data.put(db.dbCZ_Issue,issue);
                insert_data.put(db.dbCZ_OptA,optA);
                insert_data.put(db.dbCZ_OptB, optB);
                insert_data.put(db.dbCZ_OptC, optC);
                insert_data.put(db.dbCZ_Answer, answer);
                insert_data.put(db.dbCZ_Status, status);

                long dbid = dbw.insert(db.dbCZ_table_Issue, null, insert_data);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found : "+e);
        }

        dbw.close();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
