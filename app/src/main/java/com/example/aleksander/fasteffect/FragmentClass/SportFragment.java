package com.example.aleksander.fasteffect.FragmentClass;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aleksander.fasteffect.R;

import static android.widget.LinearLayout.LayoutParams.MATCH_PARENT;
import static android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;
import static java.util.Objects.requireNonNull;


/**
 * {@link Fragment}
 * Klasa wykorzystywana do ustawien dotyczacych zapotrzebowania kalorycznego dla użytkownika
 * Zakladka "Aktywność"
 */
public class SportFragment extends Fragment {

    public static final String SHARED_PREFS = "shaaredPrefs";

    private Button buttonSave;
    private int valueActivity;
    private int valueKindOfSport;
    private int valueGoal;

    private LinearLayout linearLayoutAutomatically;
    private LinearLayout linearLayoutManually;

    protected String optionSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sport, container, false);

        TextInputEditText textInputEditTextCalories = view.findViewById(R.id.textInputEditTextKalorie);
        TextInputEditText textInputEditTextProtein = view.findViewById(R.id.textInputEditTextProtein);
        TextInputEditText textInputEditTextCarb = view.findViewById(R.id.textInputEditTextCarb);
        TextInputEditText textInputEditTextFat = view.findViewById(R.id.textInputEditTextFat);

        SharedPreferences sharedPreferences = requireNonNull(getContext()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        Button buttonAutomatically = view.findViewById(R.id.buttonAutomatycznie);
        Button buttonManually = view.findViewById(R.id.buttonRęczne);

        linearLayoutAutomatically = view.findViewById(R.id.linearLayoutAutomatyczne);
        linearLayoutManually = view.findViewById(R.id.linearLayoutRęczne);

        optionSelected = String.valueOf(sharedPreferences.getString("optionSelected", "0"));
        setPref(optionSelected);

        String dataMacro = sharedPreferences.getString("textCalories", "0"); //no id: default value
        textInputEditTextCalories.setText(dataMacro);

        dataMacro = sharedPreferences.getString("textProtein", "0"); //no id: default value
        textInputEditTextProtein.setText(dataMacro);

        dataMacro = sharedPreferences.getString("textCarb", "0"); //no id: default value
        textInputEditTextCarb.setText(dataMacro);

        dataMacro = sharedPreferences.getString("textFat", "0"); //no id: default value
        textInputEditTextFat.setText(dataMacro);

        buttonAutomatically.setOnClickListener(view12 -> {
            ViewGroup.LayoutParams paramsManually = linearLayoutManually.getLayoutParams();
            paramsManually.height = 0;
            paramsManually.width = 0;
            linearLayoutManually.setLayoutParams(paramsManually);
            linearLayoutAutomatically.getLayoutParams().height = WRAP_CONTENT;
            linearLayoutAutomatically.getLayoutParams().width = MATCH_PARENT;
            optionSelected = "0";
        });

        buttonManually.setOnClickListener(view13 -> {
            ViewGroup.LayoutParams paramsAutomatically = linearLayoutAutomatically.getLayoutParams();
            paramsAutomatically.height = 0;
            paramsAutomatically.width = 0;
            linearLayoutAutomatically.setLayoutParams(paramsAutomatically);
            linearLayoutManually.getLayoutParams().height = WRAP_CONTENT;
            linearLayoutManually.getLayoutParams().width = MATCH_PARENT;
            optionSelected = "1";
        });

        buttonSave = view.findViewById(R.id.buttonSave);

        final Spinner spinnerActivity = view.findViewById(R.id.spinnerAktywnosc);
        ArrayAdapter<CharSequence> adapterActivity = ArrayAdapter.createFromResource(requireNonNull(getActivity()), R.array.spinnerActivities, R.layout.spinner_item_my);
        adapterActivity.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerActivity.setAdapter(adapterActivity);

        buttonSave.setEnabled(true);

        String activitySpinnerValue = sharedPreferences.getString("spinnerAktywnosc", "0");
        assert activitySpinnerValue != null;
        spinnerActivity.setSelection(Integer.parseInt(activitySpinnerValue));

        spinnerActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String position = String.valueOf(spinnerActivity.getSelectedItemPosition());
                valueActivity = Integer.parseInt(position);
                buttonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // empty method from setOnItemSelectedListener
            }
        });

        final Spinner spinnerKindOfSport = view.findViewById(R.id.spinnerRodzajSportu);
        ArrayAdapter<CharSequence> adapterKindOfSport = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerTypeOfActivities, R.layout.spinner_item_my);
        adapterKindOfSport.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerKindOfSport.setAdapter(adapterKindOfSport);

        String dataRodzajSportu = sharedPreferences.getString("spinnerRodzajSportu", "0"); //no id: default value
        assert dataRodzajSportu != null;
        spinnerKindOfSport.setSelection(Integer.parseInt(dataRodzajSportu));

        spinnerKindOfSport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String position = String.valueOf(spinnerKindOfSport.getSelectedItemPosition());
                valueKindOfSport = Integer.parseInt(position);
                buttonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // empty method from setOnItemSelectedListener
            }
        });

        final Spinner spinnerGoal = view.findViewById(R.id.spinnerCel);
        ArrayAdapter<CharSequence> adapterGoal = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerGoals, R.layout.spinner_item_my);
        adapterGoal.setDropDownViewResource(R.layout.spinner_item_my);
        spinnerGoal.setAdapter(adapterGoal);

        String dataGoal = sharedPreferences.getString("spinnerCel", "0"); //no id: default value
        assert dataGoal != null;
        spinnerGoal.setSelection(Integer.parseInt(dataGoal));

        spinnerGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String position = String.valueOf(spinnerGoal.getSelectedItemPosition());
                valueGoal = Integer.parseInt(position);
                buttonSave.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //empty method from setOnItemSelectedListener
            }
        });

        buttonSave.setOnClickListener(view1 -> {
            SharedPreferences sharedPreferencesSharedPrefs = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesSharedPrefs.edit();

            if (optionSelected.equals("0")) {
                optionSet("0");
                editor.putString("spinnerAktywnosc", String.valueOf(valueActivity)); //InputString: from the EditText
                spinnerActivity.setSelection(valueActivity);
                editor.putString("spinnerRodzajSportu", String.valueOf(valueKindOfSport)); //InputString: from the EditText
                spinnerKindOfSport.setSelection(valueKindOfSport);
                editor.putString("spinnerCel", String.valueOf(valueGoal)); //InputString: from the EditText
                spinnerGoal.setSelection(valueGoal);
                editor.apply();
            }
            // druga opcja
            if (!optionSelected.equals("0")) {
                optionSet("1");
                int protein = Integer.parseInt(requireNonNull(textInputEditTextProtein.getText()).toString());
                int carb = Integer.parseInt(requireNonNull(textInputEditTextCarb.getText()).toString());
                int fat = Integer.parseInt(requireNonNull(textInputEditTextFat.getText()).toString());

                if ((protein + carb + fat) == 100) {
                    editor.putString("textProtein", String.valueOf(protein));
                    editor.putString("textCarb", String.valueOf(carb));
                    editor.putString("textFat", String.valueOf(fat));
                    editor.commit();

                } else {
                    Toast.makeText(getContext(), "Wartość procentowa nie jest równa 100%", Toast.LENGTH_SHORT).show();
                }

                int calories = Integer.parseInt(requireNonNull(textInputEditTextCalories.getText()).toString());
                if ((calories) < 100) {
                    Toast.makeText(getContext(), "Wartość jest za mała", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("textCalories", String.valueOf(calories));
                    editor.commit();
                }
            }
            Toast.makeText(getContext(), "Zapisano!", Toast.LENGTH_SHORT).show();
            buttonSave.setEnabled(true);
        });
        return view;
    }

    public void optionSet(String option) {
        SharedPreferences sharedPreferences = requireNonNull(getContext()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("optionSelected", option);
        editor.apply();
    }

    public void setPref(String optionSelected) {
        if (optionSelected.equals("0")) {
            ViewGroup.LayoutParams paramsReczne = linearLayoutManually.getLayoutParams();
            paramsReczne.height = 0;
            paramsReczne.width = 0;
            linearLayoutManually.setLayoutParams(paramsReczne);
            linearLayoutAutomatically.getLayoutParams().height = WRAP_CONTENT;
            linearLayoutAutomatically.getLayoutParams().width = MATCH_PARENT;
        }
        if (optionSelected.equals("1")) {
            ViewGroup.LayoutParams paramsAutomatyczne = linearLayoutAutomatically.getLayoutParams();
            paramsAutomatyczne.height = 0;
            paramsAutomatyczne.width = 0;
            linearLayoutAutomatically.setLayoutParams(paramsAutomatyczne);
            linearLayoutManually.getLayoutParams().height = WRAP_CONTENT;
            linearLayoutManually.getLayoutParams().width = MATCH_PARENT;
        }
    }
}
