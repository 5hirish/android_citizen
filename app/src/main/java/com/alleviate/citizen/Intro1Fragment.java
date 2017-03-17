package com.alleviate.citizen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Intro1Fragment extends Fragment {

    public Intro1Fragment() {
        // Required empty public constructor
    }

    public static Intro1Fragment newInstance() {
        Intro1Fragment fragment = new Intro1Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro1, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);

        SharedPreferences citizen = getActivity().getSharedPreferences("CitiZen",Context.MODE_PRIVATE);

        String player = citizen.getString("Player", "");
        String city = citizen.getString("City", "");

        textView.setText(getString(R.string.citizen_intro_1)+" "+player+getString(R.string.citizen_intro_2)+" "+city+". "+getString(R.string.citizen_intro_3));
        return rootView;
    }
}
