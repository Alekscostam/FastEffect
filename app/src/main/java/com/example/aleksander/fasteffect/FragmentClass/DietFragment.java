package com.example.aleksander.fasteffect.FragmentClass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.aleksander.fasteffect.LoginActivity;
import com.example.aleksander.fasteffect.R;

public class DietFragment extends Fragment {


    public DietFragment() {
        // Required empty public constructor
    }

    private SharedPreferences sharedPreferences;

    public static final String SHARED_PREFS = "shaaredPrefs";
    //public static final String SHARED_PREFS="shaaredPrefs";
    public static final String SWITCHL = "switchLaktoza";
    public static final String SWITCHC = "switchCukry";
    public static final String SWITCHG = "switchGluten";
    public static final String SWITCHS = "switchStraczki";
    private boolean switchOnOfL;
    private boolean switchOnOfC;
    private boolean switchOnOfG;
    private boolean switchOnOfS;

    Switch switchLaktoza;
    Switch switchCukry;
    Switch switchGluten;
    Switch switchStraczki;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_diet, container, false);

        switchLaktoza = (Switch) view.findViewById(R.id.switchLaktoza);
        switchCukry = (Switch) view.findViewById(R.id.switchCukry);
        switchGluten = (Switch) view.findViewById(R.id.switchGluten);
        switchStraczki = (Switch) view.findViewById(R.id.switchStraczki);

        Button buttonSave = (Button) view.findViewById(R.id.buttonSave);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        //   boolean isChecked= sharedPreferences.getBoolean(ex,false);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        loadData();
        updateViews();


        return view;
    }


    public void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCHS, switchStraczki.isChecked());
        editor.putBoolean(SWITCHG, switchGluten.isChecked());
        editor.putBoolean(SWITCHC, switchCukry.isChecked());
        editor.putBoolean(SWITCHL, switchLaktoza.isChecked());
        editor.apply();

    }

    public void loadData() {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        switchOnOfS = sharedPreferences.getBoolean(SWITCHS, false);
        switchOnOfL = sharedPreferences.getBoolean(SWITCHL, false);
        switchOnOfC = sharedPreferences.getBoolean(SWITCHC, false);
        switchOnOfG = sharedPreferences.getBoolean(SWITCHG, false);

    }

    public void updateViews() {
        switchStraczki.setChecked(switchOnOfS);
        switchLaktoza.setChecked(switchOnOfL);
        switchCukry.setChecked(switchOnOfC);
        switchGluten.setChecked(switchOnOfG);

    }

}
