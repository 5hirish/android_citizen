package com.alleviate.citizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Intro3Fragment extends Fragment {

    public Intro3Fragment() {
        // Required empty public constructor
    }


    public static Intro3Fragment newInstance() {
        Intro3Fragment fragment = new Intro3Fragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro3, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getText(R.string.citizen_intro_5));

        Button play = (Button)rootView.findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(),IssueActivity.class);
                startActivity(in);
            }
        });
        return rootView;
    }


}
