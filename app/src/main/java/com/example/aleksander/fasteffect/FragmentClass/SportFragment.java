package com.example.aleksander.fasteffect.FragmentClass;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleksander.fasteffect.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SportFragment extends Fragment {


    public SportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sport, container, false);



        return view;


    }

}
