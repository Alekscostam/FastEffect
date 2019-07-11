package com.example.aleksander.fasteffect.FragmentClass;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksander.fasteffect.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SportFragment extends Fragment  {


    public SportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sport, container, false);


        Spinner spinnerAktywnosc = (Spinner) view.findViewById(R.id.spinnerAktywnosc);
        ArrayAdapter<CharSequence> adapterAktywnosc = ArrayAdapter.createFromResource( getActivity(), R.array.spinnerActivities , R.layout.spinner_item_my);
        adapterAktywnosc.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerAktywnosc.setAdapter(adapterAktywnosc);

        spinnerAktywnosc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              String text = adapterView.getItemAtPosition(i).toString();

          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });




        Spinner spinnerRodzajSportu = (Spinner) view.findViewById(R.id.spinnerRodzajSportu);
        ArrayAdapter<CharSequence> adapterRodzajSportu = ArrayAdapter.createFromResource( getActivity(), R.array.spinnerTypeOfActivities , R.layout.spinner_item_my);
        adapterRodzajSportu.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerRodzajSportu.setAdapter(adapterRodzajSportu);

        spinnerRodzajSportu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



       Spinner spinnerCel = (Spinner) view.findViewById(R.id.spinnerCel);
       ArrayAdapter<CharSequence> adapterCel = ArrayAdapter.createFromResource(getActivity(),R.array.spinnerGoals,R.layout.spinner_item_my);
       adapterCel.setDropDownViewResource(R.layout.spinner_item_my);
       spinnerCel.setAdapter(adapterCel);
       spinnerCel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });


        return view;


    }

}
