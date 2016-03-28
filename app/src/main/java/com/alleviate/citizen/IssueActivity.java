package com.alleviate.citizen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    int id,imageid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        String[] col = {db.dbCZ_Id,db.dbCZ_Issue,db.dbCZ_OptA,db.dbCZ_OptB,db.dbCZ_OptC,db.dbCZ_Answer,db.dbCZ_Status,db.dbCZ_Explanation,db.dbCZ_ImageId};
        String where = db.dbCZ_Status+" = 'N'";

        Cursor cur =dbr.query(db.dbCZ_table_Issue,col,where,null,null,null,null,"1");

        if(cur!=null){
            while (cur.moveToNext()){
                id =cur.getInt(cur.getColumnIndex(db.dbCZ_Id));
                issue =cur.getString(cur.getColumnIndex(db.dbCZ_Issue));
                option[0] =cur.getString(cur.getColumnIndex(db.dbCZ_OptA));
                option[1] =cur.getString(cur.getColumnIndex(db.dbCZ_OptB));
                option[2] =cur.getString(cur.getColumnIndex(db.dbCZ_OptC));
                answer =cur.getString(cur.getColumnIndex(db.dbCZ_Answer));
                explanation =cur.getString(cur.getColumnIndex(db.dbCZ_Explanation));
                imageid =cur.getInt(cur.getColumnIndex(db.dbCZ_ImageId));

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

                if(selected_option.equals(answer)){
                    tvanswer.setText(answer);
                }else{
                    tvanswer.setText("Wrong! It's "+answer);
                }
                tvexplanation.setText(explanation);

                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Hey",Toast.LENGTH_SHORT).show();
                        //Apply N to Y...in Status...
                        recreate();

                        /*
                          finish();
                          startActivity(getIntent());*/
                    }
                });

            }
        });
    }
}
