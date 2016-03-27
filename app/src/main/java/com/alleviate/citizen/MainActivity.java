package com.alleviate.citizen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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

    String player,city,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences citizen = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor citizen_editor = citizen.edit();
        player = citizen.getString("Player","");
        city = citizen.getString("City","");
        gender = citizen.getString("Gender","");

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
