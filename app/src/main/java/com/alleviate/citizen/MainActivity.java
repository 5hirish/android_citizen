package com.alleviate.citizen;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

        final SharedPreferences citizen = getSharedPreferences("CitiZen",MODE_PRIVATE);

        player = citizen.getString("Player", "");
        city = citizen.getString("City", "");
        gender = citizen.getString("Gender", "");
        score = citizen.getInt("Score", 0);
        setdata = citizen.getString("DataSet", "");

        final SharedPreferences.Editor citizen_editor = citizen.edit();

        if (setdata.equals("")) {
            new Thread(new InsertData(getApplicationContext())).start();

            citizen_editor.putString("DataSet", "Y").apply();
            citizen_editor.putInt("BaseScore", basescore).apply();

        }

        if (player.equals("") || city.equals("")) {

            setContentView(R.layout.activity_first);

            final EditText city_name = (EditText)findViewById(R.id.city_name);
            final EditText player_name = (EditText)findViewById(R.id.player_name);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.next);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!player.equals("") && !city.equals("")) {
                        citizen_editor.putString("Player", player_name.getText().toString()).commit();
                        citizen_editor.putString("City", city_name.getText().toString()).commit();

                        Intent in = new Intent(MainActivity.this, HelpActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(in);
                    }else {
                        Toast.makeText(getApplicationContext(),"Please Enter your City and Name...!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {

            setContentView(R.layout.activity_main);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.next);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(MainActivity.this, IssueActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(in);
                }
            });

            }

            ImageView about = (ImageView) findViewById(R.id.about);
            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(in);
                }
            });


        }
}
