package com.alleviate.citizen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class IssueActivity extends AppCompatActivity {

    View decorView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        final CardView solution = (CardView)findViewById(R.id.card_view_sol);
        solution.setVisibility(View.GONE);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linear);

        final RadioButton[] radioButtons = new RadioButton[5];
        final RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
        for(int i=0; i<5; i++){
            radioButtons[i]  = new RadioButton(this);
            radioGroup.addView(radioButtons[i]);
            radioButtons[i].setTextSize(18);
            radioButtons[i].setTextColor(Color.BLACK);
            radioButtons[i].setText("Option "+i);
            radioButtons[i].setId(i);
            //radioGroup.addView(radioButtons[i]);
        }
        linearLayout.addView(radioGroup);

        View cardView = (View)findViewById(R.id.card_view);
        FloatingActionButton select = (FloatingActionButton)cardView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Hey",Toast.LENGTH_SHORT).show();
                radioGroup.setVisibility(View.GONE);

                solution.setVisibility(View.VISIBLE);
            }
        });
    }
}
