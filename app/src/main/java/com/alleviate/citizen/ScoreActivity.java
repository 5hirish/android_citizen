package com.alleviate.citizen;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

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

    String player, city, gender, setdata;
    int score, basescore = 10, level, right, wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UiChangeListener();

        set_pref();

        reload_dataset();

    }

    private void set_pref() {

        final SharedPreferences citizen = getSharedPreferences("CitiZen",MODE_PRIVATE);

        player = citizen.getString("Player", "");
        city = citizen.getString("City", "");
        gender = citizen.getString("Gender", "");
        score = citizen.getInt("Score", 0);
        setdata = citizen.getString("DataSet", "");
        level = citizen.getInt("Level", 1);
        right = citizen.getInt("Right", 0);
        wrong = citizen.getInt("Wrong", 0);

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
            ImageView male = (ImageView) findViewById(R.id.male);
            ImageView female = (ImageView) findViewById(R.id.female);

            male.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    citizen_editor.putString("Profile", "Male").commit();
                }
            });

            female.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    citizen_editor.putString("Profile", "Female").commit();
                }
            });

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.next);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!player_name.getText().toString().equals("") && !city_name.getText().toString().equals("")) {

                        citizen_editor.putString("Player", player_name.getText().toString()).commit();
                        citizen_editor.putString("City", city_name.getText().toString()).commit();

                        Intent in = new Intent(ScoreActivity.this, HelpActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(in);
                    }else {
                        Toast.makeText(getApplicationContext(),"Please Enter your City and Name...!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {

            setContentView(R.layout.activity_score);

            TextView tv_player = (TextView)findViewById(R.id.player_name);
            TextView tv_city = (TextView)findViewById(R.id.city_name);
            TextView tv_score = (TextView)findViewById(R.id.score);
            TextView tv_level = (TextView)findViewById(R.id.level);
            TextView tv_right = (TextView)findViewById(R.id.tv_correct);
            TextView tv_wrong = (TextView)findViewById(R.id.tv_wrong);

            ImageView im_profile = (ImageView)findViewById(R.id.profile);

            String profile = citizen.getString("Profile", "Male");

            if(profile.equals("Male")){
                im_profile.setImageResource(R.mipmap.ic_male);
            }else {
                im_profile.setImageResource(R.mipmap.ic_female);
            }

            tv_player.setText(player);
            tv_city.setText(city+" City");
            tv_score.setText("$ "+score);               //Format dollars...
            tv_level.setText("Level "+level);
            tv_right.setText(right+"");
            tv_wrong.setText(wrong+"");

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.next);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(ScoreActivity.this, MainActivity.class);
                    startActivity(in);
                }
            });

        }
    }

    private void reload_dataset() {

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbw = db.getWritableDatabase();

        ContentValues update_values = new ContentValues();
        update_values.put(SQLiteHelper.dbCZ_Status,"N");

        dbw.update(SQLiteHelper.dbCZ_table_Issue, update_values, null, null);

        Toast.makeText(getApplicationContext(), "DataSet Refreshed...", Toast.LENGTH_SHORT).show();

        SharedPreferences citizen = getSharedPreferences("CitiZen", MODE_PRIVATE);
        SharedPreferences.Editor editor = citizen.edit();
        editor.putInt("Right", 0);
        editor.putInt("Wrong", 0);
        editor.commit();
    }

    public void UiChangeListener() {
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });
    }
}
