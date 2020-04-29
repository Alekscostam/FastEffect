package com.example.aleksander.fasteffect.FragmentClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.aleksander.fasteffect.R;

public class DietFragment extends Fragment {


    public DietFragment() {
        // Required empty public constructor
    }


    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shaaredPrefs";

    public static final String SWITCHL = "switchLaktoza";
    public static final String SWITCHS = "switchSacharoza";
    public static final String SWITCHFOS = "switchFOS";
    public static final String SWITCHGOS = "switchGOS";
    public static final String SWITCHP = "switchPoliole";
    public static final String SWITCHF = "switchFruktoza";

    private boolean switchOnOfL;
    private boolean switchOnOfS;
    private boolean switchOnOfGOS;
    private boolean switchOnOfFOS;
    private boolean switchOnOfP;
    private boolean switchOnOfF;

    Switch switchLaktoza;
    Switch switchSacharoza;
    Switch switchFOS;
    Switch switchGOS;
    Switch switchPoliole;
    Switch switchFruktoza;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_diet, container, false);

        switchLaktoza = view.findViewById(R.id.switchLaktoza);
        switchSacharoza =  view.findViewById(R.id.switchSacharoza);
        switchFOS = view.findViewById(R.id.switchFOS);
        switchGOS =  view.findViewById(R.id.switchGOS);
        switchPoliole = view.findViewById(R.id.switchPoliole);
        switchFruktoza =  view.findViewById(R.id.switchFruktoza);

        Button buttonSave = view.findViewById(R.id.buttonSave);

        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCHGOS, switchGOS.isChecked());
        editor.putBoolean(SWITCHFOS, switchFOS.isChecked());
        editor.putBoolean(SWITCHS, switchSacharoza.isChecked());
        editor.putBoolean(SWITCHL, switchLaktoza.isChecked());
        editor.putBoolean(SWITCHP, switchPoliole.isChecked());
        editor.putBoolean(SWITCHF, switchFruktoza.isChecked());
        editor.apply();
        Toast.makeText(getActivity(), "Zapisano", Toast.LENGTH_SHORT).show();

    }

    public void loadData() {
        switchOnOfGOS = sharedPreferences.getBoolean(SWITCHGOS, false);
        switchOnOfFOS = sharedPreferences.getBoolean(SWITCHFOS, false);
        switchOnOfS = sharedPreferences.getBoolean(SWITCHS, false);
        switchOnOfL = sharedPreferences.getBoolean(SWITCHL, false);
        switchOnOfP = sharedPreferences.getBoolean(SWITCHP, false);
        switchOnOfF = sharedPreferences.getBoolean(SWITCHF, false);
    }

    public void updateViews() {
        switchGOS.setChecked(switchOnOfGOS);
        switchLaktoza.setChecked(switchOnOfL);
        switchSacharoza.setChecked(switchOnOfS);
        switchFOS.setChecked(switchOnOfFOS);
        switchPoliole.setChecked(switchOnOfP);
        switchFruktoza.setChecked(switchOnOfF);
    }

}
