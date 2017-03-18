package com.alleviate.citizen;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class IssueActivity extends AppCompatActivity {

    View decorView;
    final static int N_options = 3;

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

    String issue,answer,explanation;
    String option[] = new String[3];
    int id,imageid,score,level,basescore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        final SharedPreferences citizen = getSharedPreferences("CitiZen",MODE_PRIVATE);
        final SharedPreferences.Editor citizen_editor = citizen.edit();
        score = citizen.getInt("Score", 0);
        level = citizen.getInt("Level", 1);
        basescore = citizen.getInt("BaseScore", 10);

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        //String[] col = {db.dbCZ_Id,db.dbCZ_Issue,db.dbCZ_OptA,db.dbCZ_OptB,db.dbCZ_OptC,db.dbCZ_Answer,db.dbCZ_Status,db.dbCZ_Explanation,db.dbCZ_ImageId};
        String where = SQLiteHelper.dbCZ_Status +" = 'N'";

        Cursor cur =dbr.query(SQLiteHelper.dbCZ_table_Issue, null, where, null, null, null, null, "1");

        if(cur!=null){
            while (cur.moveToNext()){
                id =cur.getInt(cur.getColumnIndex(SQLiteHelper.dbCZ_Id));
                issue =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_Issue));
                option[0] =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_OptA));
                option[1] =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_OptB));
                option[2] =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_OptC));
                answer =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_Answer));
                explanation =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_Explanation));
                imageid =cur.getInt(cur.getColumnIndex(SQLiteHelper.dbCZ_ImageId));

            }cur.close();
        }

        dbr.close();

        final View solution = (View)findViewById(R.id.card_view_sol);
        solution.setVisibility(View.GONE);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linear);

        final RadioButton[] radioButtons = new RadioButton[5];
        final RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL

        for(int i = 0; i < N_options; i++){
            radioButtons[i]  = new RadioButton(this);
            radioGroup.addView(radioButtons[i]);
            radioButtons[i].setTextSize(18);
            radioButtons[i].setTextColor(Color.BLACK);
            radioButtons[i].setText(option[i]);
            radioButtons[i].setId(i);
            //radioGroup.addView(radioButtons[i]);
        }
        linearLayout.addView(radioGroup);

        View cardView = (View)findViewById(R.id.card_view);
        ImageView imageView = (ImageView)findViewById(R.id.card_thumbnail_image);
        imageView.setImageResource(IconRes.images[imageid]);

        TextView tvissue = (TextView)cardView.findViewById(R.id.issue);
        tvissue.setText(issue);

        final FloatingActionButton select = (FloatingActionButton)cardView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radioGroup.setVisibility(View.GONE);
                select.setImageResource(R.mipmap.ic_next);
                solution.setVisibility(View.VISIBLE);

                TextView tvanswer = (TextView)solution.findViewById(R.id.solution);
                TextView tvexplanation = (TextView)solution.findViewById(R.id.explanation);

                // get selected radio button from radioGroup
                int selected_option_id = 0;
                selected_option_id = radioGroup.getCheckedRadioButtonId();
                if(selected_option_id==-1){
                    selected_option_id = 0;
                }
                String selected_option = radioButtons[selected_option_id].getText().toString();

                if(id % 10 == 0){
                    basescore = basescore + 5;
                    level = level + 1;
                    citizen_editor.putInt("BaseScore", basescore).apply();
                    citizen_editor.putInt("Level", level).apply();

                    showdialog(basescore, level);
                }

                if(selected_option.equals(answer)){
                    tvanswer.setText(answer);
                    citizen_editor.putInt("Score", score + basescore).apply();
                }else{
                    tvanswer.setText("Wrong! It's "+answer);
                    citizen_editor.putInt("Score", score - basescore).apply();

                }
                tvexplanation.setText(explanation);

                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        /*SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                        SQLiteDatabase dbw = db.getWritableDatabase();

                        ContentValues update_values = new ContentValues();
                        update_values.put(db.dbCZ_Status,"Y");

                        String where = db.dbCZ_Id+" = "+id;

                        dbw.update(db.dbCZ_table_Issue, update_values, where, null);*/

                        //Apply N to Y...in Status...
                        //recreate();

                        next_issue();
                        /*
                          finish();
                          startActivity(getIntent());*/
                    }
                });

            }



        });


    }

    private void showdialog(int basescore, int level) {

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Congratulations! You have levelled up. Now you can earn more by $"+basescore);
        builder.setTitle("Level Up to Level "+level+"!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog levelupdialogue = builder.create();
        levelupdialogue.show();
    }

    private void next_issue() {
        final SharedPreferences citizen = getSharedPreferences("CitiZen", MODE_PRIVATE);
        final SharedPreferences.Editor citizen_editor = citizen.edit();
        score = citizen.getInt("Score", 0);
        level = citizen.getInt("Level", 1);
        basescore = citizen.getInt("BaseScore", 10);

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        //String[] col = new String[]{SQLiteHelper.dbCZ_Id, SQLiteHelper.dbCZ_Issue, SQLiteHelper.dbCZ_OptA, db.dbCZ_OptB, db.dbCZ_OptC, db.dbCZ_Answer, db.dbCZ_Status, db.dbCZ_Explanation, db.dbCZ_ImageId};
        String where = SQLiteHelper.dbCZ_Status +" = 'N'";

        Cursor cur =dbr.query(SQLiteHelper.dbCZ_table_Issue, null, where, null, null, null, null, "1");

        if(cur!=null){
            while (cur.moveToNext()){
                id =cur.getInt(cur.getColumnIndex(SQLiteHelper.dbCZ_Id));
                issue =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_Issue));
                option[0] =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_OptA));
                option[1] =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_OptB));
                option[2] =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_OptC));
                answer =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_Answer));
                explanation =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_Explanation));
                imageid =cur.getInt(cur.getColumnIndex(SQLiteHelper.dbCZ_ImageId));

            }cur.close();
        }

        dbr.close();

        final View solution = (View)findViewById(R.id.card_view_sol);
        solution.setVisibility(View.GONE);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linear);

        final RadioButton[] radioButtons = new RadioButton[5];
        final RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL

        for(int i=0; i<N_options; i++){
            radioButtons[i]  = new RadioButton(this);
            radioGroup.addView(radioButtons[i]);
            radioButtons[i].setTextSize(18);
            radioButtons[i].setTextColor(Color.BLACK);
            radioButtons[i].setText(option[i]);
            radioButtons[i].setId(i);
            //radioGroup.addView(radioButtons[i]);
        }
        linearLayout.addView(radioGroup);

        View cardView = (View)findViewById(R.id.card_view);
        ImageView imageView = (ImageView)findViewById(R.id.card_thumbnail_image);
        imageView.setImageResource(IconRes.images[imageid]);

        TextView tvissue = (TextView)cardView.findViewById(R.id.issue);
        tvissue.setText(issue);

        final FloatingActionButton select = (FloatingActionButton)cardView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radioGroup.setVisibility(View.GONE);
                select.setImageResource(R.mipmap.ic_next);
                solution.setVisibility(View.VISIBLE);

                TextView tvanswer = (TextView)solution.findViewById(R.id.solution);
                TextView tvexplanation = (TextView)solution.findViewById(R.id.explanation);

                // get selected radio button from radioGroup
                int selected_option_id = 0;
                selected_option_id = radioGroup.getCheckedRadioButtonId();
                if(selected_option_id==-1){
                    selected_option_id = 0;
                }
                String selected_option = radioButtons[selected_option_id].getText().toString();

                if(id % 10 == 0){
                    basescore = basescore + 5;
                    level = level + 1;
                    citizen_editor.putInt("BaseScore", basescore).apply();
                    citizen_editor.putInt("Level", level).apply();

                    //Create Dialogue box//
                    showdialog(basescore, level);

                }

                if(selected_option.equals(answer)){
                    tvanswer.setText(answer);
                    citizen_editor.putInt("Score", score + basescore).apply();
                }else{
                    tvanswer.setText("Wrong! It's "+answer);
                    citizen_editor.putInt("Score", score - basescore).apply();

                }
                tvexplanation.setText(explanation);

                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                        SQLiteDatabase dbw = db.getWritableDatabase();

                        ContentValues update_values = new ContentValues();
                        update_values.put(SQLiteHelper.dbCZ_Status,"Y");

                        String where = SQLiteHelper.dbCZ_Id +" = "+id;

                        dbw.update(SQLiteHelper.dbCZ_table_Issue, update_values, where, null);

                        next_issue();
                    }
                });

            }



        });
    }
}
