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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( getActivity(), R.array.spinnerAktywnosc , R.layout.spinner_item_my);

        adapter.setDropDownViewResource(R.layout.spinner_item_my);


        spinnerAktywnosc.setAdapter(adapter);

        spinnerAktywnosc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              String text = adapterView.getItemAtPosition(i).toString();
            //  Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
           //   ((TextView) adapterView.getChildAt(0)).setTextColor(Color.DKGRAY);
           //   ((TextView) adapterView.getChildAt(0)).setTextSize(15);

          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });
        return view;


    }

}
