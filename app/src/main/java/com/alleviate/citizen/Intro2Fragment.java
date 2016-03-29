package com.alleviate.citizen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Intro2Fragment extends Fragment {

    public Intro2Fragment() {
        // Required empty public constructor
    }


    public static Intro2Fragment newInstance() {
        Intro2Fragment fragment = new Intro2Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro2, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getText(R.string.citizen_intro_4));

        return rootView;
    }

}
